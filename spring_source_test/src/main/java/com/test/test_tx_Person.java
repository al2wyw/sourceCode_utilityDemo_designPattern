package com.test;

import com.model.Person;
import com.transaction.PersonDao;
import com.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

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
    private DataSource dataSource;

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
    public void getPersonById(String id){
        //use TransactionalEventListener to do the same thing
        if(TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
                @Override
                public void afterCommit() {
                    ConnectionHolder holder = (ConnectionHolder)TransactionSynchronizationManager.getResource(dataSource);
                    LoggerUtils.getLogger().info("{}",holder.getConnection());
                }
            });
        }
        LoggerUtils.getLogger().info("{}", personDao.getPersonById(id));
    }
}
