package com.aop;

import com.annotation.BizTraceParam;
import com.test.BizTraceId;
import com.utils.LoggerUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/6
 * Time: 18:11
 * Desc:
 */
@Aspect
@Component
@Order(3)
public class transactionSynAspect {

    @Autowired
    private DataSource dataSource;

    @Before("@annotation(org.springframework.transaction.annotation.Transactional) && args(param,..)")//..,param,.. is not supported
    public void test(JoinPoint joinPoint,  BizTraceId param) throws Throwable{
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        //use TransactionalEventListener to do the same thing
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new MyBizTraceSynchronization(param.getId(), dataSource));
        }
    }

    //@Before("@annotation(org.springframework.transaction.annotation.Transactional) && @args(param,..)") // failed, @args works strangely
    public void doSync(JoinPoint joinPoint,BizTraceParam param) throws Throwable{
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object arg0 = args[param.vaule()];
        if(arg0 instanceof String) {
            String id = (String) args[0];
            //use TransactionalEventListener to do the same thing
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(new MyBizTraceSynchronization(id, dataSource));
            }
        }
    }

    public static class MyBizTraceSynchronization extends TransactionSynchronizationAdapter {

        private String bizId;
        private DataSource dataSource;

        public MyBizTraceSynchronization() {
        }

        public MyBizTraceSynchronization(String bizId, DataSource dataSource) {
            this.bizId = bizId;
            this.dataSource = dataSource;
        }

        @Override
        public void afterCommit() {
            ConnectionHolder holder = (ConnectionHolder)TransactionSynchronizationManager.getResource(dataSource);
            LoggerUtils.getLogger().info("bizId: {} is commit with connection {}",bizId, holder.getConnection());
        }
    }
}
