## 一、核心概念与内存划分

Netty 的内存池（基于 jemalloc 思想）从大到小按以下层级组织：

| 层级 | 大小 | 说明 |
|---|---|---|
| **Chunk** | 16 MB（`chunkSize = pageSize × 2^maxOrder`，默认 8KB×2^11） | 一次向 OS 申请的大块，由完全二叉树（伙伴算法）管理 |
| **Page** | 8 KB（`pageSize`，默认 8192） | Chunk 的叶子节点，是页级分配的最小单元 |
| **Subpage** | < 8 KB | 把一个 Page 切成更小的等长块 |

按申请的**规格化容量** `normCapacity` 划分为三类：

| SizeClass | 范围 | 分配方式 | 池数组大小 |
|---|---|---|---|
| **Tiny** | `[16B, 512B)`，按 16B 步进 | 在 Page 内切 Subpage | `numTinySubpagePools = 512>>>4 = 32` |
| **Small** | `[512B, pageSize)`，按 2 的幂 | 在 Page 内切 Subpage | `numSmallSubpagePools = pageShifts - 9`（默认 4：512/1K/2K/4K）|
| **Normal** | `[pageSize, chunkSize]`，按 2 的幂 | 在 Chunk 上分配整页或多页（伙伴树）| `log2(max/pageSize)+1` |
| Huge | `> chunkSize` | 不池化，直接分配 | — |

规整的原因：PoolChunk 的伙伴树(完全二叉树)要求每层节点大小 = chunkSize / 2^depth(2 的幂)以及Subpage 的 bitmap 管理要求pageSize / elemSize 必须是整数

**规整后会造成最大≈50%的内存内部碎片(按 2 的幂)**

---

## 二、`PoolThreadCache` 与线程的关系

线程绑定通过 [PooledByteBufAllocator.java](/Users/liyang/Downloads/netty-buffer-4.1.33.Final/io/netty/buffer/PooledByteBufAllocator.java) 中的 `PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache>` 实现：

- 每个线程**首次申请**内存时，`initialValue()` 被触发：
    1. 在 `heapArenas[]` 与 `directArenas[]` 中通过 `leastUsedArena()` 各挑选一个**线程绑定数最少**的 Arena（`numThreadCaches` 最小者）。
    2. 用挑中的 Arena 构造该线程独占的 `PoolThreadCache`。
- 线程退出时 `onRemoval()` → `PoolThreadCache.free()` 释放本地缓存。
- 因此：**一个线程 ↔ 一个 PoolThreadCache ↔ 1 个 heapArena + 1 个 directArena**（Arena ↔ 线程是多对一的关系）。

`PoolThreadCache` 内部为它绑定的 Arena 维护 6 组 `MemoryRegionCache[]`（MPSC 队列缓存已释放但未归还的内存）

释放路径：`free` 时优先放回当前线程的 `MemoryRegionCache`（无锁，MPSC 队列），下次同线程同规格分配可直接命中，避开 Arena 全局锁。

---

## 三、整体结构示意图（Mermaid）

