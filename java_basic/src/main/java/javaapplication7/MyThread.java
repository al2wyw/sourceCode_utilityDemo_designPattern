/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication7;

/**
 *
 * @author Administrator
 */
class Singleton {

    private static Singleton sample;

    private Singleton() {
    }

    public synchronized static Singleton getInstance() {
        if (sample == null) {
            Thread.yield();
            sample = new Singleton();
        }
        return sample;
    }
}

public class MyThread extends Thread {

    public void run() {
        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton.hashCode());
    }

    public static void main(String[] args) {
        Thread threads[] = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}
