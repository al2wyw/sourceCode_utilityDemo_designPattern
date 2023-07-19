package com.cglib;

import com.utils.ClassLoaderUtils;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
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
 */
public class ByteCodeMaxLoopAnalysis {

    public static void main(String[] args) throws Exception{
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassReader classReader = new ClassReader(cl.getResourceAsStream("com/dynamicInvoke/InstructionsTest.class"));

        ClassNode cn = new ClassNode();
        classReader.accept(cn, ClassReader.EXPAND_FRAMES);

        //分析树形结构, 找到对应的label节点
        Set<String> visited = new HashSet<>(); //visited labels
        Set<String> found = new HashSet<>(); //continue and loop-end will all back edge
        for (MethodNode method : cn.methods) {
            if (!method.name.equals("main")) {
                continue;
            }
            InsnList list = method.instructions;
            AbstractInsnNode node = list.getFirst();
            while (node != null) {
                if (node instanceof LabelNode) {
                    LabelNode label = (LabelNode) node;
                    visited.add(label.getLabel().toString());
                }
                if (node instanceof JumpInsnNode) {
                    JumpInsnNode jump =  (JumpInsnNode) node;
                    LabelNode tar = jump.label;
                    if (visited.contains(tar.getLabel().toString()) && !found.contains(tar.getLabel().toString())) {
                        found.add(tar.getLabel().toString());

                        System.out.printf("find the loop jump %s, %s \n", jump.toString(), tar.getLabel().toString());
                    }
                }
                node = node.getNext();
            }
        }

        //修改树形结构, 增加nop指令
        for (MethodNode method : cn.methods) {
            if (!method.name.equals("main")) {
                continue;
            }

            InsnList list = method.instructions;
            AbstractInsnNode node = list.getFirst();
            while (node != null) {
                if (node instanceof LabelNode) {
                    LabelNode label = (LabelNode) node;
                    if (found.contains(label.getLabel().toString())) {
                        System.out.printf("find the loop label %s \n", label.getLabel().toString());


                        /*InsnList newList = new InsnList();
                        newList.add(new LdcInsnNode(label.getLabel().toString()));
                        newList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                Type.getType(MaxLoopChecker.class).getInternalName(),
                                "checkLoop",
                                new Signature("", Type.VOID_TYPE, new Type[]{Type.getType(String.class)}).getDescriptor(),
                                false));
                        method.instructions.insert(node, newList);*/
                        method.instructions.insertBefore(node, new InsnNode(Opcodes.NOP));
                        method.instructions.insert(node, new InsnNode(Opcodes.NOP));
                    }
                }
                node = node.getNext();
            }
        }

        //transform
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        cn.accept(classWriter);
        transform(classWriter.toByteArray());
    }

    public static void transform(byte[] codes) throws Exception {
        //transform
        ClassEmitterTransformer cv = new ClassEmitterTransformer() {
            @Override
            public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
                CodeEmitter codeEmitter = super.begin_method(access, sig, exceptions);
                String name = sig.getName();
                if(!name.contains("main")){
                    return codeEmitter;
                }

                return new ByteCodeMaxLoopEmitter(codeEmitter);
            }


        };

        ClassReader classReader = new ClassReader(codes);
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cv.setTarget(classWriter);

        classReader.accept(cv, ClassReader.EXPAND_FRAMES);
        byte[] newContent = classWriter.toByteArray();

        ClassLoaderUtils.saveClassFile("InstructionsTest.class", newContent);
        Class klass =  ClassLoaderUtils.defineClass(cl, "com.dynamicInvoke.InstructionsTest", newContent);
        Method method = klass.getDeclaredMethod("main", String[].class);
        method.invoke(null, (Object) new String[] {"1", "1", "3", "4"});
    }
}
