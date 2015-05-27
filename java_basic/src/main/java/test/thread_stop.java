/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;
class mythread implements Runnable{
    public synchronized void run(){
        System.out.println(Thread.currentThread().getName());
        try{
        Thread.sleep(System.currentTimeMillis());}catch(Exception e){}
    }
}
public class thread_stop {

    public static void main(String[] args) throws Exception{
         mythread my=new mythread();
         Thread m1=new Thread(my);
         Thread m2=new Thread(my);
         Thread m3=new Thread(my);
         Thread m4=new Thread(my);
         m1.start();
         m2.start();
         m3.start();
         m4.start();
         Thread.sleep(3000);
         m1.stop();//can release the lock!
    }

}
