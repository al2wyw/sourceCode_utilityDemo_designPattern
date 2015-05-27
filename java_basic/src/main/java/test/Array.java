/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Random;
import java.io.*;

class to {

    private int i = 10;

    public to() {
    }

    public to(int j) {
        i = j;
    }

    public int geti() {
        return i;
    }

    public void seti(int j) {
        i = j;
    }

    public String toString() {
        return i + "my to";
    }
}

public class Array {

    public void prin(char[] a) {//[]写在前面或者后面都可以!
        System.out.println(a[0]);
    }

    public static void change(int i) {
        i = 10;
    }

    public static void change(String i) {
        System.out.println(i);
        i += "what";
        System.out.println(i);
    }

    public static void change(to i) {
        i.seti(1000);
    }

    public static void main(String[] args) {
        char a[] = {'s', 'd'};//数组初始化不能声明大小 char a[2] is wrong!!!
        char xxxx[]=new char[]{'s','f','d','g'};
        //如果没有明确指定初始化值，数组值就会自动置为零
        System.out.print(new Object[]{new Integer(1), new Double(13.23), new Long(32)});//奇怪
        Random rand = new Random();
        // 3-D array with varied-length vectors:
        int[][][] a3 = new int[rand.nextInt(7)][][];
        for (int i = 0; i < a3.length; i++) {
            a3[i] = new int[rand.nextInt(5)][];
            for (int j = 0; j < a3[i].length; j++) {
                a3[i][j] = new int[rand.nextInt(5)];
            }
        }
        int n;
        char input[] = new char[10];
        String s;
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        try {
            s = read.readLine();
            n = Integer.parseInt(s);
            System.out.println(n);
            int[][] aa = new int[n][];
            System.out.println(aa.length);
            for (int i = 0; i < aa.length; i++) {
                aa[i] = new int[n];
                for (int j = 0; j < aa[i].length; j++) {
                    aa[i][j] = 1;
                }
            }
        } catch (IOException io) {
        }
        int m = 1000;
        Array.change(m);
        System.out.println(m);
        String str = "fuck";
        Array.change(str);
        System.out.println(str);
        to t = new to();
        Array.change(t);
        System.out.println(t);
        int[] a1 = {1, 2, 3, 4, 5};
        int[] a2;
        a2 = a1;
        for (int i = 0; i < a2.length; i++) {
            a2[i]++;
        }
        for (int i = 0; i < a1.length; i++) {
            System.out.println("a1[" + i + "] = " + a1[i]);
        }
    }
}

abstract class person {

    private int i;

    public abstract void seti();
}
