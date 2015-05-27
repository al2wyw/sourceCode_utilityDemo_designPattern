/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * The ObjectInputStream constructor blocks until it completes reading the serialization stream header.
 * Code which waits for an ObjectInputStream to be constructed before creating the corresponding 
 * ObjectOutputStream for that stream will deadlock, since the ObjectInputStream constructor will 
 * block until a header is written to the stream,
 * and the header will not be written to the stream until the ObjectOutputStream constructor executes.
 */
class servers implements Runnable {

    private Socket s;

    public servers(Socket t) {
        s = t;

    }

    public void run() {

        try {
            System.out.println("Connection is established!");
            ObjectOutputStream dout = new ObjectOutputStream(s.getOutputStream());//output first!
            ObjectInputStream din = new ObjectInputStream(s.getInputStream());
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
                }
            }
            s.close();//can shutdown output input
//            s.shutdownOutput();//clientside's inputstream will be ended!
//            //dout.writeObject("sdfsd");//can throw error!
//            s.shutdownInput(); //clientside's outputstream will be discarded!
//            //System.out.println(din.readObject());//can throw error!
        } catch (IOException e) {
            System.out.println("IOErrors");
        } catch(ClassNotFoundException e){
             System.out.println("class Errors");
        }
    }
}

public class serp {

    public static void main(String[] args) {
        try {
            ServerSocket ser = new ServerSocket(3455);
            while (true) {
                Socket s = ser.accept();
                servers st = new servers(s);
                Thread t = new Thread(st);
                t.start();
            }
        } catch (Exception e) {
            System.out.println("Errors");
        }
    }
}
