/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
/**
 *
 * @author Administrator
 */
public class hidden_static_fun {
    public static void main(String[] args){
        hidden_base test=new hidden_dre();
        ((hidden_dre)test).count(10l);
        byte h = 0x11;
        byte a = (byte)(h&h);
    }
}
