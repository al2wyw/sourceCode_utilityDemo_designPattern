package annotation;


import java.lang.annotation.*;

/**
 * Created by johnny.ly on 2016/2/2.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Test {

}
