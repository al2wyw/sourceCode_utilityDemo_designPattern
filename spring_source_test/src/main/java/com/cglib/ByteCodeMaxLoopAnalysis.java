package com.cglib;

import com.utils.ClassLoaderUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import jdk.internal.org.objectweb.asm.Opcodes;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Signature;
import net.sf.cglib.transform.ClassEmitterTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-07-18
 * Time: 10:07
 * Description:
 *      visitor接口类似于xml的sax基于事件的流式接口
 *      tree接口类似于xml的dom基于树形的模型接口
 *      ClassVisitor: 方法调用顺序
 *      visit
 *      visitSource? visitOuterClass? ( visitAnnotation | visitAttribute )*
 *      ( visitInnerClass | visitField | visitMethod )*
 *      visitEnd
 *      MethodVisitor: 方法调用顺序
 *      visitAnnotationDefault?
 *      (visitAnnotation |visitParameterAnnotation |visitAttribute )*
 *      ( visitCode
 *      (visitTryCatchBlock |visitLabel |visitFrame |visitXxxInsn | visitLocalVariable |visitLineNumber )*
 *      visitMaxs )?
 *      visitEnd
 *      局部变量部分和操作数栈部分中的每个槽(slot)可以保存除 long 和 double 变量之外的 任意 Java 值。long 和 double 变量需要两个槽
 *      栈映射帧 说明: 仅为那些对应于跳转目标或异常处理器(handler)的指令，或者跟在无条件跳转指令之后的指令包含帧 (更多内容参考文档)
 *      栈映射帧重算的前置条件: 1)插入的代码改变操作数栈，2) 插入代码中包含跳转指令，3) 原代码的跳转指令(控制流图)被修改
 *      ClassWriter COMPUTE_FRAMES 包含 COMPUTE_MAXS 自动计算帧栈和本地变量是由ClassWriter提供的
 *      为了自动计算帧，有时需要计算两个给定类的公共超类，ClassWriter 类会在 getCommonSuperClass 方法中进行这一计算，如果遇到相互引用的类要重写此方法
 *      转换方法(无状态/有状态)需要考虑的东西，visitMaxs和visitFrame(例子说明)
 *      有状态转换RemoveAddZeroAdapter,RemoveGetFieldPutFieldAdapter的实现很巧妙
 *      标记和帧对转换方法的影响
 *      AdviceAdapter LocalVariablesSorter 等适配器的介绍
 *
 *      generate ast from bytecode ???
 */
public class ByteCodeMaxLoopAnalysis {

    public static byte[] transform(String classFile, String classNameTar) throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassReader classReader = new ClassReader(cl.getResourceAsStream(classFile));

        ClassNode cn = new ClassNode();
        classReader.accept(cn, ClassReader.EXPAND_FRAMES);

        //分析树形结构, 找到对应的label节点
        Map<String, LabelNode> visited = new HashMap<>(); //visited labels
        Map<String, LabelNode> forwardRef = new HashMap<>();//jump forward label reference
        Map<String, LabelNode> backwardRef = new HashMap<>();//jump backward label reference, continue and loop-end-bracket will all backward
        //多个跳转指令具有相同的目标label(既有往前跳，也有往回跳)，需要新增一个label进行区分
        Map<String, LabelNode> label2Rep = new HashMap<>();
        for (MethodNode method : cn.methods) {
            InsnList list = method.instructions;//soot的AsmMethodSource也是从instructions转换为CFG ???
            AbstractInsnNode node = list.getFirst();
            while (node != null) {
                if (node instanceof LabelNode) {
                    LabelNode label = (LabelNode) node;
                    visited.put(label.getLabel().toString(), label);
                }
                if (node instanceof JumpInsnNode) {
                    JumpInsnNode jump =  (JumpInsnNode) node;
                    LabelNode tar = jump.label;
                    String labelStr = tar.getLabel().toString();
                    if (visited.containsKey(labelStr) && !backwardRef.containsKey(labelStr)) {
                        backwardRef.put(labelStr, tar);
                        System.out.printf("find the backwardRef %s, %s \n", jump.toString(), labelStr);
                        //修改树形结构, 增加nop指令

                        //NOP指令用作标识符
                        if (forwardRef.containsKey(labelStr)) {//同时具有前跳和后跳
                            LabelNode rep = new LabelNode();
                            method.instructions.insert(tar, new InsnNode(Opcodes.NOP));
                            method.instructions.insert(tar, rep);
                            method.instructions.insert(tar, new InsnNode(Opcodes.NOP));
                            //把目标label替换成新的label
                            jump.label = rep;
                            label2Rep.put(labelStr, rep);//其他的后跳节点的目标label也是此label，后续一一替换
                        } else { //只有后跳
                            method.instructions.insertBefore(tar, new InsnNode(Opcodes.NOP));
                            method.instructions.insert(tar, new InsnNode(Opcodes.NOP));
                        }
                    }
                    if (!visited.containsKey(labelStr) && !forwardRef.containsKey(labelStr)) {
                        forwardRef.put(labelStr, tar);
                        System.out.printf("find the forwardRef %s, %s \n", jump.toString(), labelStr);
                    }
                    //替换所有后跳节点的label成新的label，此时跳跃节点的目标label是待替换label的话都是后跳节点，所以不用再判断当前跳跃节点是否后跳节点
                    if (label2Rep.containsKey(labelStr)) {
                        jump.label = label2Rep.get(labelStr);
                    }
                }
                node = node.getNext();
            }
        }

        //transform
        ClassEmitterTransformer cv = new ClassEmitterTransformer() {

            @Override
            public void begin_class(int version, int access, String className, Type superType, Type[] interfaces,
                    String source) {
                super.begin_class(version, access, classNameTar, superType, interfaces, source);
            }

            @Override
            public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
                CodeEmitter codeEmitter = super.begin_method(access, sig, exceptions);

                return new ByteCodeMaxLoopEmitter(codeEmitter);
            }
        };
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        //CheckClassAdapter checkClassAdapter = new CheckClassAdapter(classWriter);
        //cv.setTarget(checkClassAdapter);
        cv.setTarget(classWriter);

        cn.accept(cv);
        return classWriter.toByteArray();
    }

    public static void main(String[] args) throws Exception{
        byte[] newContent = transform("com/dynamicInvoke/InstructionsTest.class", "com.dynamicInvoke.InstructionsTest");

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassLoaderUtils.saveClassFile("InstructionsTest.class", newContent);
        Class klass =  ClassLoaderUtils.defineClass(cl, "com.dynamicInvoke.InstructionsTest", newContent);
        Method method = klass.getDeclaredMethod("main", String[].class);
        method.invoke(null, (Object) new String[] {"1", "1", "11", "4"});
    }
}
