package com.myws.dao;
import com.myws.model.Person;
import com.myws.service.ShowService;

import org.apache.log4j.Logger;
public interface savePerson {
	Logger log = Logger.getLogger(ShowService.class);
	void save(Person p) throws Exception;
}
