package com.dynamicInvoke;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/24
 * Time: 10:52
 * Desc:
 */
public class InvokeDynamicCreator {

    public static void main(final String[] args) throws Exception {
        final String outputClassName = "com/dynamicInvoke/Dynamic";
        try (FileOutputStream fos
                     = new FileOutputStream(new File("target/classes/" + outputClassName + ".class"))) {
            fos.write(dump(outputClassName, "bootstrap", "(Ljava/lang/String;)V"));
        }
    }

    public static byte[] dump(String outputClassName, String bsmName, String targetMethodDescriptor)
            throws Exception {
        final ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        // 为引导类搭建基本的元数据
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, outputClassName, null, "java/lang/Object", null);

        // 创建标准的void构造器
        mv = cw.visitMethod(ACC_PUBLIC, "", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // 创建标准的main方法
        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
                MethodType.class, String.class);
        Handle bootstrap = new Handle(H_INVOKESTATIC, "com/dynamicInvoke/InvokeDynamicCreator", bsmName,
                mt.toMethodDescriptorString(),false);
        mv.visitLdcInsn("come arg1");
        mv.visitInvokeDynamicInsn("runDynamic", targetMethodDescriptor, bootstrap, "test");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        cw.visitEnd();

        return cw.toByteArray();
    }

    private static void targetMethod(String arg1) {
        System.out.println("Hello World! " + arg1);
    }

    public static CallSite bootstrap(MethodHandles.Lookup caller, String name, MethodType type, String arg1) throws NoSuchMethodException, IllegalAccessException {
        System.out.println("arg1=" + arg1);
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 需要使用lookupClass()，因为这个方法是静态的
        final Class currentClass = lookup.lookupClass();
        final MethodType targetSignature = MethodType.methodType(void.class, String.class);
        final MethodHandle targetMH = lookup.findStatic(currentClass, "targetMethod", targetSignature);
        return new ConstantCallSite(targetMH.asType(type));
    }
}
