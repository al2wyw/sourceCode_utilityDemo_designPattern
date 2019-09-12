package com.asm;

import com.google.common.collect.Sets;
import com.utils.ClassLoaderUtils;
import com.utils.LoggerUtils;
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
        Test test = (Test)klass.newInstance();
        try {
            test.test();
        }catch (Exception e){
            LoggerUtils.getLogger().error("", e);
        }
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
        private int localIndex;
        private static final Type MY_CALL_BACK_TYPE = Type.getType(MyCallBack.class);
        private static final Type STRING_TYPE = Type.getType(String.class);
        private static final Set<String> NAMES = Sets.newHashSet("test","test1");

        public MyAdvice(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
            this.name = name;
            this.desc = desc;
        }
        //对原方法体需要使用try catch包裹起来，不然异常抛出时会跳过onMethodExit
        //这里应该还是编写jvm执行代码，而不是java代码
        @Override
        protected void onMethodEnter() {
            if(!NAMES.contains(name)){
                return;
            }
            this.newInstance(MY_CALL_BACK_TYPE);
            this.dup();
            this.push(name);
            this.push(desc);
            this.invokeConstructor(MY_CALL_BACK_TYPE, new org.objectweb.asm.commons.Method("<init>",
                    Type.getMethodDescriptor(Type.VOID_TYPE, STRING_TYPE, STRING_TYPE)));
            localIndex = this.newLocal(MY_CALL_BACK_TYPE);
            this.dup();
            this.storeLocal(localIndex);
            this.invokeVirtual(MY_CALL_BACK_TYPE, org.objectweb.asm.commons.Method.getMethod("void onMethodEnter ()"));

        }

        @Override
        protected void onMethodExit(int opcode) {
            if(!NAMES.contains(name)){
                return;
            }
            this.loadLocal(localIndex);
            this.invokeVirtual(MY_CALL_BACK_TYPE, org.objectweb.asm.commons.Method.getMethod("void onMethodExit ()"));
        }
    }

    public static class MyCallBack {

        private String name;
        private String desc;
        private long start;

        public static ThreadLocal<Stack<MyCallBack>> stacks = new ThreadLocal<Stack<MyCallBack>>(){
            @Override
            protected Stack<MyCallBack> initialValue() {
                return new Stack<>();
            }
        };

        public MyCallBack(String name, String desc) {
            this.desc = desc;
            this.name = name;
        }

        public final void onMethodEnter() {
            start = System.currentTimeMillis();
            stacks.get().push(this);
            System.out.println("on method enter " + name + desc);
        }


        public final void onMethodExit() {
            MyCallBack start = stacks.get().pop();
            if(start != null){
                String name = start.name;
                String desc = start.desc;
                System.out.println(name + desc +  " " + (System.currentTimeMillis() - start.start) + " ms");
                System.out.println("on method exit " + name + desc);
            }
        }
    }
}
