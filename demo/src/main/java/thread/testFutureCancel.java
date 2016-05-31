package thread;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by johnny.ly on 2016/5/31.
 */
public class testFutureCancel {

    private static ConcurrentLinkedQueue<String> log = new ConcurrentLinkedQueue<String>();

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //3 type of submit returns Future<Void>, Future<T>
        //Future<Void>, Future<T> are all FutureTask, FutureTask has a callable
        //so all the runnable submitted become callable inside FutureTask finally
        Future res = executorService.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+"子线程Interrupt" + System.currentTimeMillis());
                }
                System.out.println(Thread.currentThread().getName()+"子线程weak" + System.currentTimeMillis());
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+"子线程Interrupt" + System.currentTimeMillis());
                }
                System.out.println(Thread.currentThread().getName()+"子线程weak" + System.currentTimeMillis());
            }
        });
        Thread.sleep(1000);
        System.out.println(res.cancel(false));
        System.out.println(res.cancel(true));
        System.out.println(res.isCancelled());
        System.out.println(res.isDone());
    }
}
