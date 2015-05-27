/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;
import java.util.Date;
/**
 *
 * @author Administrator
 */
public class charset {
public static void main(String[] args)throws Exception{
    char[] c;
    //java.util.Arrays.fill(c,' ');
    byte[] b="刘付小问".getBytes("GBK");//gb2312
    byte[] bb="李扬".getBytes("GBK");
    byte[] bbb="李".getBytes("GBK");
    //System.out.println(new String(b,"GBK").concat(new String(c,0,c.length-new String(b,"GBK"). length()))+"!");
    //System.out.println(new String(bb,"GBK").concat(new String(c,0,c.length-new String(bb,"GBK").length()))+"!!!");
    //System.out.println(new String(bbb,"GBK").concat(new String(c,0,c.length-new String(bbb,"GBK").length()))+"!!!");
    //System.out.println(new String(b,"GBK").length());
    //System.out.println(new String(bb,"GBK").length());
    int i=10;//one chinese takes up two spaces!
    String s=new String(b,"GBK");
    c=new char[i];
    java.util.Arrays.fill(c,' ');
    System.out.println(s+new String(c)+"!");
    String ss=new String(bb,"GBK");
    c=new char[i+(s.length()-ss.length())*2];
    java.util.Arrays.fill(c,' ');
    System.out.println(ss+new String(c)+"!");
    String stro="ぃぁガ、、、、、";
    byte[] by=stro.getBytes("UTF-8");
    String str=new String(by,"UTF-8");
    //System.out.println(System.getProperty("file.encoding"));
    System.out.println(new String(stro.getBytes("gbk"),"gb2312"));
    java.io.PrintStream ps=new java.io.PrintStream(new java.io.FileOutputStream("qwe.txt"));
    ps.println(stro);
}
}