```mermaid
flowchart TB
    subgraph THREADS["应用线程 (FastThreadLocalThread)"]
        T1["Thread-1"]
        T2["Thread-2"]
        T3["Thread-N"]
    end

    subgraph TLC["FastThreadLocal&lt;PoolThreadCache&gt;"]
        PTC1["PoolThreadCache #1<br/>(Thread-1 专属)"]
        PTC2["PoolThreadCache #2<br/>(Thread-2 专属)"]
        PTC3["PoolThreadCache #N<br/>(Thread-N 专属)"]
    end

    T1 -- "FastThreadLocal.get()" --> PTC1
    T2 --> PTC2
    T3 --> PTC3

    subgraph CACHE["PoolThreadCache 内部 6 组本地缓存(MPSC Queue)"]
        direction LR
        C_TH["tinySubPageHeapCaches[32]<br/>16,32,...,496"]
        C_SH["smallSubPageHeapCaches[4]<br/>512,1K,2K,4K"]
        C_NH["normalHeapCaches[3]<br/>8K,16K,32K"]
        C_TD["tinySubPageDirectCaches[32]"]
        C_SD["smallSubPageDirectCaches[4]"]
        C_ND["normalDirectCaches[3]"]
    end
    PTC1 --- CACHE

    subgraph ARENAS["PooledByteBufAllocator 持有的全局 Arena 数组"]
        direction TB
        HA["heapArenas[ ]<br/>(默认 = CPU核数*2)"]
    end

    PTC1 -- "heapArena (leastUsed)" --> A1
    PTC2 --> A1
    PTC3 --> A1

    subgraph A1["PoolArena&lt;byte[]&gt;  (Heap)"]
        direction TB
        TSP["tinySubpagePools[32]<br/>每槽是一个 PoolSubpage 双向链表头<br/>idx = normCap >>> 4"]
        SSP["smallSubpagePools[4]<br/>idx 由 smallIdx() 算出"]
        subgraph CHUNKLISTS["按使用率组织的 PoolChunkList"]
            direction LR
            QI["qInit<br/>[MIN,25)"] --> Q0["q000<br/>[1,50)"] --> Q25["q025<br/>[25,75)"] --> Q50["q050<br/>[50,100)"] --> Q75["q075<br/>[75,100)"] --> Q100["q100<br/>[100,MAX)"]
        end
    end

    A2["PoolArena&lt;ByteBuffer&gt; (Direct)<br/>结构同 A1"]

    subgraph CHUNK["PoolChunk (16MB) - 完全二叉树 / 伙伴算法"]
        direction TB
        ROOT["depth=0  chunkSize(16MB)"]
        L1["depth=1  8MB | 8MB"]
        DOTS["..."]
        LEAF["depth=maxOrder(11)  2048 个 Page (8KB 叶子)"]
        ROOT --> L1 --> DOTS --> LEAF
        SUBPAGES["subpages[2048] : PoolSubpage[]<br/>叶子 Page 若被切分则挂 PoolSubpage"]
        LEAF -.切分.-> SUBPAGES
    end

    Q25 --- CHUNK

    subgraph SP["PoolSubpage (一个 8KB Page 内的等分)"]
        BITMAP["bitmap[]<br/>位图标记每个 elem 是否被占用"]
        ELEMS["elemSize = 16/32/.../4KB<br/>numAvail / maxNumElems"]
    end

    TSP -. "链入(空闲)" .-> SP
    SSP -. "链入(空闲)" .-> SP
    SUBPAGES --- SP
```

---

## 四、分配流程示意（命中缓存优先）

```mermaid
sequenceDiagram
    participant App as 业务线程
    participant PTC as PoolThreadCache(本线程)
    participant Arena as PoolArena(共享)
    participant Chunk as PoolChunk
    participant SP as PoolSubpage

    App->>PTC: allocate(reqCap)
    Note over PTC: 按 normCap 判定 Tiny/Small/Normal<br/>tinyIdx() / smallIdx() / log2()

    alt 命中本地 MemoryRegionCache
        PTC-->>App: 直接返回 buf (无锁)
    else 未命中
        PTC->>Arena: 走 Arena 分配路径
        alt Tiny / Small (< pageSize)
            Arena->>Arena: 查 tinySubpagePools / smallSubpagePools
            alt 池中有空闲 Subpage
                Arena->>SP: subpage.allocate() 返回 bitmap handle
            else 没有
                Arena->>Chunk: allocateSubpage(normCap)<br/>取一空闲 Page 切分
                Chunk->>SP: 新建 PoolSubpage 并挂回 Arena 池
            end
        else Normal (>= pageSize 且 <= chunkSize)
            Arena->>Chunk: allocateRun(normCap)<br/>伙伴树定位深度 d 的节点
        else Huge (> chunkSize)
            Arena-->>App: 不池化，直接分配 Unpooled Chunk
        end
        Arena-->>App: 返回 PooledByteBuf
    end

    App->>PTC: release()
    PTC->>PTC: add() 入 MPSC 队列(SizeClass 相同的 cache)<br/>队列满则归还 Arena
```

