package com.antlr;

import com.dsl.RuleParserBaseVisitor;
import com.dsl.RuleParserParser;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.beanutils.PropertyUtils;

public class MyRuleParser extends RuleParserBaseVisitor<Object> {

    private final Map<String, Object> variables = new HashMap<>();

    public void addVariable(String name, Object value) {
        variables.put(name, value);
    }

    @Override
    public Object visitAssignAction(RuleParserParser.AssignActionContext ctx) {
        try {
            setObj(ctx.variable(), visit(ctx.complexValue()));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Object getObj(RuleParserParser.VariableContext va) throws Exception {
        String key = va.variableCategory().Identifier().getText();
        String field = va.property().getText();
        Object o =  variables.get(key);
        return PropertyUtils.getProperty(o, field);
    }

    public void setObj(RuleParserParser.VariableContext va, Object val) throws Exception {
        String key = va.variableCategory().Identifier().getText();
        String field = va.property().getText();
        Object o =  variables.get(key);
        PropertyUtils.setProperty(o, field, val);
    }

    @Override
    public Object visitOp(RuleParserParser.OpContext ctx) {
        return ctx.GreaterThen().getText();
    }

    @Override
    public Object visitSingleCondition(RuleParserParser.SingleConditionContext ctx) {
        try {
            Object left = getObj(ctx.conditionLeft().variable());
            Object value = visit(ctx.complexValue());
            return (int) left > (int)value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object visitComplexValue(RuleParserParser.ComplexValueContext ctx) {
        // complexValue (ARITH complexValue)+
        // 差的语法导致visitor的逻辑不好实现
        return super.visitComplexValue(ctx);
    }

    @Override
    public Object visitValue(RuleParserParser.ValueContext ctx) {
        TerminalNode value = ctx.NUMBER();
        if (value != null) {
            return Integer.valueOf(value.getText());
        }
        value = ctx.STRING();
        if (value != null) {
            String ret = value.getText();
            return ret.substring(1, ret.length() - 1);
        }
        value = ctx.Boolean();
        if (value != null) {
            return Boolean.valueOf(value.getText());
        }
        return ctx.getText();
    }

    @Override
    public Object visitRuleDef(RuleParserParser.RuleDefContext ctx) {
        if ((boolean)visit(ctx.left())) {
            return visit(ctx.right());
        } else {
            return visit(ctx.other());
        }
    }
}
