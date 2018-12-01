package com.lookup;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2018/12/1
 * Time: 21:55
 * Desc:
 */
@Component
public abstract class LookupTest {

    public void test(){
        getBean().action();
    }

    @Lookup
    abstract PrototypeBean getBean();//PrototypeBean does not need to create scope proxy
}