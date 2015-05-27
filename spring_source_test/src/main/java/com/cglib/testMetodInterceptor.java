package com.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by apple on 23/04/2015.
 */
public class testMetodInterceptor {
    public static void main(String args[]) {

        File f = new File(test.class.getResource("/").getFile());
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, f.getPath());

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(son.class);
        enhancer.setInterfaces(new Class<?>[]{EnhancerInterface.class});
        enhancer.setCallback(new MethodInterceptor() {

            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) {
                if (method.getDeclaringClass() == Object.class && method.getReturnType() == String.class) {
                    return "Hello cglib!";
                } else {
                    try {
                        //methodProxy.invoke(proxy,args);//do call this, cause stack overflow
                        return methodProxy.invokeSuper(proxy, args);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                return null;
            }
        });
        //createUsingReflection of enhancer
        son proxy = (son) enhancer.create(new Class<?>[]{int.class},new Object[]{10});
        proxy.publicMethodSon("test method interceptor");
        System.out.println(proxy.toString());
    }
}
