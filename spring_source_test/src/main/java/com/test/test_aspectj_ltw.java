package com.test;

/**
 * Created by apple on 17/05/2015.
 */


public class test_aspectj_ltw {
    public void test(){
        System.out.println("call the public method of "+test_aspectj_ltw.class.getName());
        testProtected();
    }

    protected void testProtected(){
        System.out.println("call the protected method of "+test_aspectj_ltw.class.getName());
        testPrivate();
    }
    private void testPrivate(){
        System.out.println("call the private method of "+test_aspectj_ltw.class.getName());
    }
}