---

## 五、关键数据结构对应到代码的关系一览

| 对象 | 文件 | 作用 |
|---|---|---|
| `PoolThreadCache` | [PoolThreadCache.java](/Users/liyang/Downloads/netty-buffer-4.1.33.Final/io/netty/buffer/PoolThreadCache.java) | 线程私有缓存；持有 `heapArena` + `directArena` 引用 + 6 组 `MemoryRegionCache[]` |
| `PoolThreadLocalCache` | [PooledByteBufAllocator.java](/Users/liyang/Downloads/netty-buffer-4.1.33.Final/io/netty/buffer/PooledByteBufAllocator.java) | `FastThreadLocal<PoolThreadCache>`，把 cache 绑定到线程 |
| `PoolArena` | [PoolArena.java](/Users/liyang/Downloads/netty-buffer-4.1.33.Final/io/netty/buffer/PoolArena.java) | 全局共享内存竞技场；含 `tinySubpagePools[32]`、`smallSubpagePools[N]`、6 个 `PoolChunkList` |
| `PoolChunk` | [PoolChunk.java](/Users/liyang/Downloads/netty-buffer-4.1.33.Final/io/netty/buffer/PoolChunk.java) | 一块 16MB；用 `memoryMap`/`depthMap` 表示完全二叉树，伙伴算法 |
| `PoolSubpage` | [PoolSubpage.java](/Users/liyang/Downloads/netty-buffer-4.1.33.Final/io/netty/buffer/PoolSubpage.java) | 一个 Page 内的 Tiny/Small 切分，用 `bitmap` 管理小块 |
| `MemoryRegionCache` | `PoolThreadCache$MemoryRegionCache` | 每槽一个 MPSC 队列，缓存待复用的 `(chunk, handle, nioBuffer)` |

---

### 总结一句话

> **线程 → (FastThreadLocal) → `PoolThreadCache` → (leastUsed 选定) → `PoolArena` → (按使用率分桶的) `PoolChunkList` → `PoolChunk`(伙伴树) → 叶子 `Page`(8KB) → (Tiny/Small 时) 切成 `PoolSubpage`(bitmap)**；其中 `PoolThreadCache` 在前面再叠一层按 Tiny/Small/Normal 分类的 6 组 MPSC 本地缓存(释放但未归还)，做无锁的快速命中。


[任务规划完成]
# 按使用率分桶设计目的
## 一、6 个 List 的串联

每个节点的 `[minUsage, maxUsage)` 区间和 `prev/next` 关系如下：

| List   | minUsage | maxUsage | prevList | nextList | maxCapacity（≈可分配的最大 buf） |
|--------|----------|----------|----------|----------|-----------------------------------|
| qInit  | MIN_VALUE| 25       | **qInit (自身)** | q000 | 100% × chunkSize |
| q000   | 1        | 50       | **null** | q025 | 99% × chunkSize |
| q025   | 25       | 75       | q000     | q050 | 75% × chunkSize |
| q050   | 50       | 100      | q025     | q075 | 50% × chunkSize |
| q075   | 75       | 100      | q050     | q100 | 25% × chunkSize |
| q100   | 100      | MAX      | q075     | null | **0**（不能再分配）|

> 注意：`qInit.prevList = qInit`（自环），`q000.prevList = null`（链表底端）。

---

## 二、为什么要按使用率分桶？设计目的

