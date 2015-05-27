package com.test;

public class testScopedProxy {
	private long time = System.currentTimeMillis();
	public void showTime(){
		System.out.println(this+" "+time);
	}
}
