package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.model.Course;
import com.model.Person;
import com.transaction.CourseDao;
@Transactional(propagation=Propagation.REQUIRES_NEW)
@Service
public class test_transactional_Course {

	private CourseDao courseDao;

	@Autowired
	private test_transactional_Information informationService;
	
	@Autowired
	public test_transactional_Course(CourseDao courseDao){
		this.courseDao = courseDao;
		System.out.println("create the table course first");
		courseDao.dropTable();
		courseDao.createTable();
	}
	
	public void clearCourse(){
		courseDao.clearTable();
		informationService.clearInformation();
	}
	
	public void insertCourse(Person p){
		String id = p.getId();
		String cid = null;
		String cname = null;
		Course c = new Course();
		c.setId(id);
		
		long time = System.currentTimeMillis()+9999;
		cid = String.valueOf(time++);
		cname = "C++";
		c.setCid(cid);
		c.setCname(cname);
		courseDao.insertCourse(c);
		informationService.insertInformation(c);
		
		cid = String.valueOf(time++);//comment out as trigger for duplicate key exception
		cname = "JAVA";
		c.setCid(cid);
		c.setCname(cname);
		courseDao.insertCourse(c);
		informationService.insertInformation(c);
		
		cid = String.valueOf(time++);
		cname = "JAVASCRIPT";
		c.setCid(cid);
		c.setCname(cname);
		courseDao.insertCourse(c);
		informationService.insertInformation(c);
	}
}
