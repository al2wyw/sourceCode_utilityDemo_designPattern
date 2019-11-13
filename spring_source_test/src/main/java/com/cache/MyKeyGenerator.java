package com.cache;

import com.utils.LoggerUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/10/29
 * Time: 13:51
 * Desc:
 */
@Component
public class MyKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Parameter[] parameters = method.getParameters();
        int num = IntStream.range(0, parameters.length)
                .filter(index -> parameters[index].isAnnotationPresent(CacheKey.class))
                .findFirst().orElse(-1);
        Parameter param = parameters[num];
        CacheKey anno = param.getAnnotation(CacheKey.class);
        String path = anno.path();
        LoggerUtils.getLogger().info("path find {}", path);
        if("".equals(path)){
            return params[num];
        }else {
            try {
                return PropertyUtils.getNestedProperty(params[num], path);
            }catch (Exception e){
                LoggerUtils.getLogger().error("", e);
            }
            return null;
        }
    }
}
