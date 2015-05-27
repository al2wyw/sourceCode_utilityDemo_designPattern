package network;

import java.net.*;
import java.io.*;
import java.util.Calendar;
/**
 *
 * @author Administrator
 */
public class clip {

    public static void main(String[] args) {
        Socket s;
        try {
            s = new Socket(InetAddress.getByName("localhost"), 3455);
            System.out.println(InetAddress.getLocalHost());
            System.out.println("Connection is establishing!");
            final ObjectOutputStream dout = new ObjectOutputStream(s.getOutputStream());
            final ObjectInputStream din = new ObjectInputStream(s.getInputStream());
                new Thread(){
                    public void run(){
                        
                        BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
                        String x = "";
                        do{
                            try{
                            x = brr.readLine();
                            dout.writeObject(x);
                            dout.writeObject(Calendar.getInstance());
                            dout.flush();
                            }catch(IOException e){}
                        }while(!x.equals("exit"));
                    }
                }.start();
                
                new Thread(){
                    public void run(){
                        String get = "";
                        try{
                        while((get= (String) din.readObject())!= null){
                            System.out.println(get);
                            Calendar c = (Calendar) din.readObject();
                            System.out.println(c.getTime());
                        }
                        dout.writeObject("test");
                        dout.flush();
                        System.out.println("test is written!");
                        }catch(Exception e){}
                    }
                }.start();
                
        } catch (Exception e) {
            System.out.println("Errors");
        }
    }
}
