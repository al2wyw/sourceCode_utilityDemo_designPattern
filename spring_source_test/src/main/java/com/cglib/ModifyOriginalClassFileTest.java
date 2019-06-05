package com.cglib;

import com.utils.ClassLoaderUtils;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.Signature;
import net.sf.cglib.transform.ClassEmitterTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/30
 * Time: 17:38
 * Desc:
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
        //使用class reader来构建class writer的话，class pool和boostrap methods会保持不变
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
    }
}
