package com.instrumentation;

import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/11
 * Time: 17:52
 * Desc: -javaagent:D:\agent.jar
 *       -Dsun.reflect.inflationThreshold=0
 *       即使transform成新的方法，也不影响已经被缓存下来的Method的调用
 */
//@Controller
public class InstrumentController {

    private static Instrumentation instrumentation;

    private static List<Class> classes = Lists.newArrayList();

    private static List<Method> methods = Lists.newArrayList();

    static {
        try {
            //webAppClassLoader 打破了双亲委派，优先从自己的jar包里加载类, 而InstrumentationSteal是由AppClassLoader加载的
            Class klass = Thread.currentThread().getContextClassLoader().loadClass("unsafe.InstrumentationSteal");
            Field field = klass.getField("instrumentation");
            instrumentation = (Instrumentation) field.get(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value="inst/rein", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String rein() {
        ClassFileTransformer classFileTransformer = new MyClassTransformer();
        try {
            instrumentation.addTransformer(classFileTransformer,true);
            instrumentation.retransformClasses(TargetClass.class);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(instrumentation
                    .removeTransformer(classFileTransformer));
        }
        return "ok".toString();
    }

    @RequestMapping(value="inst/reset", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String reset() {
        try {
            instrumentation.retransformClasses(TargetClass.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping(value="inst/info", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String classInfo() {
        try {
            TargetClass targetClass = new TargetClass();
            targetClass.printHelloWorld();
            Method method = TargetClass.class.getMethod("printHelloWorld");

            classes.add(TargetClass.class);
            methods.add(method);

            method.invoke(targetClass);
            return method.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping(value="inst/invoke", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String classInvoke() {
        TargetClass targetClass = new TargetClass();
        targetClass.printHelloWorld();

        methods.stream().forEach(
                m -> {
                    try {
                        m.invoke(targetClass); //都会调用新的实现
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );
        return "ok";
    }
}