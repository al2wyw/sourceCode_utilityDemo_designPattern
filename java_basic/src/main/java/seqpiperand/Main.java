/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package seqpiperand;
import java.io.*;
import java.util.*;

class Mythread implements Runnable{
    private int i;
    public Mythread(int h){
        i=h;
    }
    public void run(){
        int b=0;
        int e=0;
        switch(i){
            case 1: b=1;e=20;break;
            case 2: b=21;e=40;break;
            case 3: b=41;e=74;
        }
        for(int h=b;h<e+1;h++){
        try{

        File inf=new File("part"+h+".mp4");
        File f=new File("K:\\123.mp4");//在每个线程里打开文件读写
        FileInputStream in=new FileInputStream(inf);
        RandomAccessFile rf=new RandomAccessFile(f,"rw");
        rf.seek((h-1)*1024*1024);
        int len=0;
        byte[] bb=new byte[1024];
        while((len=in.read(bb))!=-1){
            rf.write(bb,0,len);
        }
        System.out.println(Thread.currentThread().getName()+"is writing!");
        }catch(Exception ex){}
        }
    }
}
public class Main {
    public static void test()throws Exception{
        File f=new File("E:\\少女组.mp4");
        if(!f.exists()){
            f.createNewFile();
        }else{
            System.out.println("file is existing!");
        }
        FileInputStream fin=new FileInputStream(f);
        System.out.println(f.length()/(1024*1024));
        byte[] b=new byte[1024*1024];
        int len=0;
        int n=1;
        while((len=fin.read(b))!=-1){
            FileOutputStream fout=new FileOutputStream("part"+n+".mp4");
            fout.write(b, 0, len);
            n++;
        }
        for(int i=2;i<n;i++){
        File tf=new File("part"+(i-1)+".mp4");
        File tf1=new File("part"+i+".mp4");
        File path=new File("F:\\temp");
        File tf11=File.createTempFile("asd", "mp4",path);
        SequenceInputStream sin=new SequenceInputStream(new FileInputStream(tf),new FileInputStream(tf1));
        FileOutputStream fout=new FileOutputStream(tf11);
        while((len=sin.read(b))!=-1){
            fout.write(b, 0, len);
        }
        sin.close();
        fout.close();
        FileInputStream tfin=new FileInputStream(tf11);
        FileOutputStream tfout=new FileOutputStream(tf1);
        while((len=tfin.read(b))!=-1){
            tfout.write(b, 0, len);
            System.out.println(i);
        }
        tfin.close();
        tfout.close();
        tf11.delete();
        /*if(ff.renameTo(tf1)){
        System.out.println(ff);//已经存在目标文件，则无法改名！
        }*/
        }
    }
    public static void test1()throws Exception{
               File f=new File("E:\\少女组.mp4");
        FileInputStream fin=new FileInputStream(f);
        System.out.println(f.length()/(1024*1024));
        byte[] b=new byte[1024*1024];
        int len=0;
        int n=1;
        while((len=fin.read(b))!=-1){
            FileOutputStream fout=new FileOutputStream("part"+n+".mp4");
            fout.write(b, 0, len);
            n++;
        }
        FileOutputStream fout=new FileOutputStream("123.mp4");
        for(int i=1;i<n;i++){
            FileInputStream fin1=new FileInputStream("part"+i+".mp4");
            while((len=fin1.read(b))!=-1){
                fout.write(b,0,len);
            }
        }
    }
    public static void main(String[] args) throws Exception{
        File f=new File("E:\\少女组.mp4");
        FileInputStream fin=new FileInputStream(f);
        System.out.println(f.length()/(1024*1024));
        byte[] b=new byte[1024*1024];
        int len=0;
        int n=1;
        while((len=fin.read(b))!=-1){
            FileOutputStream fout=new FileOutputStream("part"+n+".mp4");
            fout.write(b, 0, len);
            n++;
        }
        Mythread m1=new Mythread(1);//three threads write the data into the storage;
        Mythread m2=new Mythread(2);
        Mythread m3=new Mythread(3);
        Thread t1=new Thread(m1,"Thread1");
        Thread t2=new Thread(m2,"Thread2");
        Thread t3=new Thread(m3,"Thread3");

        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
    }
}
