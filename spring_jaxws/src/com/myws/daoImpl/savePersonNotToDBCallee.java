package com.myws.daoImpl;

import com.myws.dao.savePerson;
import com.myws.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;

public class savePersonNotToDBCallee implements savePerson {
	private JdbcTemplate jdbcTemplate;
	@Override
	public void save(Person p) throws Exception{
		// TODO Auto-generated method stub
		jdbcTemplate.update("insert into myreftab(name,skill) values('alisa','c++')");
		throw new Exception("rollback exceptions"); 
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
