package com.antlr;

import com.dsl.SimpleCalculatorBaseVisitor;
import com.dsl.SimpleCalculatorParser;
import com.utils.ClassLoaderUtils;
import java.util.HashMap;
import java.util.Map;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.Signature;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class SimpleCalculatorCompileVisitor extends SimpleCalculatorBaseVisitor<Integer> {

    private ClassWriter classWriter;

    private ClassEmitter classEmitter;

    protected CodeEmitter calculate;

    private String klassName;

    private String typeName;

    private String fieldName;

    public SimpleCalculatorCompileVisitor(String name) {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classEmitter = new ClassEmitter(classWriter);

        klassName = name;
        typeName = "L" + klassName.replace('.', '/') + ";";
        classEmitter.begin_class(Constants.V1_8, Constants.ACC_PUBLIC, klassName,
                Type.getType(Object.class), new Type[]{Type.getType(SimpleCalculator.class)}, Constants.SOURCE_FILE);

        fieldName = "variables";

        classEmitter.declare_field(Constants.ACC_PRIVATE | Constants.ACC_FINAL, fieldName,
                Type.getType(Map.class), null);

        CodeEmitter constructor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature(Constants.CONSTRUCTOR_NAME,"()V"),null);
        constructor.load_this();
        constructor.super_invoke_constructor();
        constructor.load_this();
        constructor.new_instance(Type.getType(HashMap.class));
        constructor.dup();
        constructor.invoke_constructor(Type.getType(HashMap.class));
        constructor.putfield(fieldName);
        constructor.return_value();
        constructor.end_method();

        CodeEmitter addVariable = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature("addVariable","(Ljava/lang/String;Ljava/lang/Integer;)V"),null);
        addVariable.load_this();
        addVariable.getfield(Type.getType(typeName),fieldName, Type.getType(Map.class));
        addVariable.load_arg(0);
        addVariable.load_arg(1);
        addVariable.invoke_interface(Type.getType(Map.class), new Signature("put","(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"));
        addVariable.pop();
        addVariable.return_value();
        addVariable.end_method();

        calculate = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature("calculate","()Ljava/lang/Integer;"),null);
    }

    public SimpleCalculator getSimpleCalculator() throws Exception {
        calculate.invoke_static(Type.getType(Integer.class), new Signature("valueOf", "(I)Ljava/lang/Integer;"));
        calculate.return_value();
        calculate.end_method();
        classEmitter.end_class();

        ClassLoaderUtils.saveClassFile(klassName.substring(klassName.lastIndexOf('.') + 1) + ".class", classWriter.toByteArray());
        Class<?> klass = ClassLoaderUtils.defineClass(Thread.currentThread().getContextClassLoader(), klassName, classWriter.toByteArray());
        return (SimpleCalculator) klass.newInstance();
    }

    @Override
    public Integer visitInt(SimpleCalculatorParser.IntContext ctx) {
        calculate.push(Integer.valueOf(ctx.INT().getText()));
        return 0;
    }

    @Override
    public Integer visitFirst(SimpleCalculatorParser.FirstContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Integer visitMul(SimpleCalculatorParser.MulContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        calculate.visitInsn(Opcodes.IMUL);
        return 0;
    }

    @Override
    public Integer visitSub(SimpleCalculatorParser.SubContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        calculate.visitInsn(Opcodes.ISUB);
        return 0;
    }

    @Override
    public Integer visitAdd(SimpleCalculatorParser.AddContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        calculate.visitInsn(Opcodes.IADD);
        return 0;
    }

    @Override
    public Integer visitID(SimpleCalculatorParser.IDContext ctx) {

        calculate.load_this();
        calculate.getfield(Type.getType(typeName), fieldName, Type.getType(Map.class));
        calculate.push(ctx.getText());
        calculate.invoke_interface(Type.getType(Map.class), new Signature("get","(Ljava/lang/Object;)Ljava/lang/Object;"));
        calculate.checkcast(Type.getType(Integer.class));
        calculate.invoke_virtual(Type.getType(Integer.class), new Signature("intValue", "()I"));
        return 0;
    }
}
