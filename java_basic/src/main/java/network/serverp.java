/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.*;
import java.io.*;

/**
 *
 * @author Administrator
 */
public class serverp {

    public static void main(String[] args) {
        ServerSocket ser;
        try {
            ser = new ServerSocket(34555);
            while (true) {
                Socket s = ser.accept();
                System.out.println("Connection is established!");
                InputStream din = s.getInputStream();
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                byte[] b = new byte[1024];
                byte by;
                int i = 0;
                while ((by = (byte) din.read()) != '\n') {
                    b[i] = by;
                    i++;
                }
                String get = new String(b, 0, i);
                if (get.equals("questions")) {
                    dout.writeBytes("1.What is your name?\n");
                } else {
                    dout.writeBytes("Wrong request!\n");
                }
                din.close();
                dout.close();
                System.out.println(s.isClosed());
                s.close();
                System.out.println(s.isClosed());
                System.out.println("exit!!!!");
            }
        } catch (Exception e) {
            System.out.println("Errors");
        }
    }
}
