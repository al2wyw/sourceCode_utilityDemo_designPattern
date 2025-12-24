package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
    -XX:+PrintSafepointStatistics
    -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=1000 (进入安全点的超时时间)
    -XX:+UnlockDiagnosticVMOptions -XX:GuaranteedSafepointInterval=1000 -XX:+PrintFlagsFinal (最小进入安全点的时间间隔)
 *
 * HotSpot会在长时间执行的指令处放置安全点，即所有方法的临返回之前，以及所有非CountedLoop的循环的回跳之前:
 * 长时间执行的最明显特征就是指令序列的复用，例如方法调用、循环跳转、异常跳转等
 *
 * CountedLoop: index with int/char/byte counted loop
 *
 * 破解之道:
 *     -Xint
 *     -XX:+UseCountedLoopSafepoints
 *     Loop Strip Mining (jdk10+)
 *     index with long
 *     手动插入安全点
 *
 * https://stackoverflow.com/questions/67068057/the-main-thread-exceeds-the-set-sleep-time
 * https://stackoverflow.com/questions/72753599/counted-uncounted-loops-and-safepoints-is-while-i-someint-considered-u
 * */
public class SafePointTest {

    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 500000000; i++) {
                /*
                if (i % 1000 == 0) {
                    try {
                        Thread.sleep(0); //native 方法调用后的安全点检查
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                */
                num.getAndAdd(1);
            }
        };
        Thread t1 = new Thread(runnable, "ThreadOne");
        Thread t2 = new Thread(runnable, "ThreadTwo");
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        System.out.println("***********主线程开始睡眠1秒***********");
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("***********主线程恢复,耗时：+" + (end - start) + "***********");
        System.out.println("最终的计数，num= " + num);
    }
}

/*
*
          vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
1.031: no vm operation                  [      11          2              2    ]      [  3075     0  3075     0     0    ]  0
4.107: EnableBiasedLocking              [      11          0              0    ]      [     0     0     0     0     0    ]  0
4.107: no vm operation                  [       8          0              1    ]      [     0     0     0     0     0    ]  0

Polling page always armed
EnableBiasedLocking                1
    0 VM operations coalesced during safepoint
Maximum sync time   3075 ms
Maximum vm operation time (except for Exit VM operation)      0 ms

*  "no vm operation" indicates a guaranteed safepoint(GuaranteedSafepointInterval) initiated by the JVM runtime, rather than an explicit, action-driven operation.
* */