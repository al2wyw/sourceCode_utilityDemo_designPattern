package com.test;

import org.springframework.stereotype.Component;

@Component("config")
public class Config {
	public String callWithValue(String t){
		return t;
	}
}
