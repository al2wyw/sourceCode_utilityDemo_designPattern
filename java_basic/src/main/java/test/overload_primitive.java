/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Administrator
 */
public class overload_primitive {
void f1(char x) { System.out.println("f1(char)"); }
void f1(byte x) { System.out.println("f1(byte)"); }
void f1(short x) { System.out.println("f1(short)"); }
void f1(int x) { System.out.println("f1(int)"); }
void f1(long x) { System.out.println("f1(long)"); }
void f1(float x) { System.out.println("f1(float)"); }
void f1(double x) { System.out.println("f1(double)"); }

void f6(float x) { System.out.println("f6(float)"); }
void f6(double x) { System.out.println("f6(double)"); }

    public static void main(String[] args){
        overload_primitive test=new overload_primitive();
        test.f1(5);
        test.f6(5);
    }
}
