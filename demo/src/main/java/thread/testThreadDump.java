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
 *
 * BLOCKED(on object monitor) -> in Object.wait()           (wait set)  没有这一项看work1 dump???
 *                            -> waiting for monitor entry  (entry set)
 *
 * WAITING(on object monitor, parking, sleeping)  -> waiting on condition
 *                                                -> in Object.wait()
 *
 * An ownable synchronizer is a synchronizer that may be exclusively owned by a thread,
 * ReentrantLock and ReentrantReadWriteLock are two examples.
 * ReentrantLock lock成功后就会出现在 Locked ownable synchronizers(ReentrantReadWriteLock的read lock不会出现)
 *
 * 当一个线程占有一个锁的时候，线程堆栈会打印一个－locked<0x22bffb60>
 * 当一个线程正在等在其他线程释放该锁，线程堆栈会打印一个－waiting to lock<0x22bffb60>
 * 当一个线程占有一个锁，但又执行在该锁的wait上，线程堆栈中首先打印locked,然后打印－waiting on <0x22c03c60>
 * 在一个线程释放锁和另一个线程被唤醒之间有一个时间窗，这时恰好打印堆栈信息，那么只会找到waiting to ，但是找不到locked 该锁的线程
 * parking to wait for  <0x00000000d7258ea8> : LockSupport.park造成, ReentrantLock的lock，await，signal等操作都是调用park/unpark
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