1. **加速分配命中**：分配优先在使用率"中间偏高"的桶里查（默认从 q050 开始，见下文），更可能一次命中，减少遍历。
2. **避免反复拆/建大 Chunk**：使用率非常低的 Chunk 不立刻销毁（放在 q000 一段时间），有机会被复用。
3. **保证大块请求可用**：`maxCapacity = chunkSize × (100 - minUsage) / 100` 用 `minUsage` 来反推该桶里"任意一个 Chunk 能再分配的最大容量"。请求大于此值就跳过，整桶不必遍历，提速明显。

---

## 三、Arena 分配时的搜索顺序（很有讲究）

顺序：**q050 → q025 → q000 → qInit → q075**（注意 q100 不参与分配，q075 在最后）。

理由：
- 先 q050：使用率适中，碎片化和命中率折中最好；
- 然后 q025、q000、qInit：往低使用率方向找空间最多的；
- 最后 q075：尝试压榨高使用率的 Chunk，减少新建。

---

## 四、按使用率"迁移"Chunk 的核心逻辑

### 1) 关键的"边界点"——为什么 q000.prev = null

`q000` 的 `prevList = null`。当一个 Chunk 在 q000 中被全部释放（usage = 0 < minUsage = 1）时：
- `free` → `move0` → `prevList == null` → **return false**；
- 上层 [PoolArena.java](/Users/liyang/Downloads/netty-buffer-4.1.33.Final/io/netty/buffer/PoolArena.java) 收到 false 会调用 `destroyChunk(chunk)`，**把整块 16MB 还给 OS**。

这是 Netty 控制内存上限的关键：**只有 q000 里使用率彻底归零的 Chunk 才会被销毁**，不会发生"刚分完就立即销毁"的抖动。

### 2) 关键的"自环"——qInit.prev = qInit

新建的 Chunk 通过 `qInit.add(c)` 加入。`qInit` 的 `minUsage = Integer.MIN_VALUE`，意味着**无论 usage 多低都不会跌破**：

- 在 qInit 里释放，`chunk.usage() < minUsage` 永远为 `false`，永远不会 `move0` 销毁；
- 加上 `qInit.prevList = qInit`，即使从 q000 把一个 chunk "降级"过来，也不会越过 qInit 销毁。

效果：**新创建/早期使用的 Chunk 不会被立即销毁**，避免冷启动时反复申请释放。

---

## 五、整体迁移示意图

```mermaid
flowchart
    subgraph LISTS["PoolArena 内的 6 个使用率桶（双向 linked-list）"]
      qInit["qInit<br/>[MIN, 25)<br/>maxCap=100%"]
      q000["q000<br/>[1, 50)<br/>maxCap=99%"]
      q025["q025<br/>[25, 75)<br/>maxCap=75%"]
      q050["q050<br/>[50, 100)<br/>maxCap=50%"]
      q075["q075<br/>[75, 100)<br/>maxCap=25%"]
      q100["q100<br/>[100, MAX)<br/>maxCap=0 (不分配)"]
    end

    qInit -- "next (usage≥25)" --> q000
    q000  -- "next (usage≥50)" --> q025
    q025  -- "next (usage≥75)" --> q050
    q050  -- "next (usage≥100)" --> q075
    q075  -- "next (usage≥100)" --> q100

    q100 -- "prev (释放后 usage<100)" --> q075
    q075 -- "prev (usage<75)" --> q050
    q050 -- "prev (usage<50)" --> q025
    q025 -- "prev (usage<25)" --> q000
    q000 -- "prev=null<br/>usage<1 → 销毁 Chunk" --> DEAD(((destroyChunk)))
    qInit -- "prev=自身<br/>不会销毁" --> qInit

    NEW(["newChunk()"]) -- "qInit.add()" --> qInit

    APP["分配请求<br/>allocateNormal()"] .-> q050
    q050 -. "未命中" .-> q025 -. "未命中" .-> q000 -. "未命中" .-> qInit -. "未命中" .-> q075
```

[任务规划完成]

## Netty 内存池总体架构图（PooledByteBufAllocator 视角）

