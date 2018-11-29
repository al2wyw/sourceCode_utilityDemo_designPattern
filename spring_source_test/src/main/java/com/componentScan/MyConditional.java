package com.componentScan;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/25
 * Time: 19:17
 * Desc:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(MyCondition.class)
public @interface MyConditional {
    String trigger();
}
