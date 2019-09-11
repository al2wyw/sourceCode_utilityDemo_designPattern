package com.asm;

import com.google.common.collect.Sets;
import com.utils.ClassLoaderUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/9/11
 * Time: 16:26
 * Desc:
 */
public class AdviceAdapterTest {

    public static void main(String args[]) throws Exception{
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        MyClassWriter cw = new MyClassWriter(Opcodes.ASM6, classWriter);

        ClassReader classReader = new ClassReader(cl.getResourceAsStream("com/asm/Test.class"));
        classReader.accept(cw, ClassReader.EXPAND_FRAMES);

        byte[] newContent = classWriter.toByteArray();
        ClassLoaderUtils.saveClassFile("Test.class",newContent);
        Class klass =  ClassLoaderUtils.defineClass(cl, "com.asm.Test", newContent);
        Method method = klass.getDeclaredMethod("test");
        method.invoke(klass.newInstance());
    }

    public static class MyClassWriter extends ClassVisitor{

        public MyClassWriter(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            return new MyAdvice(api, mv, access, name, desc);
        }
    }

    public static class MyAdvice extends AdviceAdapter {

        private String name;
        private String desc;
        private static Set<String> names = Sets.newHashSet("test","test1");

        public MyAdvice(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
            this.name = name;
            this.desc = desc;
        }

        //这里应该还是编写jvm执行代码，而不是java代码
        @Override
        protected void onMethodEnter() {
            if(!names.contains(name)){
                return;
            }
            this.newInstance(Type.getType(MyAdvisor.class));
            this.dup();
            this.push(name);
            this.push(desc);
            this.invokeConstructor(Type.getType(MyAdvisor.class), new org.objectweb.asm.commons.Method("<init>",
                    Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(String.class), Type.getType(String.class))));
            this.invokeVirtual(Type.getType(MyAdvisor.class), org.objectweb.asm.commons.Method.getMethod("void onMethodEnter ()"));

        }

        @Override
        protected void onMethodExit(int opcode) {
            if(!names.contains(name)){
                return;
            }
            this.newInstance(Type.getType(MyAdvisor.class));
            this.dup();
            this.push(name);
            this.push(desc);
            this.invokeConstructor(Type.getType(MyAdvisor.class), new org.objectweb.asm.commons.Method("<init>",
                    Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(String.class), Type.getType(String.class))));
            this.invokeVirtual(Type.getType(MyAdvisor.class), org.objectweb.asm.commons.Method.getMethod("void onMethodExit ()"));
        }
    }

    public static class MyAdvisor {

        private String name;
        private String desc;
        private long start;
        private static Stack<MyAdvisor> stacks = new Stack<>();
        private static Set<String> names = Sets.newHashSet("test","test1");

        public MyAdvisor(String name, String desc) {
            this.desc = desc;
            this.name = name;
        }

        public final void onMethodEnter() {
            if(!names.contains(name)){
                return;
            }
            start = System.currentTimeMillis();
            stacks.push(this);
            System.out.println("on method enter " + name + desc);

        }


        public final void onMethodExit() {
            if(!names.contains(name)){
                return;
            }
            MyAdvisor start = stacks.pop();
            if(start != null){
                System.out.println(name + desc +  " " + (System.currentTimeMillis() - start.start) + " ms");
            }
            System.out.println("on method exit " + name + desc);
        }
    }
}
