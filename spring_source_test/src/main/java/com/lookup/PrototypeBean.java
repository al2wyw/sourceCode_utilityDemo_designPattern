package com.lookup;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2018/12/1
 * Time: 21:55
 * Desc:
 */
public class PrototypeBean {

    private long timestamp = System.currentTimeMillis();

    public void action(){
        System.out.println(this + " " + timestamp);
    }
}