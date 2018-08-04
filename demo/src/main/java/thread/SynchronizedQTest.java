package thread;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/7/23
 * Time: 19:53
 * Desc:
 */
public class SynchronizedQTest {

    public static void main(String[] args) throws Exception{
        SynchronousQueue<String> queue = new SynchronousQueue<>(true);
        System.out.println(Thread.currentThread().isDaemon());
        put(queue, "test");
        put(queue,"test1");
        put(queue,"test2");
        put(queue,"test3");
        Thread.sleep(1000);
        take(queue);
    }

    public static void put(SynchronousQueue<String> queue,String s){
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(s);
                    queue.put(s);
                    System.out.println(s + " done");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void take(SynchronousQueue<String> queue){
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("take start");
                    String s = queue.take();
                    System.out.println(s + " take");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
