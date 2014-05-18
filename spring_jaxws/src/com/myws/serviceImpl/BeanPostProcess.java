package com.myws.serviceImpl;
import org.springframework.beans.BeansException;

import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanPostProcess implements BeanPostProcessor {

 

    public Object postProcessBeforeInitialization(Object bean, String beanName)

           throws BeansException {

       System.out.println("The Target " + beanName + "has started initialization");

       return bean;

    }


    public Object postProcessAfterInitialization(Object bean, String beanName)

           throws BeansException {

       System.out.println("The Target " + beanName + "has been initialized");

       return bean;

    }

}

