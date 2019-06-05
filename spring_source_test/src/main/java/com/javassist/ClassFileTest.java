package com.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import javassist.bytecode.BootstrapMethodsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;

import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/6/5
 * Time: 17:33
 * Desc:
 */
public class ClassFileTest {

    public static void main( String args[] ) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass klass = pool.get("com.dynamicInvoke.LambdaTest");
        ClassFile cf = klass.getClassFile();
        ConstPool cp = cf.getConstPool();

        BootstrapMethodsAttribute attributeInfo = (BootstrapMethodsAttribute)cf.getAttribute(BootstrapMethodsAttribute.tag);
        Stream.of(attributeInfo.getMethods())
                .forEach(method -> {
                    int[] arg = method.arguments;
                    int kind = cp.getMethodHandleKind(arg[1]);
                    int index = cp.getMethodHandleIndex(arg[1]);
                    System.out.println(kind + cp.getMethodrefName(index) + cp.getMethodrefType(index) + cp.getMethodrefClassName(index));
                });
        klass.toBytecode();
    }
}
