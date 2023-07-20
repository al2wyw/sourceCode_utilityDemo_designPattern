package com.test;

import com.dynamicInvoke.InstructionsTest;
import com.dynamicInvoke.InstructionsTestG;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
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

    @Benchmark
    public void testNormal() throws Exception {
        InstructionsTest.main(ARGS);
    }

    @Benchmark
    public void testTransform() throws Exception {
        InstructionsTestG.main(ARGS);
    }

    public static void main(final String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(PerfBenchmark.class.getSimpleName()).build();
        new Runner(opt).run();
    }

}
