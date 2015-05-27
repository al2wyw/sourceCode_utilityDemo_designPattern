package com.reflect.test;

import java.lang.reflect.Method;


public class test_bridge_method {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*D test = new D();
		Object t = test.id(new Object());
		System.out.println(t);*/
		Class<D> k = D.class;
		Method[] arr = k.getDeclaredMethods();
		System.out.println(arr.length);//it is two
	}

}

abstract class C<T> {
    abstract T id(T x);
}
class D extends C<String> {
    String id(String x) { return x; }
}
