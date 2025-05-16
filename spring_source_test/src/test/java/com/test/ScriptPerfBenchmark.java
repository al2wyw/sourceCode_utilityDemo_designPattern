package com.test;

import com.antlr.SimpleCalculatorCachedVisitor;
import com.antlr.SimpleCalculatorConstantFoldVisitor;
import com.antlr.SimpleCalculatorInterpretVisitor;
import com.dsl.SimpleCalculatorLexer;
import com.dsl.SimpleCalculatorParser;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;



/*
*
Benchmark                           Mode  Cnt       Score        Error   Units
ScriptPerfBenchmark.testCached     thrpt    3    1417.554 ±   3515.793  ops/ms
ScriptPerfBenchmark.testInterpret  thrpt    3    2019.355 ±   1057.434  ops/ms
ScriptPerfBenchmark.testNative     thrpt    3  143113.659 ± 336753.762  ops/ms
*
* */

@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class ScriptPerfBenchmark {

    private SimpleCalculatorCachedVisitor cachedVisitor;

    private SimpleCalculatorInterpretVisitor visitor;

    private ParseTree tree;

    private Map<String, Integer> variables;

    @Setup
    public void init() throws Exception {
        SimpleCalculatorLexer lexer = new SimpleCalculatorLexer(new ANTLRInputStream("1 * 3 + 10 * 100 + 6 * 7 + 10 * 130 + 1 * 3 + 10 * 100 + 6 * 7 + 10 * 130 + 10 * (abc+10)"));
        SimpleCalculatorParser parser = new SimpleCalculatorParser(new CommonTokenStream(lexer));

        tree = parser.cal();
        System.out.printf("parse syntax tree:\n%s\n", tree.toStringTree(parser));

        SimpleCalculatorConstantFoldVisitor folder = new SimpleCalculatorConstantFoldVisitor();
        folder.addVariable("abc", 10);
        int ret = folder.visit(tree);
        System.out.println(ret);

        cachedVisitor = new SimpleCalculatorCachedVisitor(folder.getCache());
        visitor = new SimpleCalculatorInterpretVisitor();
        cachedVisitor.addVariable("abc", 10);
        visitor.addVariable("abc", 10);

        variables = new HashMap<>();
        variables.put("abc", 10);
    }

    @Benchmark
    public int testNative() throws Exception {
        int abc = variables.get("abc");
        return 1 * 3 + 10 * 100 + 6 * 7 + 10 * 130 + 1 * 3 + 10 * 100 + 6 * 7 + 10 * 130 + 10 * (abc+10);
    }

    @Benchmark
    public int testInterpret() throws Exception {
        return visitor.visit(tree);
    }

    @Benchmark
    public int testCached() throws Exception {
        return cachedVisitor.visit(tree);
    }

    public static void main(final String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(ScriptPerfBenchmark.class.getSimpleName()).build();
        new Runner(opt).run();
    }

    public static void profile() throws Exception {
        ScriptPerfBenchmark test = new ScriptPerfBenchmark();
        test.init();
        for (int i = 0; i < 9900000; i++)
            test.testCached();
    }

}
