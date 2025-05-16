package com.antlr;

import com.dsl.SimpleCalculatorParser;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.antlr.v4.runtime.tree.ParseTree;

public class SimpleCalculatorConstantFoldVisitor extends SimpleCalculatorInterpretVisitor {

    private boolean isVariable = false;

    private final Map<String, Integer> cache;

    public SimpleCalculatorConstantFoldVisitor() {
        cache = new HashMap<>();
    }

    public Map<String, Integer> getCache() {
        return cache;
    }

    private <E extends SimpleCalculatorParser.ExprContext> Integer cacheHandle(E ctx, Function<E, Integer> fun) {
        boolean tmp = isVariable;
        isVariable = false;
        Integer ret = null;
        try {
            ret = fun.apply(ctx);
            return ret;
        } finally {
            if (!isVariable) {
                cache.put(ctx.toString(), ret);
                isVariable = tmp;
            }
        }
    }

    @Override
    public Integer visitFirst(SimpleCalculatorParser.FirstContext ctx) {
        return cacheHandle(ctx, super::visitFirst);
    }

    @Override
    public Integer visitMul(SimpleCalculatorParser.MulContext ctx) {
        return cacheHandle(ctx, super::visitMul);
    }

    @Override
    public Integer visitSub(SimpleCalculatorParser.SubContext ctx) {
        return cacheHandle(ctx, super::visitSub);
    }

    @Override
    public Integer visitAdd(SimpleCalculatorParser.AddContext ctx) {
        return cacheHandle(ctx, super::visitAdd);
    }

    @Override
    public Integer visitID(SimpleCalculatorParser.IDContext ctx) {
        isVariable = true;
        return super.visitID(ctx);
    }
}
