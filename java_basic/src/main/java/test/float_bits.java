/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Administrator
 */
public class float_bits {
    public static void  main(String[] args){
        int i=0xBD800000;
        float f = Float.intBitsToFloat(i);
        System.out.println(String.format("%12.6f", f));
        System.out.println(String.format("%12.6f",Float.intBitsToFloat(0xC1480000)));
                System.out.println(String.format("%f",Float.intBitsToFloat(0xFF800000)));
                System.out.println(Integer.toHexString(Float.floatToIntBits(8.25f)));
                System.out.println(Float.MAX_VALUE);
                System.out.println(Float.MIN_VALUE);
        int m=10;
        float n=0.9f;
        float k=m+n;
        System.out.println(String.format("%.16f", k));
    }
}
