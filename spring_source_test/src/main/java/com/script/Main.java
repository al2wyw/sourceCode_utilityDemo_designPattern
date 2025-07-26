package com.script;

import com.github.pandora.antlr.AntlrCompiler;
import com.github.pandora.compile.Compiler;
import com.github.pandora.ast.GenerateContext;
import com.github.pandora.ast.ScriptNode;
import com.github.pandora.compile.CompileContext;
import com.github.pandora.eval.EvalTemplate;
import com.github.pandora.eval.EvaluationContext;
import com.github.pandora.eval.EvaluationValue;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        Integer i = Integer.valueOf("1233");
        Float j = Float.valueOf("34.23");
        Float k = 1 +
                -j;
        Double b = (double)k.floatValue();
        if (j > i) {
            System.out.println(k);
        }
        System.out
                .println(k);


        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                "script/nested_if.script");
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile(stream, new CompileContext(Model.class));

        GenerateContext ast = new GenerateContext(Model.class);
        // GenerateContext.enableDebug();
        // GenerateContext.enableOutPutClassFile(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        node.generateCode(ast);

        EvalTemplate template = ast.getTemplateClass().newInstance();
        Model model = new Model();
        if (!model.flag1)
            model.int3 = 100;
        else if (model.int1 > 800)
            model.int3 = model.int2 & 1;
        else
            model.int3 = 1;
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
        System.out.println(model);
    }

    public Object eval(Object ob) {
        Model model = (Model) ob;

        model.int1 = 666;
        model.int2 = 879;
        model.float1 = 12.34f;
        model.float2 = 13.44f;
        model.name1 = "my peter";
        model.flag1 = model.int1 >= model.int1;
        model.flag2 = model.float1 > model.int2;
        model.flag3 = model.flag1 && true;
        model.double1 = (model.float1 + model.float2) * model.int1;
        // model.ret = (model.float1 + model.float2) * model.int1;
        model.int3 = model.int1 * (model.int1 + model.int2);

        if (model.int1 < 100 || model.flag1)
            model.int3 = 100;
        else if (model.int1 > 800)
            model.int3 = 10;
        else
            model.int3 = 1;

        return null;
    }
}