package com.beancopy;

import com.cglib.CglibUtils;
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
        CglibUtils.setupOutPutDir();
        test2();
    }

    public static void test1(){
        BeanCopier beanCopier = BeanCopier.create(Test.class, TestTarget.class, true);
        BeanCopier beanCopierTarget = BeanCopier.create(TestInner.class, TestInnerTarget.class, true);

        //test
        Test test = new Test();
        test.setName("test123");
        TestInner testInner = new TestInner();
        testInner.setName("test inner 123");
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

    public static void test2(){
        Map<String,String> nameMap = Maps.newHashMap();
        nameMap.put("otherName","name");//setter -> getter
        BeanCopierExt<Test,TestTarget> beanCopierExt = BeanCopierExt.create(Test.class, TestTarget.class, new CopyCallback<Test,TestTarget>() {
            @Override
            public void OnSuccess(Test test, TestTarget testTarget) {
                LoggerUtils.getLogger().info("success");
                testTarget.setCallBackName(test.getName());
            }

            @Override
            public void onFailure(Test test, TestTarget testTarget, Exception e) {
                LoggerUtils.getLogger().error("", e);
            }
        }, nameMap);
        //test
        Test test = new Test();
        test.setName("test name");
        TestInner testInner = new TestInner();
        testInner.setName("inner test name");
        test.setObjectList(Lists.newArrayList(testInner));
        test.setObjectMap(Maps.newHashMap());
        test.getObjectMap().put("test", testInner);

        TestTarget testTarget = new TestTarget();
        beanCopierExt.transform(test,testTarget);

        LoggerUtils.getLogger().info("{}", testTarget.getCallBackName());

        LoggerUtils.getLogger().info("{}", testTarget.getObjectList().get(0).getName());

        LoggerUtils.getLogger().info("{}", testTarget.getObjectMap().get("test"));
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
    private String otherName;
    private String callBackName;

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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getCallBackName() {
        return callBackName;
    }

    public void setCallBackName(String callBackName) {
        this.callBackName = callBackName;
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