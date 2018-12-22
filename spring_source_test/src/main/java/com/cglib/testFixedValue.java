package com.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

/**
 * Created by apple on 23/04/2015.
 * 所有方法直接返回一个定值
 */
public class testFixedValue {
    public static void main(String args[]) {

        CglibUtils.setupOutPutDir();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(son.class);
        enhancer.setCallback(new FixedValue() {

            @Override
            public Object loadObject() throws Exception {
                return "test";
            }
        });
        son proxy = (son) enhancer.create();
        proxy.publicMethodSon("test method interceptor");
        System.out.println(proxy.toString());
    }
}
