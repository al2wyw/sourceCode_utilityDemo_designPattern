The Cost of Locks are high: require arbitration and context switch to kernel which suspend threads, leading to cache misses

CAS(loop) is alternative to locks: lock the bus to ensure atomicity and employ a memory barrier to make visibility

Cache lines: two variables in the same cache line can cause false sharing

Queue problem:
  - write contention on the head, tail, and size variables which generally occupy the same cache-line
  - large-grain locks for put and take operations are simple to implement but represent a significant bottleneck to throughput
  - elements are significant sources of garbage collection

Design of the LMAX Disruptor

Memory Allocation: All memory for the ring buffer is **pre-allocated** on start up:
  - The memory is allocated at the same time and highly likely that it will be laid out contiguously in main memory.
  - The memory which is immortal represents little burden on the garbage collector

Three Concerns:
  - Storage of items being exchanged (claim pre-allocated element)
  - Coordination of producers claiming the next sequence for exchange (claim -> write -> commit)
  - Coordination of consumers being notified that a new item is available (various waiting strategies: CPU resource(sleep) VS latency(spin))

Sequence:
```text
produce:                               consume:
 producer               consumer        consumer       producer
    ↓                     ↑                 ↓            ↑
seq(mem bar)   ->   (mem bar)cursor      cursor  ->     seq (prevent the ring from wrapping)
```

cas + release write/acquire read + false sharing 尽量减少cas操作