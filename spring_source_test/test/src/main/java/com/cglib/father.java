package com.cglib;

import org.springframework.beans.factory.annotation.Value;

public class father {
	@Value("father")
	private String name;
	protected int id;
	public double salary;
	
	private String privateMethodFather(){
		return name.toLowerCase();
	}
	
	public double publicMethodFather(){
		if (salary > 100.34)
			return salary;
		else
			return 43205.34;
	}
}
