package com.antlr;

import com.dsl.SimpleCalculatorParser;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SimpleCalculatorCachedVisitor extends SimpleCalculatorInterpretVisitor {


    private final Map<String, Integer> cache;

    public SimpleCalculatorCachedVisitor(Map<String, Integer> cache) {
        this.cache = cache;
    }

    @Override
    public Integer visitFirst(SimpleCalculatorParser.FirstContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        return super.visitFirst(ctx);
    }

    @Override
    public Integer visitMul(SimpleCalculatorParser.MulContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        return super.visitMul(ctx);
    }

    @Override
    public Integer visitSub(SimpleCalculatorParser.SubContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        return super.visitSub(ctx);
    }

    @Override
    public Integer visitAdd(SimpleCalculatorParser.AddContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        return super.visitAdd(ctx);
    }
}
