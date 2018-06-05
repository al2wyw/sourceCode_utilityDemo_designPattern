package thread;

import netty.NamedThreadFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by win10 on 2018/6/5.
 */
public class testThreadPoolSubmitDeadLock {

    public static void main(String[] args) throws Exception {
        final ExecutorService service = Executors.newFixedThreadPool(10, new NamedThreadFactory("Johnny", false));
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("start");
                try{
                    Thread.sleep(5000);

                    System.out.println("wake up");
                    Future f = service.submit(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("work");
                        }
                    });
                    f.get();
                    System.out.println("get result!");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        };
        int i = 0;
        do {
            service.submit(r);
        }while (i++ < 10);

    }
}