package com.instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.Descriptor;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/11
 * Time: 17:56
 * Desc:
 */
public class MyClassTransformer implements ClassFileTransformer {

    private static ClassPool cp = ClassPool.getDefault();

    private String name;

    public MyClassTransformer(String name) {
        this.name = name;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String classname = TargetClass.class.getName().replaceAll("\\.","/");
        if(classname.equals(className)) {
            System.out.println(name + " start to transform original class");
            try {
                ByteArrayInputStream bin = new ByteArrayInputStream(classfileBuffer);
                CtClass ctClass = cp.makeClass(bin);
                CtMethod ctMethod = ctClass.getMethod("printHelloWorld", Descriptor.ofMethod(CtClass.voidType,null));
                ctMethod.insertBefore("System.out.println(\"agent invoke\");");
                byte[] bytes = ctClass.toBytecode();
                ctClass.defrost();
                return bytes;
            }catch (Exception e){
                e.printStackTrace();
            }
            return classfileBuffer;
        }

        return classfileBuffer;
    }
}