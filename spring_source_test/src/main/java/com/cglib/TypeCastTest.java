package com.cglib;

import com.utils.ClassLoaderUtils;
import java.io.PrintStream;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.Local;
import net.sf.cglib.core.Signature;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-07-14
 * Time: 15:22
 * Description:
 *      checkcast 是必须的，不然后续的 getfield 会检测类型报错
 *      invoke virtual special interface 都会消耗一个object(处于栈底) objref arg1 arg2 ... (loc1 loc2)
 *      使用cglib接口时不要同时使用asm的低级接口 !!!
 */
public class TypeCastTest {

    public static class Target {
        public int value;
        public String key;
    }

    public interface Invoker {
        Object invoke(Object arg);
    }

    public static void main(String[] args) throws Exception{
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        ClassEmitter classEmitter = new ClassEmitter(classWriter);

        String className = "com.cglib.GeneratedTypeCastTest";
        classEmitter.begin_class(Constants.V1_8, Constants.ACC_PUBLIC, className,
                Type.getType(Object.class), new Type[]{Type.getType(Invoker.class)}, Constants.SOURCE_FILE);

        CodeEmitter constructor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature(Constants.CONSTRUCTOR_NAME,"()V"),null);
        constructor.load_this();
        constructor.super_invoke_constructor();
        constructor.return_value();
        constructor.end_method();

        CodeEmitter codeEmitter = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature("invoke","(Ljava/lang/Object;)Ljava/lang/Object;"),null);

        Label start= codeEmitter.make_label();
        Label end= codeEmitter.make_label();

        codeEmitter.push(10);
        codeEmitter.push(11);
        codeEmitter.if_icmp(CodeEmitter.GT, end);

        Local i  = codeEmitter.make_local(Type.INT_TYPE);
        codeEmitter.push(0);
        codeEmitter.store_local(i);
        codeEmitter.mark(start);
        codeEmitter.load_arg(0);
        codeEmitter.checkcast(Type.getType(Target.class));
        codeEmitter.getfield(Type.getType(Target.class), "value", Type.INT_TYPE);
        codeEmitter.getstatic(Type.getType(System.class),"out", Type.getType(PrintStream.class));
        codeEmitter.swap();
        codeEmitter.invoke_virtual(Type.getType(PrintStream.class),new Signature("println",Type.VOID_TYPE,new Type[]{Type.INT_TYPE}));

        codeEmitter.iinc(i, 1);
        //codeEmitter.visitIincInsn(i.getIndex(), 1);//不要直接使用低级api
        codeEmitter.load_local(i);
        codeEmitter.push(10);
        codeEmitter.if_icmp(CodeEmitter.LT, start);

        codeEmitter.mark(end);
        codeEmitter.load_arg(0);
        codeEmitter.return_value();
        codeEmitter.end_method();

        classEmitter.end_class();

        byte[] content = classWriter.toByteArray();
        ClassLoaderUtils.saveClassFile(className.substring(className.lastIndexOf('.') + 1) + ".class", content);

        Class klass = ClassLoaderUtils.defineClass(Thread.currentThread().getContextClassLoader(),className,content);
        Invoker invoker = (Invoker)klass.newInstance();
        Target tar = new Target();
        tar.key = "key";
        tar.value = 100;
        invoker.invoke(tar);
    }
}
