package com.antlr;

import com.dsl.SimpleCalculatorParser;
import com.dsl.SimpleCalculatorBaseVisitor;
import java.util.HashMap;
import java.util.Map;

public class SimpleCalculatorInterpretVisitor extends SimpleCalculatorBaseVisitor<Integer> {

    private final Map<String, Integer> variables;

    public SimpleCalculatorInterpretVisitor() {
        variables = new HashMap<>();
    }

    public void addVariable(String name, Integer value) {
        variables.put(name, value);
    }

    @Override
    public Integer visitInt(SimpleCalculatorParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitFirst(SimpleCalculatorParser.FirstContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Integer visitMul(SimpleCalculatorParser.MulContext ctx) {
        return visit(ctx.expr(0)) * visit(ctx.expr(1));
    }

    @Override
    public Integer visitSub(SimpleCalculatorParser.SubContext ctx) {
        return visit(ctx.expr(0)) - visit(ctx.expr(1));
    }

    @Override
    public Integer visitAdd(SimpleCalculatorParser.AddContext ctx) {
        return visit(ctx.expr(0)) + visit(ctx.expr(1));
    }

    @Override
    public Integer visitID(SimpleCalculatorParser.IDContext ctx) {
        return variables.get(ctx.getText());
    }
}
