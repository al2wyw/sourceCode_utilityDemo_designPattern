package thread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/3/30
 * Time: 15:39
 * Desc: failed!!!!
 *
 */
public class testVolatile {

    private long lo = -1L;

    private AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String args[]) throws Exception{
        //i5 4200u 2 core 4 threads : 4
        System.out.println(Runtime.getRuntime().availableProcessors());
        final testVolatile test = new testVolatile();
        new Thread(){
            @Override
            public void run() {
                test.test1();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                test.test2();
            }
        }.start();
        Thread.sleep(300);
        test.start();
        test.print();
        Thread.currentThread().join(400);
        test.print();
    }

    private void start(){
        flag.set(false);
    }

    private void print(){
        System.out.println("start: " + lo);
    }

    private void test1(){
        while(flag.get());
        int i = 0;
        while(i++<99999999){
            lo++;
            lo = 314234L;
        }
        System.out.println("Thread 1: " + lo);
    }
    private void test2(){
        while(flag.get());
        int i = 0;
        while(i++<99999999){
            lo++;
            lo = -346435L;
        }
        System.out.println("Thread 2: " + lo);
    }
}
