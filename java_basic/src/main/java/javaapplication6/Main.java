/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

class MutliThread implements Runnable {

    private int ticket = 0;
    public final String s = "";//final is better for synchronized! s="sdfg", the object is changed!

    public void run() {
        while (true) {
            synchronized(s){// s can not be primitive type variable, must be object type
            if(ticket<=100000)
            {
                    System.out.println(Thread.currentThread().getName() + "now sum is: " + ticket++);
                    try{
                        s.wait();
                    }catch(InterruptedException e){
                        
                    }
                    System.out.println(Thread.currentThread().getName() + " wake up !");
                    s.notify();
                }
                }
            }
        }
    }

public class Main {

    public static void main(String args[]) throws Exception {
        MutliThread m = new MutliThread();
        Thread t1 = new Thread(m);
        Thread t2 = new Thread(m);
        Thread t3 = new Thread(m);
        Thread t4 = new Thread(m);
        Thread t5 = new Thread(m);
        Thread t6 = new Thread(m);
        Thread t7 = new Thread(m);
        Thread t8 = new Thread(m);

 
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        Thread.sleep(1000);
        synchronized(m.s){
            m.s.notifyAll();
        }
    }
}
