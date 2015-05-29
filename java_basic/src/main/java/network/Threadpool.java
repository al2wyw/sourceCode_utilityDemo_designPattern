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
        try{
        	Thread.sleep(10000);
        }catch(InterruptedException e){
        	
        }
    }
}
public class Threadpool {
    public static void main(String args[]){
        ExecutorService exe = Executors.newFixedThreadPool(10);
        int i = 0;
        while(i++<20){
        myThread my = new myThread();
        Future<String> f = exe.submit(my, "done");
        }
        //my.flag = false;
        System.out.println("try to get ...");
        
       /* try{
        System.out.println(f.get());
        }catch(Exception e){}*/
        
        exe.shutdown();
        try {
			exe.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        exe.shutdownNow();
    }
}
