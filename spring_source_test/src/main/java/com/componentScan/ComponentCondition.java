package com.componentScan;

import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/25
 * Time: 16:04
 * Desc:
 */
@Component
@MyConditional(trigger="autowire")
public class ComponentCondition {
    public ComponentCondition(){
        System.out.println("ComponentCondition init");
    }
}
