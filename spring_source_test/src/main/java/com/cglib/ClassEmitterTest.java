package com.cglib;

import com.annotation.TargetMethod;
import com.utils.ClassLoaderUtils;
import net.sf.cglib.core.*;
import org.objectweb.asm.*;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.signature.SignatureWriter;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/8/4
 * Time: 20:08
 * Desc:
 */
public class ClassEmitterTest {

    public static void main(String[] args) throws Exception{
        testIncompatibleArgsType();
    }

    public interface Test{
        Object test(Object[] args);
    }

    public static void invoke(String str){
        System.out.println(str);
    }

    public static void testIncompatibleArgsType() throws Exception{
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        ClassEmitter classEmitter = new ClassEmitter(classWriter);

        String className = "com.cglib.GeneratedIncompatibleArgsTypeTest";
        classEmitter.begin_class(Constants.V1_8, Constants.ACC_PUBLIC, className,
                Type.getType(Object.class), null, Constants.SOURCE_FILE);

        CodeEmitter staticEmitter = classEmitter.begin_static();
        staticEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
        staticEmitter.visitLdcInsn(className + " init");
        staticEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Constants.TYPE_STRING}));
        staticEmitter.return_value();
        staticEmitter.end_method();

        CodeEmitter constructor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature(Constants.CONSTRUCTOR_NAME,"()V"),null);
        constructor.load_this();
        constructor.super_invoke_constructor();
        constructor.return_value();
        constructor.end_method();

        CodeEmitter codeEmitter = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature("test","()V"),null);
        codeEmitter.push(10);
        //codeEmitter.visitLdcInsn("test");
        codeEmitter.invoke_static(Type.getType(ClassEmitterTest.class), new Signature("invoke", Type.VOID_TYPE, new Type[]{Constants.TYPE_STRING}));
        codeEmitter.return_value();
        codeEmitter.end_method();

        classEmitter.end_class();

        byte[] content = classWriter.toByteArray();
        ClassLoaderUtils.saveClassFile(className.substring(className.lastIndexOf('.') + 1) + ".class", content);

        Class klass = ClassLoaderUtils.defineClass(Thread.currentThread().getContextClassLoader(),className,content);
        Object tar = klass.newInstance(); //Exception in thread "main" java.lang.VerifyError: Bad type on operand stack
        Method method = klass.getDeclaredMethod("test");
        method.invoke(tar);
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

        //增加generic
        //<T> void test(List<T> args)
        Signature s = TypeUtils.parseSignature("void test(java.util.List)");
        //<T:Ljava/lang/Object;>(Ljava/util/List<TT;>;)V
        SignatureWriter signatureWriter = new SignatureWriter();
        signatureWriter.visitFormalTypeParameter("T");
        signatureWriter.visitClassType(Type.getType(Object.class).getInternalName());
        signatureWriter.visitEnd();//visitEnd -> end the class or interface type -> append '>' or ';'

        SignatureVisitor v = signatureWriter.visitParameterType();
        v.visitClassType(Type.getType(List.class).getInternalName());
        SignatureVisitor vv = v.visitTypeArgument('=');
        vv.visitTypeVariable("T");
        v.visitEnd();

        signatureWriter.visitReturnType().visitBaseType('V');
        System.out.println(signatureWriter.toString());

        MethodVisitor mv = classEmitter.visitMethod(Constants.ACC_PUBLIC, s.getName(), s.getDescriptor(), signatureWriter.toString(),null);

        //增加注解
        AnnotationVisitor annotationVisitor = mv.visitAnnotation("Lcom/annotation/TargetMethod;", true);
        annotationVisitor.visit("name","test");
        annotationVisitor.visitEnd();

        mv.visitInsn(Constants.RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();


        CodeEmitter codeEmitter = classEmitter.begin_method(Constants.ACC_PUBLIC, TypeUtils.parseSignature("Object test(Object[])"),null);

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
        codeEmitter.aconst_null();
        codeEmitter.return_value();
        codeEmitter.end_method();

        classEmitter.end_class();

        byte[] content = classWriter.toByteArray();
        ClassLoaderUtils.saveClassFile("GeneratedSubTest.class", content);

        //Test test = ClassLoaderUtils.defineInstance(Thread.currentThread().getContextClassLoader(),"com.cglib.GeneratedSubTest",content);
        Class klass = ClassLoaderUtils.defineClass(Thread.currentThread().getContextClassLoader(), "com.cglib.GeneratedSubTest", content);
        Constructor method = klass.getDeclaredConstructor(String.class);
        Test test = (Test)method.newInstance("value passed to test");


        test.test(null);

        Method m = klass.getMethod("test", List.class);
        System.out.println(m.isAnnotationPresent(TargetMethod.class));
        TargetMethod targetMethod = m.getAnnotation(TargetMethod.class);
        System.out.println(targetMethod.name());
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