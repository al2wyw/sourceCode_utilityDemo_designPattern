package com.cglib;

//ConstructorDelegate to create a byte-instrumented factory method
//MethodDelegate has some drawbacks, do not use except for required
//MethodDelegate to emulate a C#-like delegate
import net.sf.cglib.reflect.MethodDelegate;

public class testMethodDelegate {

	public static void main(String[] args) {
		DelegatedBean bean = new DelegatedBean("this is a test");
		Delegator d = (Delegator)MethodDelegate.create(bean, "getName", Delegator.class);
		System.out.println(d.test());
	}

}

interface Delegator{
	String test();
	//just can have a method!!! bad!
}

class DelegatedBean{
	private String name;

	public DelegatedBean(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}