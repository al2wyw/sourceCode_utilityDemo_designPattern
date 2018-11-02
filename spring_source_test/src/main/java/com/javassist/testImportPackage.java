package com.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/2
 * Time: 10:21
 * Desc:
 */
public class testImportPackage {
    public static void main( String args[] ) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        pool.importPackage("javax.annotation");
        pool.importPackage("com.model");

        CtClass cc = pool.makeClass("com.test.myTest");
        ClassFile ccFile = cc.getClassFile();
        ConstPool constpool = ccFile.getConstPool();

        CtField ctField = CtField.make("private Person person;",cc);// importPackage 有效果
        cc.addField(ctField);
        ctField = new CtField(CtClass.intType,"count",cc);
        ctField.setModifiers(Modifier.PRIVATE);
        cc.addField(ctField);

        CtMethod ctMethod = CtMethod.make("public Person getPerson(Object o) {" +
                "if(o == null){" +
                    "System.out.println(\"empty!\");" +
                "} " +
                "return person;" +
                "}",cc);
        ctMethod.insertBefore("int i = 10 ; person = new Person(); System.out.println(person + \" \" +i);");//对代码有限制，不能访问make里面定义的局部变量
        cc.addMethod(ctMethod);

        AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        Annotation annot = new Annotation("Resource", constpool); // importPackage 对 annotation 没有效果
        attr.addAnnotation(annot);
        ccFile.addAttribute(attr);

        Class klass = cc.toClass();
        System.out.println(klass.isAnnotationPresent(Resource.class));
        Method method = klass.getMethod("getPerson",Object.class);
        method.invoke(klass.newInstance(),(Object)null);
    }
}
