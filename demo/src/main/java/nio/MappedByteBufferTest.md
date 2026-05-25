## 验证方案（按推荐顺序）

### ✅ 方法 1：清除 page cache 后对比测试（验证预读 + 缓存）

```bash
# 步骤1：清空 page cache（需要 root）
sync && echo 3 > /proc/sys/vm/drop_caches

# 步骤2：再次运行测试
perf stat -e major-faults,minor-faults,task-clock \
  ~/linux-x86_64-normal-server-slowdebug/jdk/bin/java \
  nio.MappedByteBufferTest /data/home/johnnyleeli/tdwdfsclient-3.2.x.tgz
```

**预期结果对比：**

| 场景 | major-faults | 含义 |
|------|--------------|------|
| 你的当前结果 | 1            | 文件已在 page cache 中，几乎不需要从磁盘读 |
| 清缓存后 + 预读开启 | 几百到几千        | 真实从磁盘读，但预读减少了 fault 次数 |
| 清缓存后 + 关闭预读 | ~10万         | 每个页面都需要单独从磁盘读 |

如果**清缓存后 major-faults 依然只有几次**，说明预读在生效。

---

### ✅ 方法 2：关闭预读机制（关键验证手段）

```bash
# 查看当前预读大小（单位：512字节扇区，默认通常是256，即128KB）
blockdev --getra /dev/sda

# 临时关闭预读
blockdev --setra 0 /dev/sda

# 清缓存后运行测试
sync && echo 3 > /proc/sys/vm/drop_caches
perf stat -e major-faults,minor-faults ~/.../java nio.MappedByteBufferTest /path/to/file

# 恢复
blockdev --setra 256 /dev/sda
```

或者在 Java 代码里通过 `madvise` 控制（需要 JNI），更精细的方式是用 `posix_fadvise`：

```bash
# 用 strace 观察是否有 readahead 系统调用
strace -e trace=readahead,fadvise64,madvise -f \
  ~/.../java nio.MappedByteBufferTest /path/to/file
```

**预期结果**：关闭预读后，major-faults 数量会**急剧增加**（接近文件页数）。

---

### ✅ 方法 3：验证是否使用大页（Transparent Huge Pages）

#### 3.1 查看 THP 配置

```bash
# 查看 THP 是否开启
cat /sys/kernel/mm/transparent_hugepage/enabled
# 输出可能为：[always] madvise never  -> 当前是 always
#           always [madvise] never  -> 仅在 madvise 时启用
#           always madvise [never]  -> 关闭

# 查看当前进程使用的大页数量
cat /proc/<pid>/smaps | grep -A 20 "java" | grep -i huge
```

#### 3.2 查看进程的 mmap 大页使用情况

在程序运行时（可以在 Java 代码循环中加 `Thread.sleep` 或读完后等待）：

```bash
# 找到 java 进程的 pid
pgrep -f MappedByteBufferTest

# 查看它的内存映射详情
cat /proc/<pid>/smaps | grep -E "AnonHugePages|FilePmdMapped|Size"
```

关注以下字段：
- `AnonHugePages`：匿名大页（不适用于 mmap 文件）
- `FilePmdMapped`：**文件映射使用的大页**（如果非0，说明用了大页）
- `KernelPageSize` / `MMUPageSize`：实际使用的页大小

### ✅ 方法 4：通过页数反推

```
理论 4KB 页数 = 416,929,793 / 4096 = 101,789
实际 minor-faults = 80,933
比例 = 80,933 / 101,789 ≈ 79.5%
```

由于不是 100%，可以推断：
- **不太可能用了 2MB 大页**（如果用了大页，minor-faults 应该是 `397MB / 2MB ≈ 199`，远小于 80,933）
- **更可能是 fault-around 机制**：一次 fault 映射约 1.26 个页面（每次 fault 顺便映射相邻页面）

可以查看 fault-around 配置：
```bash
cat /proc/sys/vm/fault_around_bytes
# 默认是 65536 (64KB) = 16 个 4KB 页
```
