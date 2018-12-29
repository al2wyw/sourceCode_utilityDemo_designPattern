package com.javassist;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/12/22
 * Time: 23:58
 * Desc:
 */
public class GenericTarget<T> {

    private T target;

    public T callTarget(){
        return target;
    }
}
