package com.cache;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/10/29
 * Time: 11:34
 * Desc:
 */
public class Person {
    private Long id;
    private String name;
    private Integer age;
    private Boolean gender;
    private BigDecimal salary;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