下面这张图自上而下展示了 **Thread → PoolThreadCache → PoolArena → PoolChunkList → PoolChunk → Page/Subpage** 的层级关系，并标明了内存按 `SizeClass` 的划分方式。

```mermaid
flowchart TB
    %% ===== 线程层 =====
    subgraph Threads["① 业务线程层（多线程）"]
        T1["Thread-1<br/>(FastThreadLocalThread)"]
        T2["Thread-2"]
        T3["Thread-3"]
        Tn["...Thread-N"]
    end

    %% ===== ThreadLocal Cache 层 =====
    subgraph TLC["② PoolThreadCache（每线程一份，FastThreadLocal）"]
        direction TB
        PTC1["PoolThreadCache#1<br/>持有 heapArena + directArena 引用"]
        PTC2["PoolThreadCache#2"]
        PTC3["PoolThreadCache#3"]
        PTCn["PoolThreadCache#N"]

        subgraph CacheArrays["内部 4 组 MemoryRegionCache 数组（MPSC 队列）"]
            C1["smallSubPageHeapCaches[]<br/>长度 = nSubpages（约 39 个）"]
            C2["normalHeapCaches[]<br/>长度 ≈ pageSize ~ maxCachedBufferCapacity"]
            C3["smallSubPageDirectCaches[]"]
            C4["normalDirectCaches[]"]
        end
        PTC1 -.内部持有.-> CacheArrays
    end

    %% ===== Arena 层 =====
    subgraph Arenas["③ PoolArena 池（数量 ≈ 2 × CPU核数，由所有线程共享）"]
        direction LR
        HA1["HeapArena#0"]
        HA2["HeapArena#1"]
        HAn["HeapArena#..."]
    end

    %% 线程 -> PoolThreadCache（1:1）
    T1 --> PTC1
    T2 --> PTC2
    T3 --> PTC3
    Tn --> PTCn

    %% PoolThreadCache 通过 leastUsedArena 绑定到使用最少的 Arena
    PTC1 -- "heapArena" --> HA1
    PTC2 -- "heapArena" --> HA2
    PTC3 -- "heapArena" --> HA1
    PTCn -- "leastUsedArena()" --> HAn

    %% ===== Arena 内部结构 =====
    subgraph ArenaInner["④ PoolArena 内部结构（以 HeapArena#0 为例）"]
        direction TB

        subgraph SubpagePools["smallSubpagePools[ ] —— 按 sizeIdx 分桶的 PoolSubpage 链表头数组"]
            SP0["[idx=0]<br/>elemSize=16B"]
            SP1["[idx=1]<br/>elemSize=32B"]
            SPx["[idx=...]<br/>...512B,1K,2K..."]
            SPn["[idx=nSubpages-1]<br/>elemSize≈28K（小于 pageSize×2）"]
        end

        subgraph ChunkLists["⑤ PoolChunkList 链表（按 chunk 使用率分级）"]
            direction LR
            QInit["qInit<br/>(min~25%)"]
            Q000["q000<br/>(1~50%)"]
            Q025["q025<br/>(25~75%)"]
            Q050["q050<br/>(50~100%)"]
            Q075["q075<br/>(75~100%)"]
            Q100["q100<br/>(100%)"]
            QInit --> Q000 --> Q025 --> Q050 --> Q075 --> Q100
        end
    end

    HA1 --> ArenaInner

    %% ===== PoolChunk 内部结构 =====
    subgraph ChunkInner["⑥ PoolChunk 内部布局（默认 chunkSize=4MB，pageSize=8KB → 512 pages）"]
        direction TB
        CMem["byte[] / ByteBuffer memory（连续内存块）"]
        CMem --> Layout

        subgraph Layout["chunk 内部按 page 切分，按 run 管理"]
            R1["Run-A：多页连续<br/>(Normal 分配，例如 32KB=4 pages)"]
            R2["Run-B：单页<br/>切分为 PoolSubpage<br/>(Small 分配，例如 elemSize=512B → 16 个槽位)"]
            R3["Run-C：空闲<br/>(在 runsAvail[] 优先队列中)"]
            R4["Run-D：多页连续<br/>(Normal 分配)"]
            R5["..."]
        end

        Mgmt["管理结构：<br/>• runsAvail: IntPriorityQueue[]（按 pageIdx 分桶的可用 run）<br/>• runsAvailMap: 记录每个 run 首尾 offset → handle<br/>• subpages[]: 每个 page 对应一个 PoolSubpage<br/>• handle(long): runOffset|size|isUsed|isSubpage|bitmapIdx"]
        Layout -.- Mgmt
    end

    Q050 --> Chunk1["PoolChunk#1"]
    Q050 --> Chunk2["PoolChunk#2"]
    Q025 --> Chunk3["PoolChunk#3"]
    Chunk1 --> ChunkInner

    %% Subpage 双向链表回连到 Arena 的 smallSubpagePools
    R2 -. "PoolSubpage 加入对应 sizeIdx 的链表" .-> SP1
```

