package com.cglib;

import org.springframework.beans.factory.annotation.Value;

public class son extends father {
	private int price;

	public son(){
		super();
		System.out.println("son born with default");
	}

	public son(int price){
		super();
		this.price = price;
		System.out.println("son born with args");
	}

	public void publicMethodSon(@Value("this is a new value")String test){
		System.out.println(test);
	}
	
	public double publicMethodFather(){
		if (salary > 700.34)
			return salary;
		else
			return 8945.99;
	}

	private String privateMethodSon(int i){
		System.out.println("privateMethodSon"+" "+i);
		return "my son";
	}

	protected void protectedMethodSon(){
		System.out.println("protectedMethodSon");
	}
}
