package com.antlr;

import com.dsl.SimpleCalculatorParser;
import java.util.Map;

public class SimpleCalculatorCompileCachedVisitor extends SimpleCalculatorCompileVisitor {

    private final Map<String, Integer> cache;

    public SimpleCalculatorCompileCachedVisitor(String name, Map<String, Integer> cache) {
        super(name);
        this.cache = cache;
    }

    @Override
    public Integer visitFirst(SimpleCalculatorParser.FirstContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            calculate.push(cache.get(key));
            return cache.get(key);
        }
        return super.visitFirst(ctx);
    }

    @Override
    public Integer visitMul(SimpleCalculatorParser.MulContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            calculate.push(cache.get(key));
            return cache.get(key);
        }
        return super.visitMul(ctx);
    }

    @Override
    public Integer visitSub(SimpleCalculatorParser.SubContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            calculate.push(cache.get(key));
            return cache.get(key);
        }
        return super.visitSub(ctx);
    }

    @Override
    public Integer visitAdd(SimpleCalculatorParser.AddContext ctx) {
        String key = ctx.toString();
        if (cache.containsKey(key)) {
            calculate.push(cache.get(key));
            return cache.get(key);
        }
        return super.visitAdd(ctx);
    }
}
