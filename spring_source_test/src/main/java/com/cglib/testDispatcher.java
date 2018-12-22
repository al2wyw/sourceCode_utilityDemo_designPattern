package com.cglib;

import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;

/**
 * Created by apple on 23/04/2015.
 * 使用loadObject返回的object来调用对应的方法
 */
public class testDispatcher {
    public static void main(String args[]) {

        CglibUtils.setupOutPutDir();

        final son s = new son();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(son.class);
        enhancer.setCallback(new Dispatcher() {

            @Override
            public Object loadObject() throws Exception {
                return s;
            }
        });
        son proxy = (son) enhancer.create();
        proxy.publicMethodSon("test method interceptor");
        System.out.println(proxy.toString());
    }
}
