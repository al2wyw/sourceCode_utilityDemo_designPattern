package com.cglib;

import com.utils.ClassLoaderUtils;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.Signature;
import net.sf.cglib.transform.ClassEmitterTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/30
 * Time: 17:38
 * Desc: 使用class reader来构建class writer的话，class pool和boostrap methods会保持不变
 *       使用ClassNode可以分析到boostrap methods
 *
         visitInsn -> no operand ins
         visitVarInsn -> local variable ins
         visitTypeInsn -> NEW, ANEWARRAY, CHECKCAST or INSTANCEOF
         visitIntInsn -> BIPUSH, SIPUSH or NEWARRAY
         visitIincInsn -> IINC ins
         visitFieldInsn -> GETSTATIC, PUTSTATIC, GETFIELD or PUTFIELD
         visitMethodInsn
         visitInvokeDynamicInsn
         visitJumpInsn
         visitLdcInsn

        MAXSTACK -> 操作栈大小 MAXLOCALS -> 本地变量大小,不是数量, Type的getSize(long,double是2,void是0,其他是1)
 */
public class ModifyOriginalClassFileTest {

    public static void main(String[] args) throws Exception{
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassReader classReader = new ClassReader(cl.getResourceAsStream("com/dynamicInvoke/LambdaTest.class"));

        ClassEmitterTransformer cv = new ClassEmitterTransformer() {
            @Override
            public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
                CodeEmitter codeEmitter = super.begin_method(access, sig, exceptions);
                String name = sig.getName();
                if(name.contains("init")){
                    return codeEmitter;
                }
                codeEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
                codeEmitter.visitLdcInsn("my string");
                codeEmitter.invoke_virtual(Type.getType(PrintStream.class), new Signature("println", Type.VOID_TYPE, new Type[]{Constants.TYPE_STRING}));
                return codeEmitter;
            }
        };

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cv.setTarget(classWriter);

        classReader.accept(cv, ClassReader.EXPAND_FRAMES);
        byte[] newContent = classWriter.toByteArray();
        Class klass =  ClassLoaderUtils.defineClass(cl, "com.dynamicInvoke.LambdaTest", newContent);
        Method method = klass.getDeclaredMethod("main", String[].class);
        method.invoke(null, (Object) null);

        FileOutputStream fileOutputStream = new FileOutputStream("test.class");//class pool的index发生改变了
        fileOutputStream.write(newContent);
        fileOutputStream.close();

        //test for boostrap method analysis:
        ClassNode cn = new ClassNode();
        classReader.accept(cn, ClassReader.EXPAND_FRAMES);
        Map<String,MethodNode> methodNodeMap = cn.methods.stream()
                .collect(Collectors.toMap(methodNode -> methodNode.name + methodNode.desc, Function.identity()));

        Set<String> methodExplored = new HashSet<>();
        List<String> methodToExplore = new LinkedList<>();
        String owner = "LambdaTest";
        methodToExplore.add("test" + "()V");
        while(!methodToExplore.isEmpty()) {
            String name = methodToExplore.remove(0);
            if(!methodExplored.add(name)){
                continue;
            }
            MethodNode methodNode = methodNodeMap.get(name);

            Stream.of(methodNode.instructions.toArray())
                    .forEach(abstractInsnNode -> {
                        if (abstractInsnNode.getOpcode() == Opcodes.INVOKEDYNAMIC) {
                            InvokeDynamicInsnNode node = (InvokeDynamicInsnNode) abstractInsnNode;
                            Type type = (Type) node.bsmArgs[0];
                            Handle target = (Handle)node.bsmArgs[1];
                            if(target.getOwner().contains(owner)) {
                                System.out.println(target.getName() + target.getDesc());
                                methodToExplore.add(target.getName() + target.getDesc());
                            }
                        }
                    });
        }
    }
}
