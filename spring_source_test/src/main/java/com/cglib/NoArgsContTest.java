package com.cglib;

import com.utils.ClassLoaderUtils;
import com.utils.LoggerUtils;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Signature;
import net.sf.cglib.transform.ClassEmitterTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/8/3
 * Time: 22:33
 * Desc: -Dsun.reflect.inflationThreshold=0
 */
public class NoArgsContTest {

    public NoArgsContTest(int i) {
    }

    public void show(){
        LoggerUtils.getLogger().info("show me");
    }

    public static void main(String[] args) throws Exception{
        //GeneratedConstructorAccessor
        Constructor constructor = ReflectionFactory.getReflectionFactory().newConstructor(NoArgsContTest.class,
                new Class[]{Integer.TYPE},
                new Class[]{},
                Modifier.PUBLIC,
                0,
                "<init>(I)V",//<init>()V will occur hs_err, noSuchMethodError will come up
                null,
                null);
        NoArgsContTest o = (NoArgsContTest)constructor.newInstance(1);
        o.show();

        //GeneratedSerializationConstructorAccessor
        constructor = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(NoArgsContTest.class,
                Object.class.getDeclaredConstructor());
        o = (NoArgsContTest)constructor.newInstance();
        o.show();

        testNoArgsConst();
    }

    public static void testNoArgsConst() throws Exception{
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassReader classReader = new ClassReader(cl.getResourceAsStream("com/cglib/NoArgsConstructor.class"));

        ClassEmitterTransformer transformer = new ClassEmitterTransformer() {

            @Override
            public void begin_class(int version, int access, String className, Type superType, Type[] interfaces, String source) {
                //remove this method will get verification exception
                try {
                    Class c = Class.forName("sun.reflect.SerializationConstructorAccessorImpl");

                    super.begin_class(version, access, className, Type.getType(c), interfaces, source);
                }catch (Exception e){
                    LoggerUtils.getLogger().error("",e);
                }
            }

            @Override
            public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
                CodeEmitter codeEmitter = super.begin_method(access, sig, exceptions);
                if(sig.getName().contains("getNewObject")) {
                    //failed !!! 虽然按照GeneratedSerializationConstructorAccessor的实现操作，但是依然报错
                    //只是new操作，不dup和调用<init>方法，最终是会报错的
                    codeEmitter.new_instance(Type.getType(NoArgsContTest.class));
                    codeEmitter.dup();
                    codeEmitter.invoke_constructor(Type.getType(Object.class), new Signature("<init>","()V"));
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

        ClassLoaderUtils.saveClassFile("NoArgsConstructor.class", newContent);

        Class klass =  ClassLoaderUtils.defineClass(cl, "com.cglib.NoArgsConstructor", newContent);
        NoArgsConstructor constuctor = (NoArgsConstructor)klass.newInstance();
        NoArgsContTest test = (NoArgsContTest)constuctor.getNewObject();
        test.show();
    }

}