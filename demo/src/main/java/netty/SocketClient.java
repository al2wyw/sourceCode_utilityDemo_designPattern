package netty;

import org.apache.poi.util.IOUtils;
import sun.misc.SharedSecrets;
import sun.misc.Signal;
import sun.misc.SignalHandler;
import sun.nio.ch.Interruptible;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/17
 * Time: 9:48
 * Desc:
 */
public class SocketClient {

    public static void main(String[] args) {

        Signal.handle(new Signal("INT"), new SignalHandler() {
            @Override public void handle(Signal signal) {
                System.out.println(signal.getName());
            }
        });

        Socket s;
        try {
            s = new Socket(InetAddress.getByName("localhost"), 3455);
            System.out.println(InetAddress.getLocalHost());
            System.out.println("Connection is establishing!");
            //s.shutdownInput();
            final ObjectOutputStream dout = new ObjectOutputStream(s.getOutputStream());
            System.out.println("dout is established!");
            final ObjectInputStream din = new ObjectInputStream(s.getInputStream());
            System.out.println("din is established!");

            Thread write = new Thread(){
                public void run(){

                    BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
                    String x = "";
                    do{
                        try{
                            x = brr.readLine();
                            dout.writeObject(x);
                            dout.writeObject(Calendar.getInstance());
                            dout.flush();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }while(!x.equals("exit"));
                }
            };
            write.start();

            Thread read = new Thread(){
                public void run(){
                    String get = "";
                    try{
                        /**
                         "Thread-1@546" prio=5 tid=0xe nid=NA runnable
                         java.lang.Thread.State: RUNNABLE
                         at java.net.SocketInputStream.socketRead0(SocketInputStream.java:-1)
                         at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
                         at java.net.SocketInputStream.read(SocketInputStream.java:170)
                         at java.net.SocketInputStream.read(SocketInputStream.java:141)
                         at java.net.SocketInputStream.read(SocketInputStream.java:223)
                         at java.io.ObjectInputStream$PeekInputStream.peek(ObjectInputStream.java:2303)
                         at java.io.ObjectInputStream$BlockDataInputStream.peek(ObjectInputStream.java:2596)
                         at java.io.ObjectInputStream$BlockDataInputStream.peekByte(ObjectInputStream.java:2606)
                         at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1319)
                         at java.io.ObjectInputStream.readObject(ObjectInputStream.java:371)
                         at netty.SocketClient$2.run(SocketClient.java:50)
                         * */
                        while((get= (String) din.readObject())!= null){
                            System.out.println(get);
                            Calendar c = (Calendar) din.readObject();
                            System.out.println(c.getTime());
                        }
                        dout.writeObject("test");// server close test point
                        dout.flush();
                        System.out.println("test is written!");
                    }catch(Exception e){
                        e.printStackTrace();
                    }finally {
                        IOUtils.closeQuietly(dout);
                    }
                    System.out.println("test is end!");
                }
            };
            read.start();

            SharedSecrets.getJavaLangAccess().blockedOn(read, new Interruptible() {
                @Override
                public void interrupt(Thread t) {
                    System.out.println(t.getName() + " is interrupted!");
                    IOUtils.closeQuietly(din);
                }
            });
            //read.interrupt();//can not be interrupted, nio is interruptible
            System.out.println(read.isInterrupted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
