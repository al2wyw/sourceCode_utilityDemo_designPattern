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

    public long test1() throws Exception{
        long time = 1000;
        try {
            Thread.sleep(time);
            int h = 1/0;
        }catch (Exception e){
            System.out.println("test1 error:" + e);
            throw e;
        }
        System.out.println("test1");
        return Long.MAX_VALUE;
    }

    public Test testGen() throws Exception{
        System.out.println("testGen");
        return new Test();
    }

    public synchronized void testSyn() throws Exception{
        System.out.println("testSyn");
    }
}
