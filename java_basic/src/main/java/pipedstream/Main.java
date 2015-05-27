/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//字符流没有 available! 字节流才有！
package pipedstream;

import java.io.*;
import java.util.*;

class Sender extends Thread {
    protected boolean flag=true;
    private Random rand = new Random();
    private PipedWriter out = new PipedWriter();

    public PipedWriter getPipedWriter() {
        return out;
    }

    public void run() {
        while (flag) {
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                try {
                    String s=br.readLine();//readline to string!
                    char[] c=s.toCharArray();//getbytes()
                    out.write(c);
                } catch (Exception e) {
                }
            
        }
    }
}

class Receiver extends Thread {
    protected boolean flag=true;
    private PipedReader in;
    private Random rand=new Random();
    public Receiver(Sender sender) throws IOException {
        in = new PipedReader(sender.getPipedWriter());
    }

    public void run() {
        try {
            while (flag) {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName() + "Read: "
                            + (char) in.read());
                    int a=rand.nextInt(2);
                    if(a==2||a==1){
                    try {
                        this.notify();
                        this.wait();
                    } catch (Exception e) {
                    }
                }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {

    public static void main(String[] args) throws
            Exception {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        Thread t1 = new Thread(receiver, "t1");
        Thread t2 = new Thread(receiver, "t2");
        Thread t3 = new Thread(receiver, "t3");
        sender.start();
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(10000);
        sender.flag=false;
        receiver.flag=false;
        synchronized(receiver){
            receiver.notify();
        }
    }
}
