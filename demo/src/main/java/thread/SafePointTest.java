package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
    -XX:+PrintSafepointStatistics
    -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=1000 (进入安全点的超时时间)
    -XX:+UnlockDiagnosticVMOptions -XX:GuaranteedSafepointInterval=3000 (最小进入安全点的时间间隔)
 *
 * CountedLoop: index with int/char/byte counting loop
 * 破解之道:
 *     -Xint
 *     index with long
 *     手动插入安全点
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
