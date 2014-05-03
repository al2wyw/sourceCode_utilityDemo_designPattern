package com.myws.service;
import com.myws.model.Person;
import org.apache.log4j.Logger;
public interface savePerson {
	Logger log = Logger.getLogger(ShowService.class);
	void save(Person p);
}
