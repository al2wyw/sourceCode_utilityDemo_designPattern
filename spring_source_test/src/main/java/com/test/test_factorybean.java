package com.test;

import org.springframework.beans.factory.FactoryBean;

public class test_factorybean implements FactoryBean<test4>{

	public test_factorybean(){
		System.out.println("factory bean instatiation");
	}
	public test4 getObject() throws Exception {
		System.out.println("get the object test4 from factory bean");
		test4 test = new test4();
		return test;
	}

	public Class<?> getObjectType() {
		
		return test4.class;
	}

	public boolean isSingleton() {
		
		return true;
	}

}
