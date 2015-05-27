/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executors;

import java.util.*;

/**
 *
 * @author Administrator
 */
public class ClientThread implements Runnable {
 
    private String name;

    public ClientThread(String s){
        name = s;
    }
    public void run() {
        try {
            System.out.println(name+" is running his application!");
            Thread.sleep(3000);
            System.out.println(name+" is exiting his application!");
        } catch (InterruptedException e) {
        }
    }
}
