package com.test;

import com.antlr.MyRuleParser;
import com.dsl.RuleParserLexer;
import com.dsl.RuleParserParser;
import jodd.io.FileUtil;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

public class RuleParserTest {

    @Test
    public void Test() throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("rule.txt").getPath();
        String script = FileUtil.readString(path, "utf-8");

        ANTLRInputStream antlrInputStream = new ANTLRInputStream(script);
        RuleParserLexer lexer = new RuleParserLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        RuleParserParser parser = new RuleParserParser(tokenStream);

        MyRuleParser visitor = new MyRuleParser();
        MyData data = new MyData();
        data.age = 19;
        data.name = "test";
        visitor.addVariable("test", data);
        visitor.visitRuleSet(parser.ruleSet());
        System.out.println(data);
    }

    public static class MyData {
        private String name;
        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MyData{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
