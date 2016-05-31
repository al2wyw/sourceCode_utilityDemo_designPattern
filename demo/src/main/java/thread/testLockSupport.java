package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/5/31.
 */
public class testLockSupport {
    //lockSupport park will be interrupt
    public static void main(String[] args) throws Exception{
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
}
