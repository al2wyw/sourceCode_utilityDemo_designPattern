package com.reflect.test;

public class test_object_getclass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test_object_getclass t = new test_object_getclass();
		get(t);//out put com.reflect.test.test_object_getclass
	}
	
	public static void get(Object o){
		String name = o.getClass().getName();
		System.out.println(name);
	}

}
