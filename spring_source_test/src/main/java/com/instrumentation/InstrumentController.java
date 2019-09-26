package com.instrumentation;

import com.componentScan.MyConditional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
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
 *
 *       redefineClass 先用redefineByteCode替换运行时的字节码，会触发transformer(false)然后触发transformer(true)
 *       retransformClass 只会触发transformer(true), 类似于回滚操作，
 *       回滚到当前agent第一次redefineClass或者loadClass的结果(包含transformer(false)的transform效果)，然后再触发transformer(true)
 *       redefineClass 和 retransformClass 搭配使用比较复杂，参考寒泉子的文章
 */
@Controller
@MyConditional(trigger="instrument")
public class InstrumentController {

    private static Instrumentation instrumentation;

    private static List<Class> classes = Lists.newArrayList();

    private static List<Method> methods = Lists.newArrayList();

    private static List<ClassFileTransformer> transformers = Lists.newLinkedList();

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

    @RequestMapping(value="inst/add", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String add(@RequestParam("flag") boolean flag) {
        ClassFileTransformer classFileTransformer = new MyClassTransformer(String.valueOf(flag));
        try {
            instrumentation.addTransformer(classFileTransformer, flag);
            transformers.add(classFileTransformer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping(value="inst/remove", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String remove() {
        try {
            Preconditions.checkArgument(transformers.size() > 0);
            instrumentation.removeTransformer(transformers.remove(0));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping(value="inst/retran", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String retran() {
        try {
            instrumentation.retransformClasses(TargetClass.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping(value="inst/red", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String red() {
        try {
            InputStream inputStream = TargetClass.class.getResourceAsStream("TargetClass.class");
            ClassFileTransformer classFileTransformer = new MyClassTransformer("test");
            byte[] transformedCode = classFileTransformer.transform(Thread.currentThread().getContextClassLoader(),
                    TargetClass.class.getName().replaceAll("\\.","/"),
                    TargetClass.class,
                    TargetClass.class.getProtectionDomain(),
                    IOUtils.toByteArray(inputStream));
            ClassDefinition classDefinition = new ClassDefinition(TargetClass.class,transformedCode);
            instrumentation.redefineClasses(classDefinition);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping(value="inst/rein", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String rein() {
        ClassFileTransformer classFileTransformer = new MyClassTransformer("test");
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