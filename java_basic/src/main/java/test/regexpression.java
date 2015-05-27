/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;
import java.util.regex.*;
/**
 *
 * @author Administrator
 */
public class regexpression {

public static void main(String[] args){
        Pattern p=Pattern.compile("199\\d|200\\d");//| just seperates the expression,[(1922)(2022)] does not work!
        Matcher m=p.matcher("2001");
        System.out.println(m.matches());
        p=Pattern.compile("[(199\\d)(200\\d)]");
        m=p.matcher("2001");
        System.out.println(m.matches());
        int a=0;
        int b=1;
        int c=10;
        int d=100;
        int h=a<b?c:d>a?c+1:d+1;
        System.out.println(h);
}
}
