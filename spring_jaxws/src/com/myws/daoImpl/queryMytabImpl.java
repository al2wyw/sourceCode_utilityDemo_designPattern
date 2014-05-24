package com.myws.daoImpl;

import com.myws.dao.queryMytab;
import com.myws.model.mytab;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
public class queryMytabImpl implements queryMytab {
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
	}

	@Override
	public mytab query(String name) throws Exception {
		// TODO Auto-generated method stub
		Object[] params = new Object[] { name };    
		int[] types = new int[] { Types.VARCHAR }; 
		mytab o=jdbcTemplate.queryForObject("select * from mytab where name=?",params,types, new RowMapper<mytab>(){
			 public mytab mapRow(ResultSet rs, int num) throws SQLException {
				 mytab o=new mytab();
				 o.setId(rs.getInt("id"));
				 o.setName(rs.getString("name"));
				 o.setSalary(rs.getDouble("salary"));
				 o.setSex(rs.getString("sex"));
				 o.setDay(rs.getDate("day"));
				 o.setLevel(rs.getString("level"));
				 return o;
			 }
		});
		return o;
	}

}
