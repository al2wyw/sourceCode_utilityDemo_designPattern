package com.myws.daoImpl;

import com.myws.dao.savePerson;
import com.myws.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;

public class savePersonNotToDBCaller implements savePerson {
	private savePerson action;
	private JdbcTemplate jdbcTemplate;
	
	public savePerson getAction() {
		return action;
	}
	public void setAction(savePerson action) {
		this.action = action;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void save(Person p) throws Exception{
		// TODO Auto-generated method stub
		jdbcTemplate.update("insert into mytab values(213424,'Peter Clare',456.34)");
		action.save(p);
	}

}
