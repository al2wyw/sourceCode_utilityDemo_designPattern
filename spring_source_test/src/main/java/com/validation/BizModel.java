package com.validation;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/13
 * Time: 20:54
 * Desc:
 */
public class BizModel {

    //ConstraintViolationImpl{interpolatedMessage='最大不能超过10', propertyPath=bizCheckAgain.arg0.i,
    //rootBeanClass=class com.validation.BizService, messageTemplate='{javax.validation.constraints.Max.message}'}
    @Max(10)
    @Min(1)
    private int i;

    //ConstraintViolationImpl{interpolatedMessage='name is blank',propertyPath=bizCheckAgain.arg0.name,
    //rootBeanClass=class com.validation.BizService, messageTemplate='name is blank'}
    @NotBlank(message = "name is blank")
    private String name;

    @Past
    private Date birth;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
