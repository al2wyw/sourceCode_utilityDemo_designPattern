package thread;

import netty.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by johnny.ly on 2016/5/27.
 */
//wait 的语义是 释放锁并等待notify, wait结束时没有重新获得锁的话依然跑不了，如果wait没有timeout也没有接收到notify，将一直wait下去
public class testThreadDump {
    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(10, new NamedThreadFactory("Johnny",false));
        /*
        Object lock = new Object();
        System.out.println(lock);
        service.execute(new Worker1(lock));
        service.execute(new Worker1(lock));
        */

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        System.out.println(lock);
        service.execute(new Worker2(lock,condition));
        service.execute(new Worker2(lock,condition));

        try{
            Thread.sleep(40000);
        }catch (Exception e){

        }
        service.shutdownNow();
        service.awaitTermination(3, TimeUnit.SECONDS);
    }

    static class Worker implements Runnable{

        private Object lock;

        public Worker(Object lock) {
            this.lock = lock;
        }

        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + "--------start");
                try {
                    Thread.sleep(15000);
                }catch (Exception e){

                }
                System.out.println(Thread.currentThread().getName()+"--------wake up");
                try {
                    lock.wait();
                }catch (Exception e){
                    System.out.println(Thread.currentThread().getName()+"--------interrupt");
                }
                System.out.println(Thread.currentThread().getName()+"------done");
            }
        }
    }

    static class Worker1 implements Runnable{

        private Object lock;

        public Worker1(Object lock) {
            this.lock = lock;
        }

        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + "--------start");
                try {
                    Thread.sleep(15000);
                }catch (Exception e){

                }
                System.out.println(Thread.currentThread().getName()+"--------wake up");
                try {
                    lock.wait(5000);
                }catch (Exception e){
                    System.out.println(Thread.currentThread().getName()+"--------interrupt");
                }
                System.out.println(Thread.currentThread().getName()+"------done");
            }
        }
    }

    static class Worker2 implements Runnable{

        private ReentrantLock lock;

        private Condition condition;

        public Worker2(ReentrantLock lock,Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "--------start");
            try {
                Thread.sleep(15000);
            }catch (Exception e){

            }
            System.out.println(Thread.currentThread().getName()+"--------wake up");
            try {
                condition.signal();
                condition.await();
            }catch (Exception e){
                System.out.println(Thread.currentThread().getName()+"--------interrupt");
            }
            System.out.println(Thread.currentThread().getName()+"------done");
            lock.unlock();
        }
    }
}
