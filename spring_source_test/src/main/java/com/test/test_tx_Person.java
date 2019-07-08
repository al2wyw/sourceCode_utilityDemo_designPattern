package com.test;

import com.annotation.BizTraceParam;
import com.model.Person;
import com.transaction.PersonDao;
import com.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/5
 * Time: 15:58
 * Desc:
 */
@Service
public class test_tx_Person {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private TransactionTemplate transactionTemplate;//require new

    //count 大于100 最终会block在dataSource.getConnection
    public void insertPerson(Person p, int count){
        if(count-- <= 0){
            return;
        }
        final int cou = count;
        transactionTemplate.execute(ac -> {
            //ac.setRollbackOnly();//finally rollback
            personDao.insertPerson(p);
            p.setId("0x2"+cou);
            insertPerson(p,cou);
            return null;
        });
    }

    @Transactional
    public void getPersonById(BizTraceId id, String context){
        LoggerUtils.getLogger().info("{}, context:{}", personDao.getPersonById(id.getId()), context);
    }

    @Transactional
    public void getPersonById(long context, BizTraceId id){
        LoggerUtils.getLogger().info("{}, context:{}", personDao.getPersonById(id.getId()) , context);
    }

    @Transactional
    public void getPersonById(@BizTraceParam String id, String context){
        LoggerUtils.getLogger().info("{}, context:{}", personDao.getPersonById(id), context);
    }
}
