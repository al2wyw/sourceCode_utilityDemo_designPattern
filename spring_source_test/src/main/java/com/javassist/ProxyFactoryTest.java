package com.javassist;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2018/12/22
 * Time: 16:54
 * Desc:
 */
public class ProxyFactoryTest {
    public static void main( String args[] ) throws Exception{
        ProxyFactory factory = new ProxyFactory();
        factory.writeDirectory = System.getProperty("user.dir") + File.separator + "jassist_generate";

        factory.setSuperclass(Target.class);
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method m) {
                String name = m.getName();
                if (name.equals("callTarget")){
                    return true;
                }
                return false;
            }
        });
        Target target = (Target)factory.create(null, null, new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                System.out.println("MethodHandler is called " + thisMethod.getName());
                return proceed.invoke(self,args);
            }
        });
        target.setName("target");
        System.out.println(target.callTarget());
        System.out.println(target.getName());
    }
}