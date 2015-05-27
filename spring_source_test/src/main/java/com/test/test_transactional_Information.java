package com.test;

import com.model.Course;
import com.model.Information;
import com.transaction.InformationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by apple on 29/04/2015.
 */
@Transactional(propagation=Propagation.NESTED)
@Service
public class test_transactional_Information {

    private InformationDao informationDao;

    @Autowired
    public test_transactional_Information(InformationDao informationDao){
        this.informationDao = informationDao;
        System.out.println("create the table information first");
        informationDao.dropTable();
        informationDao.createTable();
    }

    public void clearInformation(){
    	informationDao.clearTable();
    }

    public List<Information> getInfo(int offset,int limit){
        return informationDao.selectInformationByLimit(offset,limit);
    }
    
    public void insertInformation(Course c){
        String cid = c.getCid();
        String iid = null;
        String description = null;
        Information i = new Information();
        i.setCid(cid);

        try{
        	Thread.sleep(100);
        }catch(Exception e){
        	
        }
        long time = System.currentTimeMillis()+9999;
        iid = String.valueOf(time++);
        description = "C++ leads every things";
        i.setIid(iid);
        i.setDescription(description);
        informationDao.insertInformation(i);

        iid = String.valueOf(time++);//comment out as trigger for duplicate key exception
        description = "Java can tell you the tomorrow";
        i.setIid(iid);
        i.setDescription(description);
        informationDao.insertInformation(i);

        iid = String.valueOf(time++);
        description = "JavaScript is so dark";
        i.setIid(iid);
        i.setDescription(description);
        informationDao.insertInformation(i);
    }
}