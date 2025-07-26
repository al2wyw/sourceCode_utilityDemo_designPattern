package com.test;

import static org.openjdk.jmh.annotations.CompilerControl.Mode.DONT_INLINE;

import com.script.Model;
import java.io.InputStream;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
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
Benchmark                          Mode  Cnt        Score   Error   Units
ScriptPerfBenchmark.testBaseline  thrpt       3708048.232          ops/ms
ScriptPerfBenchmark.testEval      thrpt           773.249          ops/ms
ScriptPerfBenchmark.testPandora   thrpt        597942.897          ops/ms

Benchmark                          Mode  Cnt        Score   Error   Units
ScriptPerfBenchmark.testBaseline  thrpt       3947746.939          ops/ms
ScriptPerfBenchmark.testEval      thrpt           789.621          ops/ms
ScriptPerfBenchmark.testNative    thrpt        541306.344          ops/ms
ScriptPerfBenchmark.testPandora   thrpt        478255.565          ops/ms

Benchmark                          Mode  Cnt        Score   Error   Units
ScriptPerfBenchmark.testBaseline  thrpt       3954801.951          ops/ms
ScriptPerfBenchmark.testEval      thrpt           159.004          ops/ms
ScriptPerfBenchmark.testNative    thrpt        211442.518          ops/ms
ScriptPerfBenchmark.testPandora   thrpt        168746.103          ops/ms
* */
@Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class ScriptBenchmark {
    private ParseTree tree;

    private EvalTemplate template;

    private EvalVisitor visitor;

    @Setup
    public void init() throws Exception {
        initEval(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/benchmark.script"));
        initPandora(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/benchmark.script"));
    }

    public void initEval(InputStream stream) throws Exception {
        TLLexer lexer = new TLLexer(CharStreams.fromStream(stream));
        TLParser parser = new TLParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);
        tree = parser.parse();

        tl.antlr4.Scope scope = new tl.antlr4.Scope();
        Map<String, Function> functions = Collections.emptyMap();
        visitor = new EvalVisitor(scope, functions);
    }

    public void initPandora(InputStream stream) throws Exception {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile(stream, new CompileContext(Model.class));

        GenerateContext ast = new GenerateContext(Model.class);
        // GenerateContext.enableDebug();
        // GenerateContext.enableOutPutClassFile(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        node.generateCode(ast);

        template = ast.getTemplateClass().newInstance();
    }

    @Benchmark
    public void testBaseline() {

    }

    @Benchmark
    public void testEval() {
        visitor.visit(tree);
    }

    @Benchmark
    @CompilerControl(DONT_INLINE)
    public Object testNative() {
        Model model = new Model();
        return invoke(model);
    }

    public Object invoke(Object o) {
        Model model = (Model) o;
        model.int1 = 666;
        model.int2 = 879;
        model.float1 = 12.34f;
        model.float2 = 13.44f;
        model.name1 = "my peter";
        model.flag1 = model.int1 >= model.int1;
        model.flag2 = model.float1 > model.int2;
        model.double1 = (model.float1 + model.float2) * model.int1;
        model.float3 = (model.float1 + model.float2) * model.int1;
        model.int1 = model.int1 * (model.int1 + model.int2);

        if(model.int1 < 100 || model.flag1)
            model.int3 = 100;
        else if (model.int1 > 800)
            model.int3 = 10;
        else if (model.int1 > 600)
            model.int3 = 1000;
        else
            model.int3 = 1;

        model.int2 = 0;
        while(model.int2 < 10)
            model.int2 = model.int2 + 1;

        return o;
    }

    @Benchmark
    @CompilerControl(DONT_INLINE)
    public Object testPandora() {
        Model model = new Model();
        return template.eval(model);
    }

    public static void main(final String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(ScriptPerfBenchmark.class.getSimpleName())
                //.addProfiler(HotspotMemoryProfiler.class)
                //.addProfiler(GCProfiler.class)
                .build();
        new Runner(opt).run();

        /*ScriptPerfBenchmark test = new ScriptPerfBenchmark();
        test.initPandora(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.tl"));

        for (int i = 0; i < 1000000000; i++) {
            test.testNative();
            test.testPandora();
        }*/
    }
}
