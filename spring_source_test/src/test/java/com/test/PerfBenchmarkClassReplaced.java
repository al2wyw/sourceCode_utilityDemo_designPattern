package com.test;

import com.cglib.ByteCodeMaxLoopAnalysis;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-07-20
 * Time: 19:48
 * Description:
 */
public class PerfBenchmarkClassReplaced {

    public static void main(final String[] args) throws Exception {
        Class<?> klass = new MyDefineClassLoader((URLClassLoader)Thread.currentThread().getContextClassLoader()).loadClass("com.test.TransformPerfBenchmark");
        System.out.println(klass.getClassLoader());
        Method testTransform = klass.getDeclaredMethod("testTransform");
        testTransform.invoke(klass.newInstance());

        //jmh的Benchmark注释的方法会被额外的AppClassLoader加载
        ////Method method = klass.getDeclaredMethod("main", String[].class);
        //method.invoke(null, (Object) new String[] {});
    }

    public static class MyDefineClassLoader extends URLClassLoader {

        public MyDefineClassLoader(URLClassLoader parent) {
            super(parent.getURLs(), parent.getParent());//屏蔽掉AppClassLoader，由MyDefineClassLoader代替它
        }

        //改变findClass的逻辑比改变loadClass的逻辑更合适，可以充分利用classloader的cache
        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.contains("InstructionsTestG")) {
                try {
                    byte[] newContent = ByteCodeMaxLoopAnalysis.transform("com/dynamicInvoke/InstructionsTest.class",
                            "com.dynamicInvoke.InstructionsTestG");
                    return defineClass("com.dynamicInvoke.InstructionsTestG", newContent, 0, newContent.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException();
                }
            }
            return super.findClass(name);
        }
    }
}
