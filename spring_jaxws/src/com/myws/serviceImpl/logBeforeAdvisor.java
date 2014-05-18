package com.myws.serviceImpl;

import com.myws.service.logAdvisor;
import com.myws.model.Person;

public class logBeforeAdvisor implements logAdvisor{

	@Override
	public void log(Person person) {
		// TODO Auto-generated method stub
		System.out.println("test before save service: person name is "+person.getName());
	}

}
