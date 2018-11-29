package com.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.beans.PropertyDescriptor;

/**
 * Created by apple on 25/04/2015.
 */
public class MyXmlWebApplicationContext extends XmlWebApplicationContext {

    @Override
    protected ConfigurableEnvironment createEnvironment() {
        ConfigurableEnvironment environment = super.createEnvironment();
        PropertySourceFactory propertySourceFactory = new DefaultPropertySourceFactory();
        try {
            PropertySource propertySource = propertySourceFactory.createPropertySource("SPECIAL-ENV", new EncodedResource(getResource("classpath:environment-properties")));
            environment.getPropertySources().addLast(propertySource);
        }catch (Exception e){
            e.printStackTrace();
        }
        return environment;
    }

    public DefaultListableBeanFactory createBeanFactory(){
        return new MyBeanFactory(getInternalParentBeanFactory());
    }

    public void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
        super.customizeBeanFactory(beanFactory);
        System.out.println("this is my defined XmlWebApplicationContext");
        this.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor(){

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor(){

                    @Override
                    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

                        return null;
                    }

                    @Override
                    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                        return true;
                    }

                    @Override
                    public PropertyValues postProcessPropertyValues(
                            PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName)
                            throws BeansException {

                        return pvs;
                    }

                    @Override
                    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                        return bean;
                    }

                    @Override
                    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                        return bean;
                    }
                });
            }
        });
    }
}
