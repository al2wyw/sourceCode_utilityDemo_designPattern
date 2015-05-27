/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;


public class MyThread1 extends Thread {

    public String methodName;

    public static void method(String s) {
        System.out.println(s);
        while (true);
    }

    public  void method1() {
        synchronized(this){
        method("非静态的method1方法");
    }
    }

    public  void method2() {
        synchronized(this){
        method("非静态的method2方法");
    }
    }

    public static  void method3() {
        synchronized(MyThread1.class)
        {
        method("静态的method3方法");
    }
    }
    public static  void method4() {
        synchronized(MyThread1.class){
        method("静态的method4方法");
    }
    }
    public void run() {
        try {
            getClass().getMethod(methodName).invoke(this);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws Exception {
        MyThread1 myThread1 = new MyThread1();
        for (int i = 1; i <= 4; i++) {
            myThread1.methodName = "method" + String.valueOf(i);
            new Thread(myThread1).start();
            System.out.println(myThread1.getClass());
            sleep(1000);
            myThread1.setPriority(10);//1~10
            System.out.println(myThread1.getPriority());
        }
    }
}
