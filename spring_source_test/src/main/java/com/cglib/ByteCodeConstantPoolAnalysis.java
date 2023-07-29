package com.cglib;

import com.utils.ClassLoaderUtils;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import jdk.internal.org.objectweb.asm.Opcodes;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
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
 *
 */
public class ByteCodeConstantPoolAnalysis {


    public static void main(String[] args) throws Exception{

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassEmitter classEmitter = new ClassEmitter(classWriter);

        String className = "com.cglib.GeneratedStringTableTest";
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

        //ClassWriter会常量池化相同的字符串，int字面量等
        CodeEmitter codeEmitter = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature("test","()V"),null);
        codeEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
        codeEmitter.push("test");
        codeEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Constants.TYPE_STRING}));
        codeEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
        codeEmitter.push("test");
        codeEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Constants.TYPE_STRING}));

        codeEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
        codeEmitter.push(Short.MAX_VALUE  + 1);//大于 Short.MAX_VALUE 会变成ldc, 小于则直接固化在字节码指令的参数里
        codeEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Type.INT_TYPE}));
        codeEmitter.getstatic(Type.getType(System.class),"out",Type.getType(PrintStream.class));
        codeEmitter.push(Short.MAX_VALUE  + 1);
        codeEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Type.INT_TYPE}));

        codeEmitter.return_value();
        codeEmitter.end_method();

        classEmitter.end_class();


        byte[] newContent = classWriter.toByteArray();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassLoaderUtils.saveClassFile("GeneratedStringTableTest.class", newContent);
        Class klass =  ClassLoaderUtils.defineClass(cl, "com.cglib.GeneratedStringTableTest", newContent);
        Method method = klass.getDeclaredMethod("test");
        method.invoke(klass.newInstance());
    }
}
