package com.cglib;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.utils.LoggerUtils;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/8/23
 * Time: 14:40
 * Desc:
 */
public class BeanCopierTest {
    public static void main(String args[]){
        BeanCopier beanCopier = BeanCopier.create(Test.class, TestTarget.class, true);
        BeanCopier beanCopierTarget = BeanCopier.create(TestInner.class, TestInnerTarget.class, true);

        //test
        Test test = new Test();
        TestInner testInner = new TestInner();
        test.setName("test123");
        test.setObjectList(Lists.newArrayList(testInner));
        test.setObjectMap(Maps.newHashMap());
        test.getObjectMap().put("test", testInner);

        Converter con = new Converter() {
            @Override
            public Object convert(Object value, Class target, Object context) {
                if(List.class.isAssignableFrom(target)){
                    LoggerUtils.getLogger().info("list");
                    List source = (List)value;
                }else if(Map.class.isAssignableFrom(target)){
                    LoggerUtils.getLogger().info("map");
                }else if(Set.class.isAssignableFrom(target)){
                    LoggerUtils.getLogger().info("set");
                }
                return value;
            }
        };
        //test target
        TestTarget testTarget = new TestTarget();
        beanCopier.copy(test, testTarget, con);

        LoggerUtils.getLogger().info("{}", testTarget);

        LoggerUtils.getLogger().info("{}", testTarget.getObjectList().get(0).getName());//class cast error
    }
}

class Test{
    private List<TestInner> objectList;
    private Map<String,TestInner> objectMap;
    private String name;

    public Test(String name) {
        this.name = name;
    }

    public Test(){}

    public List<TestInner> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<TestInner> objectList) {
        this.objectList = objectList;
    }

    public Map<String, TestInner> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, TestInner> objectMap) {
        this.objectMap = objectMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class TestInner{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class TestTarget{
    private List<TestInnerTarget> objectList;
    private Map<String,TestInnerTarget> objectMap;
    private String name;

    public List<TestInnerTarget> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<TestInnerTarget> objectList) {
        this.objectList = objectList;
    }

    public Map<String, TestInnerTarget> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, TestInnerTarget> objectMap) {
        this.objectMap = objectMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class TestInnerTarget{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}