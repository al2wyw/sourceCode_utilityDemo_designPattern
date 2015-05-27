/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

public class formattedString {
public static void main(String[] args) throws Exception{
    String c="sdf";
    String cc="zcx";
    int i=9;
    String s="李";
    String ss="李扬";
    System.out.println(String.format("%2$-9s",c,cc));//the same as setw()!!!the string will align to right!
    System.out.println(String.format("%1$10d", -34));
    System.out.println(String.format("%1$-10s", s)+"1");
    System.out.println(String.format("%1$-10s", ss)+"1");
    String str="sdddfg";
    System.out.println(str.replaceAll("d+", "h"));
    String[] fstr=str.split("\n");
    System.out.println(fstr[0]);
    java.io.PrintStream ps=new java.io.PrintStream(new java.io.FileOutputStream("sdf.txt")).format("%1$10s", "sdf");
    ps.print("vdf\n\n\n");
    double gg=-234.3375345;
    System.out.println(String.format("%1$.2f",gg));
}
}
