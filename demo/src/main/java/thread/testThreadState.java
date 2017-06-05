package thread;

import netty.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/6/5
 * Time: 11:09
 * Desc:  synchronized -> block
 *  when thread 5 wake up, dump the threads, you can see some threads are blocked
 */
public class testThreadState {
    public static void main(String[] args) throws Exception{

        final Object lock = new Object();
        ExecutorService service = Executors.newFixedThreadPool(10, new NamedThreadFactory("Johnny", false));
        Runnable run = new Runnable() {
            public void run() {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + "--------start");
                    try {
                        lock.wait();
                        Thread.sleep(5000);
                    } catch (Exception e) {

                    }
                    System.out.println(Thread.currentThread().getName() + "--------wake up");

                }
            }
        };
        service.execute(run);//thread 1
        service.execute(run);//thread 2
        service.execute(run);//thread 3
        service.execute(run);//thread 4
        Runnable run1 = new Runnable() {
            public void run() {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + "--------start");
                    try {
                        Thread.sleep(15000);
                        lock.notifyAll();
                    } catch (Exception e) {

                    }
                    System.out.println(Thread.currentThread().getName() + "--------wake up");

                }
            }
        };
        service.execute(run1);//thread 5
    }
}
