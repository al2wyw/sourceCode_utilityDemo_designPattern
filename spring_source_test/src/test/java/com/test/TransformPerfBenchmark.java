package com.test;

import com.cglib.ByteCodeMaxLoopAnalysis;
import com.dynamicInvoke.InstructionsTest;
import com.dynamicInvoke.InstructionsTestG;
import com.utils.ClassLoaderUtils;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/*
在使用 JMH 的过程中，一定要避免一些陷阱，比如 JIT 优化中的死码消除，这就会影响测试结果。
JMH 提供了两种方式避免这种问题，一种是将这个变量作为方法返回值 return a，一种是通过 Blackhole 的 consume 来避免 JIT 的优化消除。
如果你的测试输入是可预测的(常量参数)，那么很可能会被JIT给优化掉。
其他陷阱还有常量折叠与常量传播、永远不要在测试中写循环、使用 Fork 隔离多个测试方法、方法内联、伪共享与缓存行、分支预测、多线程测试等，可以阅读 github上的JMH-samples了解全部的陷阱

Benchmark                                    Mode  Cnt         Score         Error   Units
TransformPerfBenchmark.baseline             thrpt    3  14361974.773 ± 1347552.652  ops/ms
TransformPerfBenchmark.testNormal           thrpt    3    360675.610 ±    8980.718  ops/ms
TransformPerfBenchmark.testNormalInvoke     thrpt    3    305413.880 ± 1072445.133  ops/ms
TransformPerfBenchmark.testTransformInvoke  thrpt    3    354861.241 ±   21907.134  ops/ms
* */
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class TransformPerfBenchmark {

    private final static String[]ARGS = {"1", "1", "3", "4"};

    private Method instructionsTest;

    private Method instructionsTestG;

    @Setup
    public void init() throws Exception {
        instructionsTest = InstructionsTest.class.getDeclaredMethod("main", String[].class);

        byte[] newContent = ByteCodeMaxLoopAnalysis.transform("com/dynamicInvoke/InstructionsTest.class",
                "com.dynamicInvoke.InstructionsTestG");
        instructionsTestG = ClassLoaderUtils.defineClass(InstructionsTest.class.getClassLoader(), "com.dynamicInvoke.InstructionsTestG", newContent).getDeclaredMethod("main", String[].class);
    }

    @Benchmark
    public void baseline() throws Exception {

    }

    @Benchmark
    public void testNormal() throws Exception {
        InstructionsTest.main(ARGS);
    }

    //这个方法是配合PerfBenchmarkClassReplaced进行测试的，跟当前PerfBenchmark测试无关
    public void testTransform() throws Exception {
        InstructionsTestG.main(ARGS);
    }

    @Benchmark
    public void testNormalInvoke() throws Exception {
        instructionsTest.invoke(null, (Object) ARGS);
    }

    @Benchmark
    public void testTransformInvoke() throws Exception {
        instructionsTestG.invoke(null, (Object) ARGS);
    }


    public static void main(final String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(TransformPerfBenchmark.class.getSimpleName())
                //.addProfiler(StackProfiler.class)
                .build();
        new Runner(opt).run();
    }

}
