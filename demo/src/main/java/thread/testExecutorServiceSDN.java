package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnny.ly on 2016/1/21.
 */
public class testExecutorServiceSDN {

    public static void main(String[] args){
        System.out.println(System.currentTimeMillis() + " start ");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+ "wake up");
                try {
                    Thread.sleep(10000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+ "wake up again");
                System.out.println(1 / 0);
            }
        });
        //executorService.shutdown();//can not execute or submit again
        try {
            boolean flag = executorService.awaitTermination(3, TimeUnit.SECONDS);
            System.out.println(System.currentTimeMillis()+"await result: " + flag);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        executorService.shutdownNow();//sdn will not terminate the thread, just send interrupt to thread
        System.out.println(System.currentTimeMillis()+"test");
    }
}
