package thread;

import utils.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/5/31.
 */
public class testLockSupport {
    //lockSupport park will be interrupt and not throw interrupted exception
    public static void main(String[] args) throws Exception{
        //testPark();
        //testInterruptPark();
        testSpuriousPark();
        //testSpinForTimeoutThreshold();
    }

    private static void testSpinForTimeoutThreshold() throws Exception {
        // spinForTimeoutThreshold = 1000L;
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            LockSupport.parkNanos(1000);
        }
        long end = System.nanoTime();
        System.out.println("spend time: " + (end - start));
    }

    private static void testPark(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Object lock = new Object();
        final Thread t = Thread.currentThread();
        executorService.submit(new Runnable() {
            public void run() {
                LockSupport.unpark(t);//can unpark early
                LockSupport.unpark(t);//can not accumulate
                System.out.println("main thread unpark" + System.currentTimeMillis());
            }
        });
        try{
            Thread.sleep(3000);
        }catch (Exception e){

        }
        System.out.println("main thread park" + System.currentTimeMillis());
        LockSupport.park(lock);
        System.out.println("main thread wake up" + System.currentTimeMillis());
        try{
            Thread.sleep(3000);
        }catch (Exception e){

        }
        System.out.println("main thread park" + System.currentTimeMillis());
        LockSupport.park(lock);//this will  wait
        System.out.println("main thread wake up" + System.currentTimeMillis());
    }

    private static void testInterruptPark(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Object lock = new Object();
        final Thread t = Thread.currentThread();
        executorService.submit(new Runnable() {
            public void run() {
                System.out.println("thread start" + System.currentTimeMillis());
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                if (!t.isInterrupted()) {
                    t.interrupt();
                    System.out.println("interrupt main thread" + System.currentTimeMillis());
                }
            }
        });
        System.out.println("main thread park" + System.currentTimeMillis());
        LockSupport.park(lock); //lock is parkBlocker for a thread, which is used for thread diagnostic tool to get the reason why the thread is blocked
        System.out.println("main thread wake up" + System.currentTimeMillis());
        if(t.isInterrupted()){
            System.out.println("main thread interrupted" + System.currentTimeMillis());
        }
    }

    /**
     *  由于park会因为未知原因返回(spuriously return)，所以要用以下模式
     *  while (!canProceed()) { ... LockSupport.park(this); }
     *  Each blocking system call(including pthread_cond_wait) on Linux returns abruptly with EINTR when the process receives a signal.
     *  Windows system have no spurious wake-up.
     * */
    private static void testSpuriousPark(){
        Object lock = new Object();
        /*ExecutorService executorService = Executors.newFixedThreadPool(10);
        final Thread t = Thread.currentThread();
        executorService.submit(new Runnable() {
            public void run() {
                while(true) {
                    ThreadUtils.sleep(1000);
                    LockSupport.unpark(t);
                }
            }
        });*/
        while(true) {
            System.out.println("main thread sleep" + System.currentTimeMillis());
            LockSupport.parkNanos(lock, 3000000000L);//3 seconds
            System.out.println("main thread wake up" + System.currentTimeMillis());
        }
    }
}

/**
 * 由于每个jvm的实现可以自己安排字段的存储顺序，对字段的访问可以使用offset抽象出来，统一接口，解耦实现
 * unsafe.putObject(t, parkBlockerOffset, arg);
 * */