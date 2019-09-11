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
        Thread.sleep(1000);
        System.out.println("test");
        test1();
    }

    public void test1() throws Exception{
        Thread.sleep(1000);
        System.out.println("test1");
    }
}
