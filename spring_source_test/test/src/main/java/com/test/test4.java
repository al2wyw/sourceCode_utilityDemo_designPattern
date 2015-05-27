package com.test;

import org.springframework.beans.factory.BeanNameAware;

public class test4 implements BeanNameAware{

	public void setBeanName(String name) {
		System.out.println("I am test4, I know my name?");
		
	}
	public test4(){
		
		System.out.println("test4");
	}
}
