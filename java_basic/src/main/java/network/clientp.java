/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.*;
import java.io.*;

public class clientp {

    public static void main(String[] args) {
        Socket s;
        try {
            s = new Socket(InetAddress.getLocalHost(), 34555);
            System.out.println("Connection is establishing!");
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            InputStream din = s.getInputStream();
            dout.writeBytes("questions\n");
            byte[] b = new byte[1024];
            byte by;
            int i = 0;
            while ((by = (byte) din.read()) != '\n') {
                b[i] = by;
                i++;
            }
            String get = new String(b, 0, i);
            System.out.println(get);
            s.close();
            while(!s.isClosed());
            System.out.println(s.isInputShutdown());//without explicitly call shutdown function it is still false
            System.out.println(s.isOutputShutdown());
        } catch (UnknownHostException e) {
            System.out.println("1Errors");
        } catch (IOException b) {
            System.out.println("2Errors");
            b.printStackTrace();
        }
    }
}
