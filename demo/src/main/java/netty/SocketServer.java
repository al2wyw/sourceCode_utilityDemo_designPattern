package netty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/17
 * Time: 9:48
 * Desc: socket block时的线程状态都是runnable
 *       进程结束时会回收socket，tcp立马释放(对方的tcp也一起释放)
 */
public class SocketServer implements Runnable {

    private Socket s;

    public SocketServer(Socket t) {
        s = t;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3455);
            while (true) {
                Socket s = serverSocket.accept();
                SocketServer st = new SocketServer(s);
                Thread t = new Thread(st);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println("Connection is established!");
            //output first! input first at both server & client sides will block forever
            //because of readStreamHeader in ObjectInputStream and writeStreamHeader in ObjectOutputStream
            ObjectOutputStream dout = new ObjectOutputStream(s.getOutputStream());
            System.out.println("dout is established!");
            ObjectInputStream din = new ObjectInputStream(s.getInputStream());
            System.out.println("din is established!");
            String gett = "";
            while (!gett.equals("exit")) {

                String get = (String) din.readObject();
                gett = get;
                Calendar c = (Calendar) din.readObject();


                try {
                    Random r = new Random();
                    if (get.equals("questions")) {
                        if (r.nextInt(2) == 1) {
                            Thread.sleep(2000);
                        }
                        dout.writeObject("1.What is your name?");
                        dout.writeObject(c);
                        dout.flush();
                    } else {
                        if (r.nextInt(2) == 1) {
                            Thread.sleep(2000);
                        }
                        dout.writeObject("Wrong request!");
                        dout.writeObject(c);
                        dout.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Connection is closing!");
            s.close();//can shutdown output input! tcp is fin_wait2
            //s.shutdownOutput();//clientside's inputstream will be ended! tcp is fin_wait2
            //dout.writeObject("sdfsd");//can throw error!
            //s.shutdownInput(); //clientside's outputstream will be discarded! tcp is established
            //System.out.println(din.readObject());//can throw error!
            System.out.println("test is end!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
