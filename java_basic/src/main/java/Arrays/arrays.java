/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Arrays;
import java.util.Arrays;
public class arrays {
public static void main(String[] args){
    String s="qwerty";
    String ss="asdfghhh";
    char[] a=s.toCharArray();
    char[] b=ss.toCharArray();
    System.out.println(b);
    System.arraycopy(a, 0, b, 0, a.length);//different with copyOf()
    System.out.println(b);
    b=Arrays.copyOf(a,a.length);
    System.out.println(b);
    System.out.println(Arrays.equals(a, "qwerty".toCharArray()));
    Arrays.fill(b, 'A');
    System.out.println(b);
    int[] c={1,6,23,8,14,2,53};
    Arrays.sort(c);
    System.out.println(c[5]);
    System.out.println(Arrays.binarySearch(c, 14));
}
}
