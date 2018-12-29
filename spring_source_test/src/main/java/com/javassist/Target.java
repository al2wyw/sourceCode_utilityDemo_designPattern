package com.javassist;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2018/12/22
 * Time: 18:57
 * Desc:
 */
public class Target extends GenericTarget<String>{

    private int i;

    private String name;

    private Date birth;

    @Override
    public String callTarget(){
        System.out.println(this.getClass() + " father is called");
        return name;
    }

    public int getI() {
        return i;
    }

    public String getName() {
        return name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}