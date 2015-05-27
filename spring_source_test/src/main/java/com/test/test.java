package com.test;

import java.util.List;

public class test {
	private List<String> eles;
	public test(List<String> eles){
		System.out.println("this is test");
		this.eles = eles;
	}
	public List<String> getEles() {
		return eles;
	}
	public void setEles(List<String> eles) {
		this.eles = eles;
	}
}
