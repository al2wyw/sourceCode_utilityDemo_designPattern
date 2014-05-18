package com.myws.daoImpl;

import com.myws.dao.savePerson;
import com.myws.model.Person;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class savePersonToDB implements savePerson{
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Person p) {
		// TODO Auto-generated method stub
		SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource);
		SimpleJdbcInsert myinsert=insert.withTableName("person");
		myinsert.execute(new BeanPropertySqlParameterSource(p));
	}

}
