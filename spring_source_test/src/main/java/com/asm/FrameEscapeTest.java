package com.asm;


import com.utils.ClassLoaderUtils;
import org.objectweb.asm.*;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/7/7
 * Time: 18:00
 * Desc: failed !!!
 */
public class FrameEscapeTest {

    public static void main( String args[] ) {
        int t = 100;
        try{
            int i = 0;
            i++;
            i = i + 10;

        }catch (Exception e){
            int j = 10;
            System.out.println(j);
        }
        try {
            byte[] code = dump();
            Class klass = ClassLoaderUtils.defineClass(FrameEscapeTest.class.getClassLoader(),"com.asm.Main",code);
            Method method = klass.getDeclaredMethod("main", String[].class);
            method.invoke(null, (Object) null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] dump() throws Exception {

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, "com/asm/Main", null, "java/lang/Object", null);

        cw.visitSource("Main.java", null);

        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(10, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/asm/Main;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
            mv.visitLabel(l0);
            mv.visitLineNumber(13, l0);
            mv.visitLdcInsn("test");
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            mv.visitLabel(l1);
            mv.visitLineNumber(19, l1);
            Label l3 = new Label();
            mv.visitJumpInsn(Opcodes.GOTO, l3);
            mv.visitLabel(l2);
            mv.visitLineNumber(16, l2);
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(17, l4);
            mv.visitIntInsn(Opcodes.BIPUSH, 10);
            mv.visitVarInsn(Opcodes.ISTORE, 2);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLineNumber(18, l5);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 2);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            mv.visitLabel(l3);
            mv.visitLineNumber(20, l3);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"java/lang/String"}, 0, null);
            /*mv.visitTypeInsn(Opcodes.NEW, "java/lang/String");
            mv.visitInsn(Opcodes.DUP);
            mv.visitLdcInsn("111");
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/String", "<init>", "(Ljava/lang/String;)V", false);
            mv.visitVarInsn(Opcodes.ASTORE, 1);*/
            Label l6 = new Label();
            mv.visitLabel(l6);
            mv.visitLineNumber(21, l6);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label l7 = new Label();
            mv.visitLabel(l7);
            mv.visitLineNumber(22, l7);
            mv.visitInsn(Opcodes.RETURN);
            Label l8 = new Label();
            mv.visitLabel(l8);
            mv.visitLocalVariable("j", "I", null, l5, l3, 2);
            mv.visitLocalVariable("e", "Ljava/lang/Exception;", null, l4, l3, 1);
            mv.visitLocalVariable("args", "[Ljava/lang/String;", null, l0, l8, 0);
            mv.visitLocalVariable("o", "Ljava/lang/String;", null, l6, l8, 1);
            mv.visitMaxs(3, 3);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
