package com.annotation;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/8
 * Time: 11:23
 * Desc:
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizTraceParam {
    int vaule() default 0;
}
