package com.cglib;

import java.io.File;
import java.lang.reflect.*;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.*;
import net.sf.cglib.proxy.InvocationHandler;

public class testInvocationHandler {
    public static void main(String args[]) {

        File f = new File(test.class.getResource("/").getFile());
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, f.getPath());

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
