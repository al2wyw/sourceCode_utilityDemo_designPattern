package com.cglib;

import com.utils.ClassLoaderUtils;
import com.utils.LoggerUtils;
import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import net.sf.cglib.transform.ClassEmitterTransformer;
import net.sf.cglib.transform.TransformingClassGenerator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.io.FileOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/8/3
 * Time: 22:33
 * Desc: failed !!!
 */
public class NoArgsContTest {

    public NoArgsContTest(int i) {
    }

    public void show(){
        LoggerUtils.getLogger().info("show me");
    }

    public static void main(String[] args) throws Exception{
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassReader classReader = new ClassReader(cl.getResourceAsStream("com/cglib/NoArgsConstructor.class"));

        ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
            @Override
            public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
                CodeEmitter codeEmitter = super.begin_method(access, sig, exceptions);
                if(sig.getName().contains("getNewObject")) {
                    codeEmitter.new_instance(Type.getType(NoArgsContTest.class));
                    codeEmitter.dup();
                    codeEmitter.push(10);
                    codeEmitter.invoke_constructor(Type.getType(NoArgsContTest.class), new Signature("<init>","(I)V"));
                    codeEmitter.return_value();
                    codeEmitter.end_method();
                }
                return codeEmitter;
            }
        };
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        transformer.setTarget(classWriter);

        classReader.accept(transformer, ClassReader.EXPAND_FRAMES);
        byte[] newContent = classWriter.toByteArray();

        FileOutputStream fileOutputStream = new FileOutputStream("NoArgsConstructor.class");
        fileOutputStream.write(newContent);
        fileOutputStream.close();

        Class klass =  ClassLoaderUtils.defineClass(cl, "com.cglib.NoArgsConstructor", newContent);
        NoArgsConstructor constuctor = (NoArgsConstructor)klass.newInstance();
        NoArgsContTest test = (NoArgsContTest)constuctor.getNewObject();
        test.show();
    }

}