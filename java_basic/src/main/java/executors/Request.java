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
public class Request {

    private final String name; //  委托者   
    private final int number;  // 请求编号   
    private static final Random random = new Random();

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {//执行请求   
        System.out.println(Thread.currentThread().getName() + " executes " + this);
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
        }
    }

    public String toString() {
        return "[ Request from " + name + " No." + number + " ]";
    }
}