---

## 内存按 SizeClass 的划分

Netty 把 `[16B, chunkSize]` 的所有可分配规格预先生成为一张固定 size 表，每个规格对应一个 `sizeIdx`：

```mermaid
flowchart
    REQ["申请 reqCapacity"] --> N["sizeClass.size2SizeIdx(reqCapacity)"]
    N --> JUDGE{sizeIdx 落在哪一段}

    JUDGE -- "sizeIdx ≤ smallMaxSizeIdx<br/>（约 0~38）" --> SMALL["Small<br/>16B / 32B / 48B / 64B ... ≈28KB<br/>↓<br/>走 PoolArena.smallSubpagePools[sizeIdx]<br/>从 PoolSubpage 切槽位"]

    JUDGE -- "smallMaxSizeIdx < sizeIdx < nSizes<br/>（pageSize ~ chunkSize）" --> NORMAL["Normal<br/>32KB / 40KB / 48KB ... 4MB<br/>↓<br/>走 PoolChunk.allocateRun()<br/>分配 N 个连续 page"]

    JUDGE -- "sizeIdx == nSizes（> chunkSize）" --> HUGE["Huge<br/>不池化<br/>↓<br/>allocateHuge() 直接 newUnpooledChunk"]

    SMALL -.线程缓存.-> TC1["PoolThreadCache.<br/>smallSubPageXxxCaches[sizeIdx]"]
    NORMAL -.线程缓存.-> TC2["PoolThreadCache.<br/>normalXxxCaches[sizeIdx - nSubpages]"]
    HUGE -.X 不缓存.-> NOC["不进入 ThreadCache"]
```

### sizeClass 表结构（pageShifts=13 时的示例）

| 段 | sizeIdx 范围 | 大小范围 | 来源 | 是否进 ThreadCache |
|---|---|---|---|---|
| **Small / SubPage** | `0 ~ smallMaxSizeIdx` | 16B、32B、48B、64B … 接近 2×pageSize | 从 `PoolSubpage` 的位图槽位中切 | ✅ `smallSubPageXxxCaches[]` |
| **Normal / Run** | `smallMaxSizeIdx+1 ~ nSizes-1` | pageSize 的多倍：32KB、40KB … chunkSize（默认 4MB）| 从 `PoolChunk` 的 `runsAvail` 中按 pageIdx 切 N 个 page | ✅ `normalXxxCaches[]`（≤ `maxCachedBufferCapacity`） |
| **Huge** | `== nSizes`（> chunkSize） | > 4MB | 单独 `newUnpooledChunk` | ❌ 不缓存 |

每一档的精确大小由公式：

```
size = (1 << log2Group) + nDelta * (1 << log2Delta)
```

每翻倍一组（`LOG2_SIZE_CLASS_GROUP=2` 即每组 4 个）地生成，做到密集且整齐对齐。
