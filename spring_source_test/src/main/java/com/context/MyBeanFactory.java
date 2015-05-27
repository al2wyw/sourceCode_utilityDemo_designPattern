package com.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Created by apple on 25/04/2015.
 */
public class MyBeanFactory extends DefaultListableBeanFactory {
    public MyBeanFactory(BeanFactory parentBeanFactory){
        super(parentBeanFactory);
    }

    public MyBeanFactory(){
        super();
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        super.addBeanPostProcessor(beanPostProcessor);
        if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
            System.out.println("InstantiationAwareBeanPostProcessor found");
        }
    }
}
