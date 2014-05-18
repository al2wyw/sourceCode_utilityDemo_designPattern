package com.myws.serviceImpl;
import org.springframework.beans.factory.InitializingBean;
public class initializeBean implements InitializingBean{

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("test InitializingBean");        
    }
    
}
