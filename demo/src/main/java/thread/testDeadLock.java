package thread;

import netty.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by johnny.ly on 2016/5/28.
 */
public class testDeadLock {
    public static void main(String[] args) throws Exception{
        System.out.println(Runtime.getRuntime().availableProcessors() * 2);
        ExecutorService service = Executors.newFixedThreadPool(10, new NamedThreadFactory("Johnny", false));
        /*
        final Object lock = new Object();
        System.out.println(lock);
        final Object lock1 = new Object();
        System.out.println(lock1);
        service.execute(new Runnable() {
            public void run() {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + "--------start");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                    System.out.println(Thread.currentThread().getName() + "--------wake up");
                    synchronized (lock1){
                        System.out.println(Thread.currentThread().getName() + "--------again");
                    }
                }
            }
        });
        service.execute(new Runnable() {
            public void run () {
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "--------start");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                    System.out.println(Thread.currentThread().getName() + "--------wake up");
                    synchronized (lock){
                        System.out.println(Thread.currentThread().getName() + "--------again");
                    }
                }
            }
        });
        */
        final ReentrantLock lock = new ReentrantLock();
        System.out.println(lock);
        final ReentrantLock lock1 = new ReentrantLock();
        System.out.println(lock1);
        service.execute(new Runnable() {
            public void run() {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "--------start");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                System.out.println(Thread.currentThread().getName() + "--------wake up");
                lock1.lock();
                System.out.println(Thread.currentThread().getName() + "--------again");
                lock1.unlock();
                lock.unlock();
            }
        });
        service.execute(new Runnable() {
            public void run() {
                lock1.lock();
                System.out.println(Thread.currentThread().getName() + "--------start");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                System.out.println(Thread.currentThread().getName() + "--------wake up");
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "--------again");
                lock.unlock();
                lock1.unlock();
            }
        });
        try {
            Thread.sleep(5000);
        } catch(Exception e) {

        }

        service.shutdownNow();
        service.awaitTermination(3, TimeUnit.SECONDS);
    }
}
