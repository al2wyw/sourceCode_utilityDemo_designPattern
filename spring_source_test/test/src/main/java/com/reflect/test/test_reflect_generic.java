package com.reflect.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class test_reflect_generic {

	@SuppressWarnings("all")
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		parent<String> p = new parent<String>();
		p.setObject("test");
		//do the type cast here, not in the method getObject
		//String test = p.getObject();
		Object test = p.getObject();
		System.out.println(test);
		Class<parent> klass = parent.class;
		Method[] ms = klass.getDeclaredMethods();
		System.out.println(ms.length);
		for(Method m:ms){
			System.out.println(m.getName());
		}
		Method setter = klass.getMethod("setObject", new Class<?>[]{Object.class});
		setter.invoke(p, new Object());
		//no this method
		//setter = klass.getMethod("setObject", new Class<?>[]{String.class});
		//setter.invoke(p, "names");
		Method getter = klass.getMethod("getObject", null);
		System.out.println(getter.invoke(p, null));
		Object object = p.getObject();
		System.out.println(object);
	}

}

class parent<T>{
	private T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
}