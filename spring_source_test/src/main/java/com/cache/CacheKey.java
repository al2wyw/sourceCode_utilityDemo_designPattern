package com.cache;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/10/29
 * Time: 13:54
 * Desc:
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheKey {
    /**
     *  property path: id or object.id, empty if current parameter used as key
     * */
    String path() default "";
}
