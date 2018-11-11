package com.test;

import org.springframework.context.annotation.Bean;

public class ImportedService {

    @Bean(name="ImportedService.test")
    public ImportedService test(){
        return this;
    }
}