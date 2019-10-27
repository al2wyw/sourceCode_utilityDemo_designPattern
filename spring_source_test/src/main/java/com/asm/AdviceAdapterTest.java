package com.asm;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.utils.ClassLoaderUtils;
import com.utils.LoggerUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

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
        ClassLoaderUtils.saveClassFile("Test.class", newContent);
        Test test =  ClassLoaderUtils.defineInstance(cl, "com.asm.Test", newContent);
        Preconditions.checkNotNull(test);
        try {
            test.test();
        }catch (Exception e){
            LoggerUtils.getLogger().error("", e);
        }
        test.testGen().test();
    }

    public static boolean isPrimitive(Type type) {
        switch (type.getSort()) {
            case Type.ARRAY:
            case Type.OBJECT:
            case Type.METHOD:
                return false;
            default:
                return true;
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
        private Label start;
        private Label end;
        private static final Type MY_CALL_BACK_TYPE = Type.getType(MyCallBack.class);
        private static final Type STRING_TYPE = Type.getType(String.class);
        private static final Type EXP_TYPE = Type.getType(Exception.class);
        private static final Set<String> NAMES = Sets.newHashSet("test", "test1", "testGen");
        private static final org.objectweb.asm.commons.Method ENTER_CB = org.objectweb.asm.commons.Method.getMethod("void onMethodEnter ()");
        private static final org.objectweb.asm.commons.Method EXIT_CB = org.objectweb.asm.commons.Method.getMethod("void onMethodExit (java.lang.Object)");
        private static final org.objectweb.asm.commons.Method EXIT_RE_CB = org.objectweb.asm.commons.Method.getMethod("java.lang.Object onMethodReExit (java.lang.Object)");


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
            this.invokeVirtual(MY_CALL_BACK_TYPE, ENTER_CB);
            start = mark();
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            if(!NAMES.contains(name)){
                super.visitMaxs(maxStack, maxLocals);
                return;
            }
            end = mark();
            catchException(start, end, EXP_TYPE);
            dup();
            this.loadLocal(localIndex);
            swap();
            this.invokeVirtual(MY_CALL_BACK_TYPE, EXIT_RE_CB);
            Type type = EXIT_RE_CB.getReturnType();
            if(type != Type.VOID_TYPE){
                if (type.getSize() == 2) {
                    pop2();
                } else {
                    pop();
                }
            }
            throwException();

            super.visitMaxs(maxStack, maxLocals);
        }

        @Override
        protected void onMethodExit(int opcode) {
            if(!NAMES.contains(name)){
                return;
            }
            Type returnType = Type.getReturnType(this.methodDesc);
            if(opcode==ATHROW){
                return;
            }
            if(opcode==RETURN) {
                visitInsn(ACONST_NULL);
            } else if(opcode==ARETURN ) {
                dup();
            } else {
                if(opcode==LRETURN || opcode==DRETURN) {
                    dup2();
                }else {
                    dup();
                }
                box(returnType);
            }
            this.loadLocal(localIndex);
            swap();//ok with long
            this.invokeVirtual(MY_CALL_BACK_TYPE, EXIT_RE_CB);
            if(!isPrimitive(returnType)) {
                //Type 'java/lang/Object' (current frame, stack[1]) is not assignable to 'com/asm/Test' (from method signature)
                checkCast(Type.getReturnType(this.methodDesc));
            }else{
                pop();
            }
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
            LoggerUtils.getLogger().info("on method enter " + name + desc);
        }


        public final void onMethodExit(Object value) {
            LoggerUtils.getLogger().info("return value " + value);
            if(stacks.get().size() == 0){
                return;
            }
            MyCallBack start = stacks.get().pop();
            if(start != null){
                String name = start.name;
                String desc = start.desc;
                LoggerUtils.getLogger().info(name + desc +  " " + (System.currentTimeMillis() - start.start) + " ms");
                LoggerUtils.getLogger().info("on method exit " + name + desc);
            }
        }

        public final Object onMethodReExit(Object value) {
            this.onMethodExit(value);
            return new TestExt();
        }
    }
}
