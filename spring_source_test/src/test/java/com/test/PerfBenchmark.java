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
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class PerfBenchmark {

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
    public void testNormal() throws Exception {
        InstructionsTest.main(ARGS);
    }

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
        Options opt = new OptionsBuilder().include(PerfBenchmark.class.getSimpleName()).build();
        new Runner(opt).run();
    }

}
