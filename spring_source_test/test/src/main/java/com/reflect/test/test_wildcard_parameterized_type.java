package com.reflect.test;

import java.util.ArrayList;
import java.util.List;

public class test_wildcard_parameterized_type {

	private static void test(List<?> list){
		//can not call parameterized function
		Object t = list.get(0);
		if(t instanceof String)
			System.out.println("This is a string:"+(String)t);
	}
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("test");
		test(list);
	}

}
