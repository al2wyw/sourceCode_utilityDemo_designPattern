/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;
import java.util.concurrent.*;
/**
 *
 * @author Administrator
 */
class myThread implements Runnable{
    protected boolean flag = true;
    public void run(){
        while(flag);
    }
}
public class Threadpool {
    public static void main(String args[]){
        ExecutorService exe = Executors.newFixedThreadPool(10);
        myThread my = new myThread();
        Future<String> f = exe.submit(my, "done");
        //my.flag = false;
        System.out.println("try to get ...");
        
        try{
        System.out.println(f.get());
        }catch(Exception e){}
        exe.shutdown();
        exe.shutdownNow();
    }
}
