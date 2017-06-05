package demo;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/4/25
 * Time: 19:43
 * Desc: 实验证明因为锁而挂起的线程不会导致load高
 */
public class testCPULoad {

    public static void main(String[] args){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            new MyThread().start();
        }
    }

    private static class MyThread extends Thread{

        private static final Object LOCK = new Object();

        private static final AtomicInteger COUNT = new AtomicInteger(0);

        @Override
        public void run() {
            int count = COUNT.getAndIncrement();
            System.out.println(count);
            synchronized (LOCK){
                try {
                    Thread.sleep(1232131343);
                }catch (Exception e){

                }
            }
        }
    }
}
