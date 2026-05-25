package thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiTreadProfileTest implements Runnable {

    private static final Lock lock = new ReentrantLock();
    private static final AtomicInteger flag = new AtomicInteger(0);

    private static double ret = 1.1;

    private static final int LOOP = 10000000;

    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();
        MultiTreadProfileTest t = new MultiTreadProfileTest();
        //10个线程start和join
        int count = 10;
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(t);
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            threads[i].join();
        }

        System.out.println(ret);
        System.out.println("Total time: " + (System.currentTimeMillis() - start) + " ms");

    }

    @Override
    public void run() {
        // 单个thread: cpu 200ms total time 600ms, profiler会把所有线程的耗时累加起来
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            lock.lock();
            ret += i / ret;
            lock.unlock();
        }
        System.out.println("Thread finished in " + (System.currentTimeMillis() - start) + " ms");
    }
}
