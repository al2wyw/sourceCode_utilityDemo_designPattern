/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Administrator
 */
class myTask implements Runnable {
    private boolean flag = true;
    
    public synchronized void run(){
        if(flag){
            reset();
            try{
            Thread.sleep(3000);
            }catch(Exception e){
                e.printStackTrace();
            }
            int i=10;
            //i=10/0; //release the lock
            return ; // release the lock
            //System.exit(1);//the whole process will stop
        }
        while(true){
             try{
            Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
             System.out.println(Thread.currentThread().getName());
        }
    }
    public void reset(){
        flag = !flag;
    }
    public boolean get(){
        return flag;
    }
}
public class thread_release {
    public static void main(String[] args) throws Exception{
        myTask t= new myTask();
        Thread t1=new Thread(t);
        Thread t2=new Thread(t);
        t1.start();
        Thread.sleep(1000);
        t2.start();
    }
}
