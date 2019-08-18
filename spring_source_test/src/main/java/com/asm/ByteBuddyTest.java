package com.asm;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/8/17
 * Time: 10:48
 * Desc:
 */
public class ByteBuddyTest {

    public static void main(String args[]) throws Exception{
        test();
        test1();

        Object i = "test".contains("t") ? new Integer(3) : new Float(1);
        System.out.println(i);
    }

    public void print(int i){
        System.out.println(i);
    }

    public void draw(double i){
        System.out.println("draw " + i);
    }

    public static void test() throws  Exception{
        ByteBuddy byteBuddy = new ByteBuddy();
        ByteBuddyTest target = new ByteBuddyTest();
        ByteBuddyTest byteBuddyTest = byteBuddy.subclass(ByteBuddyTest.class)
                .defineMethod("test", Void.TYPE, Modifier.PUBLIC)
                .withParameters(Integer.TYPE)
                .intercept(MethodDelegation.to(target))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded()
                .newInstance();
        //MethodCall.invoke(Main.class.getMethod("print", Integer.TYPE)); no args method call
        byteBuddyTest.getClass().getMethod("test",Integer.TYPE).invoke(byteBuddyTest,10);
    }

    public static void test1() throws  Exception{
        ByteBuddy byteBuddy = new ByteBuddy();
        ByteBuddyTest byteBuddyTest = byteBuddy.subclass(ByteBuddyTest.class)
                .defineMethod("print",Void.TYPE, Modifier.PUBLIC)
                .withParameters(Integer.TYPE)
                .intercept(MethodDelegation.to(new methodInterceptor()))
                .defineMethod("draw",Void.TYPE, Modifier.PUBLIC)
                .withParameters(Double.TYPE)
                .intercept(MethodDelegation.to(new methodInterceptor()))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded()
                .newInstance();
        byteBuddyTest.print(10);
        byteBuddyTest.draw(10.12);
    }

    public static class methodInterceptor{

        public Object intercept(@This Object proxy, @SuperMethod Method proxiedMethod,@Origin Method proxyMethod, @AllArguments Object[] o) throws Exception{
            System.out.println("decorate ....");
            return proxiedMethod.invoke(proxy, o);
        }
    }
}