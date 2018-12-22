package com.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by apple on 23/04/2015.
 * InvocationHandler 就是jdk的InvocationHandler, 实现的机制是一模一样的!!!
 * 由于jdk代理实现的都是接口，需要在InvocationHandler的实现里显性调用被代理的实例，如这里的son s
 */
public class testInvocationHandler {
    public static void main(String args[]) {

        CglibUtils.setupOutPutDir();

        final son s = new son();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(son.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getDeclaringClass() == Object.class && method.getReturnType() == String.class) {
                    return "Hello cglib!";
                } else {
                    try {
                        return method.invoke(s, args);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
        son proxy = (son) enhancer.create();
        proxy.publicMethodSon("test invocation handler");
        System.out.println(proxy.toString());
    }
}
