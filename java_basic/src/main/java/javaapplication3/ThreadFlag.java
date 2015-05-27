package javaapplication3;

public class ThreadFlag extends Thread {

    public   static int i=0;
    //run can not throws exception
    public void run() {
        for(int n=0;n<10;n++)
        i=i+1;
    }

    public static void main(String[] args) throws Exception {
        ThreadFlag thread[] = new ThreadFlag[100];
        for(int i=0;i<thread.length;i++)
        thread[i]=new ThreadFlag();
        for(int i=0;i<thread.length;i++)
        thread[i].start();
        for(int i=0;i<thread.length;i++)
        thread[i].join();
        System.out.println(ThreadFlag.i);
        System.out.println("线程退出!");
    }
}
