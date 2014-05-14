package com.myws.daoImpl;

import com.myws.dao.savePerson;
import com.myws.model.Person;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class savePersonToDB implements savePerson{
	private SimpleJdbcInsert insert;
	
	public SimpleJdbcInsert getInsert() {
		return insert;
	}

	public void setInsert(SimpleJdbcInsert insert) {
		this.insert = insert;
	}

	@Override
	public void save(Person p) {
		// TODO Auto-generated method stub
		SimpleJdbcInsert myinsert=insert.withTableName("person");
		myinsert.execute(new BeanPropertySqlParameterSource(p));
	}

}
