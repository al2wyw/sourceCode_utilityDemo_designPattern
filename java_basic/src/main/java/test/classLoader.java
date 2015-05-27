/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Administrator
 */
public class classLoader {
    public static void main(String[] args){
        classLoader.class.getClassLoader().getResource("test");
    }
}
