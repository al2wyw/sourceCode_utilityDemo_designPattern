package com.cglib;

import com.utils.ClassLoaderUtils;
import net.sf.cglib.core.*;
import org.checkerframework.checker.units.qual.C;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/8/4
 * Time: 20:08
 * Desc:
 */
public class ClassEmitterTest {

    public static void main(String[] args) throws Exception{
        test1();
        test2();
    }

    public interface Test{
        void test();
    }

    public static void test2() throws Exception{
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        ClassEmitter classEmitter = new ClassEmitter(classWriter);

        classEmitter.begin_class(Constants.V1_8,Constants.ACC_PUBLIC,"com.cglib.GeneratedSubTest",
                Type.getType(Object.class), new Type[]{Type.getType(Test.class)}, Constants.SOURCE_FILE);
        classEmitter.declare_field(Constants.ACC_PRIVATE ,"test",Type.getType(String.class),null);

        CodeEmitter constructor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature(Constants.CONSTRUCTOR_NAME,"()V"),null);
        constructor.load_this();
        constructor.super_invoke_constructor();
        constructor.load_this();
        constructor.visitLdcInsn("this is my test value");
        constructor.putfield("test");
        constructor.return_value();
        constructor.end_method();

        constructor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature(Constants.CONSTRUCTOR_NAME,"(Ljava/lang/String;)V"),null);
        constructor.load_this();
        constructor.super_invoke_constructor();
        constructor.load_this();
        constructor.load_arg(0);
        constructor.putfield("test");
        constructor.return_value();
        constructor.end_method();

        CodeEmitter codeEmitter = classEmitter.begin_method(Constants.ACC_PUBLIC,TypeUtils.parseSignature("void test()"),null);
        //桥接到com.cglib.son.protectedMethodSon
        Type target = Type.getType(son.class);
        codeEmitter.new_instance(target);
        codeEmitter.dup();
        codeEmitter.invoke_constructor(target);
        codeEmitter.invoke_virtual(target,TypeUtils.parseSignature("void protectedMethodSon()"));
        codeEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
        codeEmitter.load_this();
        codeEmitter.getfield("test");
        codeEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Constants.TYPE_STRING}));
        codeEmitter.return_value();
        codeEmitter.end_method();

        classEmitter.end_class();

        byte[] content = classWriter.toByteArray();
        ClassLoaderUtils.saveClassFile("GeneratedSubTest.class", content);

        //Test test = ClassLoaderUtils.defineInstance(Thread.currentThread().getContextClassLoader(),"com.cglib.GeneratedSubTest",content);
        Class klass = ClassLoaderUtils.defineClass(Thread.currentThread().getContextClassLoader(),"com.cglib.GeneratedSubTest",content);
        Constructor method = klass.getDeclaredConstructor(String.class);
        Test test = (Test)method.newInstance("value passed to test");


        test.test();
    }

    public static void test1() throws Exception{
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        ClassEmitter classEmitter = new ClassEmitter(classWriter);

        classEmitter.begin_class(Constants.V1_8,Constants.ACC_PUBLIC,"com.test.GeneratedClass",
                Type.getType(Object.class), null, Constants.SOURCE_FILE);
        EmitUtils.null_constructor(classEmitter);

        //ReflectUtils.getMethodInfo(java.lang.reflect.Method)
        CodeEmitter codeEmitter = EmitUtils.begin_method(classEmitter, new MethodInfo() {
            //not used
            @Override
            public ClassInfo getClassInfo() {
                return null;
            }

            @Override
            public int getModifiers() {
                return Constants.ACC_PUBLIC;
            }

            @Override
            public Signature getSignature() {
                return TypeUtils.parseSignature("void test()");//nice parse
            }

            @Override
            public Type[] getExceptionTypes() {
                return new Type[0];
            }
        }, Constants.ACC_PUBLIC);
        codeEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
        codeEmitter.visitLdcInsn("my string");
        codeEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Constants.TYPE_STRING}));
        codeEmitter.return_value();
        codeEmitter.end_method();

        classEmitter.end_class();

        byte[] content = classWriter.toByteArray();
        ClassLoaderUtils.saveClassFile("GeneratedClass.class", content);

        Class klass = ClassLoaderUtils.defineClass(Thread.currentThread().getContextClassLoader(),"com.test.GeneratedClass",content);
        Method method = klass.getDeclaredMethod("test");
        method.invoke(klass.newInstance());
    }
}