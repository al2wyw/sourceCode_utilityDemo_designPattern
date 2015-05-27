/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iostreamdate;
import java.util.*;
import java.io.*;
/**
 *
 * @author Administrator
 */
public class Main {
    public static void calculate(long t){
        long time1=0L;
        long time2=0l;
        long time=0l;
        Calendar c=Calendar.getInstance();
        time=c.getTimeInMillis();
        time1=(time-t)/1000;
        if(time1>59){
            time2=time1/60;
            time1=time1%60;
            System.out.println(time2+" minutes and "+time1+" seconds is consumed!");
        }else if(time1>3599){
            time2=time1/3600;
            time1=time1%3600;
            time1=time1/60;
            System.out.println(time2+" hours and "+time1+" minutes is consumed!");
        }
        else{
            System.out.println(time1+" seconds is consumed!");
        }
    }
    public static void test()throws Exception{
        Date d=new Date();
        Calendar c=Calendar.getInstance();
        long systime=System.currentTimeMillis();
        c.set(2010, 0, 1,0,0,12);
        System.out.println(d);
        Date dc=c.getTime();
        System.out.println(dc);
        System.out.println(d.getTime());
        System.out.println(c.getTimeInMillis());
        System.out.println(systime);
        Thread.sleep(1000);
        Date dd=new Date();
        System.out.println(dd);
    }
    public static void main(String[] args)throws Exception {
            //test();
            System.out.println("Enter the path of the file:");
            String str=null;
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            str=br.readLine();
            br.close();
            File f=new File(str);
            if(f.exists()){
                System.out.println("The file is existing! Proceed....");
            }
            String filen=f.getName();
            long size=f.length();
            long sizef=size/1024/100;
            FileInputStream fin=new FileInputStream(f);
            FileOutputStream fout=new FileOutputStream(filen);
            byte[] b=new byte[1024];
            int bb=0;
            int count=0;
            int n=1;
            long t=0l;
            Calendar begin=Calendar.getInstance();
            t=begin.getTimeInMillis();
            while((bb=fin.read(b))!=-1){
                    fout.write(b, 0, bb);
                    if(count==n*sizef){
                        calculate(t);
                        System.out.println(n+"% of file is copied! Proceed...");
                        n++;
                    }
                    count++;
            }
            fin.close();
            fout.close();
    }

}
