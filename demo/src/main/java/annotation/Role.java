package annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/4/1
 * Time: 11:06
 * Desc:
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(Roles.class)
public @interface Role {
    String name();
    int level() default 1;
}
