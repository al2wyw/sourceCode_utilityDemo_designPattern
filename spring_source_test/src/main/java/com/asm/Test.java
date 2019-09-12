package com.asm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/9/11
 * Time: 17:29
 * Desc:
 */
public class Test {

    public void test() throws Exception{
        long time = 1000;
        Thread.sleep(time);
        String print = "test";
        System.out.println(print);
        test1();
    }

    public void test1() throws Exception{
        long time = 1000;
        Thread.sleep(time);
        //int h = 1/0;
        System.out.println("test1");
    }
}
