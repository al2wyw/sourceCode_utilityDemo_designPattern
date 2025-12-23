package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountedLoop: index with int/char/byte counting loop
    -XX:+PrintSafepointStatistics
    -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=1000
 * */
public class SafePointTest {

    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 1000000000; i++) {
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
