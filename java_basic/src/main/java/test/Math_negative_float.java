/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.math.*;
/**
 *
 * @author Administrator
 */
public class Math_negative_float {
    public static void main(String[] args) {
        float f=-34.5f;
        System.out.println(Math.round(f));
        System.out.println(Math.round(-34.6f));
        System.out.println(Math.ceil(f));
        System.out.println(Math.floor(f));
        f=34.5f;
        System.out.println(Math.round(f));
        System.out.println(Math.round(34.4f));
        float h=34.56f;
        h=h+1;
        h+=1.4;
        //h=h+1.4;
    }
}
