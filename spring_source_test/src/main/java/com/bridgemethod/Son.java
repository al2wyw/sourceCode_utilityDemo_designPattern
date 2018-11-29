package com.bridgemethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/29
 * Time: 17:38
 * Desc:
 */
@Component
public class Son extends Father<Target> {


    @Autowired
    @Override
    public Target getValue(Target s) {
        System.out.println(s);
        return s;
    }

    //bridge method : Object getValue(Object s) with @Autowired
    //isVisibilityBridgeMethodPair: ParameterTypes and ReturnType all the same,
    //getValue(Object s) and getValue(Target s) is not visible
}
