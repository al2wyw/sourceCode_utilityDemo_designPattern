package com.componentScan;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/25
 * Time: 16:48
 * Desc:
 */
public class MyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attribute = metadata.getAnnotationAttributes(MyConditional.class.getName());
        Object value = attribute.get("trigger");
        if(value instanceof String) {
            System.out.println("MyCondition get trigger: " + value);
            boolean flag = context.getEnvironment().getRequiredProperty((String)value,Boolean.TYPE);
            return flag;
        }
        return false;
    }
}
