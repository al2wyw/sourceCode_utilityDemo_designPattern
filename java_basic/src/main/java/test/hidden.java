/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

class hidden_base{
    public static int count(int i){
        System.out.println("hidden_base");
        return i;
    }
}
class hidden_dre extends hidden_base{
    public static int count(long i){
        System.out.println("hidden_dre");
        return 1;
    }
}
/**
 *
 * @author Administrator
 */
public class hidden {
    public static void main(String[] args){
        hidden_base test=new hidden_dre();
        ((hidden_dre)test).count(10l);
        byte h = 0x11;
        byte a = (byte)(h&h);
    }
}
