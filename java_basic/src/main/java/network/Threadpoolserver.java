package network;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class server implements Runnable {

    private Socket s;
    private ArrayList<ObjectOutputStream> list;
    private ObjectOutputStream o;
    private String name;
    public server(Socket t,ArrayList<ObjectOutputStream> l,ObjectOutputStream a) {
        s = t;
        list = l;
        o = a;
    }

    public void run() {
        try {
            ObjectInputStream din = new ObjectInputStream(s.getInputStream());
            name = (String)din.readObject();
            String get = "";
            while (true) {
                get = (String) din.readObject();
                Calendar c = (Calendar) din.readObject();
                if(get.equals("EXIT")){
                    o.writeObject(name);
                    o.writeObject("EXIT");
                    o.writeObject(c);
                    o.flush();
                    break;
                }
                 
                synchronized(Threadpoolserver.class){
                ListIterator<ObjectOutputStream> it = list.listIterator();
                while (it.hasNext()) {
                    ObjectOutputStream dout1 = it.next();
                    dout1.writeObject(name);
                    dout1.writeObject(get);
                    dout1.writeObject(c);
                    dout1.flush();
                }
                }
            }
            s.shutdownOutput();
            s.shutdownInput();
            s.close();
            System.out.println("close!");
            synchronized(Threadpoolserver.class){
                list.remove(o);
            }
        } catch (IOException e) {
            System.out.println(" IO Errors");
        }catch (ClassNotFoundException e){
            System.out.println(" Class Errors");
        }
    }
}

public class Threadpoolserver {

    private static ArrayList<ObjectOutputStream> list = new ArrayList<ObjectOutputStream>();

    public static void main(String[] args) {
        try {
            ServerSocket ser = new ServerSocket(3455);
            ExecutorService exe = Executors.newFixedThreadPool(10);
            while (true) {
                Socket s = ser.accept();
                ObjectOutputStream o = new ObjectOutputStream(s.getOutputStream());
                synchronized (Threadpoolserver.class) {
                    list.add(o);
                }
                System.out.println(s.getInetAddress() + " is connected!");
                server st = new server(s, list,o);
                exe.submit(st);
            }
        } catch (Exception e) {
            System.out.println("Errors");
        }
    }
}
