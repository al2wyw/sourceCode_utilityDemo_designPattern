package com.validation;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.MessageInterpolator;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/13
 * Time: 19:57
 * Desc:
 Bean Validation 1.1当前实现是Hibernate validator 5，且spring4才支持。接下来我们从以下几个方法讲解Bean Validation 1.1，当然不一定是新特性：
 集成Bean Validation 1.1到SpringMVC
 分组验证、分组顺序及级联验证 (比较厉害)
 消息中使用EL表达式
 方法参数/返回值验证
 自定义验证规则
 类级别验证器
 脚本验证器
 cross-parameter，跨参数验证
 混合类级别验证器和跨参数验证器
 组合多个验证注解
 本地化
 */
@Configuration
public class ValidationConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

    //配置 Validator, 可以注入到其他类里进行手动校验
    /*@Bean
    public LocalValidatorFactoryBean LocalValidatorFactoryBean(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        return localValidatorFactoryBean;
    }*/
}
