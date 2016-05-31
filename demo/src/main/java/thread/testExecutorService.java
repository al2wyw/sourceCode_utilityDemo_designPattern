package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by johnny.ly on 2016/1/20.
 */
public class testExecutorService {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future res = executorService.submit(new Runnable() { //submit will not throw exception, while execute will throw
            public void run() {
                System.out.println(1 / 0);
            }
        });
        try {
            res.get();//this will throw exception
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        executorService.shutdown();//once execute/submit task, must shutdown(even if there is exception), otherwise main can not end

        /*
        new Thread(new Runnable() {
            public void run() {
                System.out.println(1 / 0);//exception will terminate thread
            }
        }).start();
        */
        System.out.println("test");
    }
}
