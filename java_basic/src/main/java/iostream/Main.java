/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iostream;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Administrator
 */
class MyThread implements Runnable{
    private static int ticket=100;
    protected  boolean flag=true;
    public void run(){
        Random r=new Random();//this method seems work well!
        while(flag){
            synchronized(this){
                if(ticket>0){
            System.out.println(Thread.currentThread().getName()+" "+ticket);
            ticket--;
            //Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            if(r.nextInt(2)==1){
                this.notify();
                try{this.wait();
                }catch(Exception e){
                }
            }
            }
            }
        }
    }
}
class MyTimerTask extends TimerTask{
    public void run(){
        System.out.println("the task is operated!");
    }
}
public class Main {

    public static void main(String[] args) throws Exception {//output class all contain flush
        /*
        FileOutputStream stdout =new FileOutputStream("filewriter.txt");
        DataOutputStream dout=new DataOutputStream(stdout);//skipBytes();no write.String(),string is not foundational data!
        dout.writeInt(765);
        dout.writeBytes("dsfs\n");
        dout.close();
       BufferedInputStream stdin =new BufferedInputStream(new FileInputStream("filewriter.txt"));
       DataInputStream din=new DataInputStream(stdin);//no read.String()
       
        int a=din.readInt();
        System.out.println(a);
        Class.forName("com.mysql.jdbc.Driver").newInstance();//newInstance() is always forgotten! f er get not f o get
        String str="jdbc:mysql://localhost:3306/test?user=test&password=test";
        //String user="test";
        //String password="test";
        Connection conn=DriverManager.getConnection(str);//(str,user,password)
        Statement stmt=conn.createStatement();
        boolean flag=false;
        flag=stmt.execute("select make,year from cars where carid=2;");
        if(flag)
       {
        ResultSet rs=stmt.executeQuery("select make,year from cars where carid=2;");
        while(rs.next())
        {
        String s=rs.getString(1);//rs position begins from 1 not 0!
        int year=rs.getInt(2);
        year++;
        System.out.println(s);
        System.out.println(year);
        }
        }
        //stmt.executeUpdate("insert into cars values(null,'BMW','rollover',1999,135000,'black','six dang',3.5,'huge','#FG3493',4000,1,'air-conditioner,qidie,coloured body',null)");
        //stmt.executeUpdate("create table teacher(id int(3) not null primary key auto_increment,name varchar(30) not null, sex varchar(30) not null,salary int(6))");
       stmt.close();
       PreparedStatement pstmt=conn.prepareStatement("select * from teacher where name=?");
       BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
       System.out.println("Enter the name of the teacher:");
       str=in.readLine();//reutrn null
       pstmt.setString(1, str);
       ResultSet rs=pstmt.executeQuery();
       rs.next();
       System.out.println(rs.getString(3));
       pstmt.close();
       CallableStatement cstmt=conn.prepareCall("{call ts(?)}");
       cstmt.setInt(1,7000);
       cstmt.executeQuery();
       cstmt.close();
       */
       /*int by=0;
       char ch=0;
       byte[] b=new byte[1024];
       char[] c=new char[1024];
       by=System.in.read(b);//return -1
       System.out.println(by);
       System.out.println(new String(b,2,by-3));
       //by=System.in.read();//return -1
       //System.out.println(by);
       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       by=br.read(c);
       System.out.println(new String(c,1,by-2));*/
     //System.out.println((char)by);
       /*
       FileWriter fout=new FileWriter("abc.txt");
       BufferedInputStream bin=new BufferedInputStream(new FileInputStream("1.jpg"));
       BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream("2.jpg"));
       fout.write(by);
       fout.close();
       while((by=bin.read())!=-1){
           bout.write(by);
       }
       bin.close();
       bout.close();
     */
       File cf=new File("G:\\a\\b");
       cf.mkdirs();//cf.mkdir();
       if(cf.isDirectory()){
           System.out.println("The directory is existing!");
       }
       File cff=new File("G:\\a\\b\\sdf.txt");
       cff.createNewFile();//new empty file!
       cff.deleteOnExit();//cff.delete() returns boolean
       File f=new File("G:\\Hanai Meisa\\SMBD-09かAAA Hanai Meisa .m2ts");
       System.out.println(f.getName());
       System.out.println(f.getParent());
       System.out.println(f.getPath());
       System.out.println(f.length());
       if(f.setReadOnly()){
            System.out.println("it is read only!");
       }
       if(f.setReadable(true)){
           System.out.println("it can be read !");
       }
       FileInputStream fin=new FileInputStream(f);
       System.out.println(fin.available());//it depends on my memory and the thread block
       Thread.sleep(5000);
       /*MyThread myt1=new MyThread();
       MyThread myt2=new MyThread();
       Thread t1=new Thread(myt1,"myThread1");
       Thread t2=new Thread(myt2,"myThread2");*///the static data doesnt work well!
       MyThread myt=new MyThread();
       Thread t1=new Thread(myt,"myThread1");
       Thread t2=new Thread(myt,"myThread2");
       t1.start();
       t2.start();
       Thread.sleep(1000);
       Timer t=new Timer();
       t.schedule(new MyTimerTask(), 5000);
       Thread.sleep(5001);//if timeout is 5000, the task can not work!
       t.cancel();
       synchronized(myt){
           myt.notifyAll();
       }
       myt.flag=false;
    }
}
