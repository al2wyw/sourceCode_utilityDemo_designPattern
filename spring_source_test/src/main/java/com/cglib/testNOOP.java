package com.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * Created by apple on 23/04/2015.
 */
public class testNOOP {
    public static void main(String args[]) {

        CglibUtils.setupOutPutDir();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(son.class);
        enhancer.setCallback(NoOp.INSTANCE);

        son proxy = (son) enhancer.create();
        proxy.publicMethodSon("test method interceptor");
        System.out.println(proxy.toString());
    }
}
