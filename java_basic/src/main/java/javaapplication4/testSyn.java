/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//synchronize let thread obtain the object lock for the part locked by synchronize
//1.  synchronized关键字不能被覆盖。覆盖时要再次声明。不然覆盖后的方法失去同步效果。
//2.  在定义接口方法时不能使用synchronized关键字。
//3.  构造方法不能使用synchronized关键字，但可以使用下节要讨论的synchronized块来进行同步。
//4.  synchronized可以自由放置。
//5. synchronized修辞 non-static function时等同于synchronized（this）,而修辞static function时等同于synchronized（Class.class），有区别！
package javaapplication4;

class MyThread implements Runnable {

    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "has run already!");
            synchronized (this) {//when one thread run this block, the other thread still can run the statement above
                try {
                    System.out.println(Thread.currentThread().getName() + "will sleep!");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                System.out.println(Thread.currentThread().getName() + "Throw the lock!");
            }
        }
    }
}
class base implements Runnable{
    public synchronized void test(){
       try {
                    System.out.println(Thread.currentThread().getName() + "will sleep!");
                    Thread.sleep(3000);
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().getName() + "Throw the lock!");
                } catch (InterruptedException e) {
                }
    }
    public void run(){
        while(!Thread.interrupted()){
        test();
        }
        System.out.println(Thread.currentThread().getName() + "is " +Thread.currentThread().isInterrupted());
    }
}
class derive extends base{
    private final static int i;//可以为空白final
    static{
        i = 10;
    }
}
public class testSyn {

    public static void main(String[] args) throws Exception {
        /*MyThread t = new MyThread();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        Thread t4 = new Thread(t);
        Thread t5 = new Thread(t);
        Thread t6 = new Thread(t);
        Thread t7 = new Thread(t);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();*/
        derive t = new derive();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        Thread t4 = new Thread(t);
        Thread t5 = new Thread(t);
        Thread t6 = new Thread(t);
        Thread t7 = new Thread(t);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
    }
}
