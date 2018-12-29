package com.cglib;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by apple on 23/04/2015.
 * cglib特殊的方法拦截器，由于cglib可以代理实类，不需要在MethodInterceptor的实现里显性调用被代理的实例，
 * 而是通过MethodProxy的invokeSuper调用被代理实例的具体方法实现
 */
public class testMetodInterceptor {
    public static void main(String args[]) {

        CglibUtils.setupOutPutDir();

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
        son proxy = (son) enhancer.create(new Class<?>[]{int.class},new Object[]{10});//没有过滤bridge方法,要升级到高版本才会过滤
        /**
         * type.getDeclaredMethods
         * CollectionUtils.filter(methods, new RejectModifierPredicate(Constants.ACC_STATIC));
         * CollectionUtils.filter(methods, new VisibilityPredicate(superclass, true)); //accessible is true: public, protect, the same package
         * CollectionUtils.filter(methods, new DuplicatesPredicate());
         * CollectionUtils.filter(methods, new RejectModifierPredicate(Constants.ACC_FINAL));
         * */
        proxy.publicMethodSon("test method interceptor");
        System.out.println(proxy.toString());
    }
}
