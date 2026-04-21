package com.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.DTraceAsmProfiler;
import org.openjdk.jmh.profile.LinuxPerfAsmProfiler;
import org.openjdk.jmh.profile.LinuxPerfNormProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/*
linux x86_64的测试结果:
Benchmark                                 Mode  Cnt        Score   Error   Units
VolatilePerfBenchmark.baseline           thrpt       2728982.687          ops/ms
VolatilePerfBenchmark.testAtomicGet      thrpt        301024.312          ops/ms
VolatilePerfBenchmark.testAtomicSet      thrpt        110253.050          ops/ms
VolatilePerfBenchmark.testAtomicSetLazy  thrpt       1342074.026          ops/ms
VolatilePerfBenchmark.testNormalGet      thrpt        363300.966          ops/ms
VolatilePerfBenchmark.testNormalSet      thrpt       2142444.611          ops/ms
VolatilePerfBenchmark.testVolatileGet    thrpt        361102.161          ops/ms
VolatilePerfBenchmark.testVolatileSet    thrpt        110419.432          ops/ms

mac arm64的测试结果:
Benchmark                                 Mode  Cnt        Score         Error   Units
VolatilePerfBenchmark.baseline           thrpt       3847346.497          ops/ms
VolatilePerfBenchmark.testAtomicGet      thrpt        594446.270          ops/ms
VolatilePerfBenchmark.testAtomicSet      thrpt       2925457.861          ops/ms
VolatilePerfBenchmark.testAtomicSetLazy  thrpt       1895931.192          ops/ms
VolatilePerfBenchmark.testNormalGet      thrpt        928968.673          ops/ms
VolatilePerfBenchmark.testNormalSet      thrpt       3773354.571          ops/ms
VolatilePerfBenchmark.testVolatileGet    thrpt        840582.472          ops/ms
VolatilePerfBenchmark.testVolatileSet    thrpt       3487641.725          ops/ms
感觉还是测不出来，测试代码的payload远远超过了这种简单的读写指令代码 ???
* */
@Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class VolatilePerfBenchmark {

    private static AtomicInteger int1 = new AtomicInteger(1);
    private static AtomicInteger int2 = new AtomicInteger(1);

    private static volatile boolean test1 = false;

    private static boolean test2 = false;

    @Benchmark
    public void baseline() throws Exception {

    }

    @Benchmark
    public boolean testVolatileGet() throws Exception {
        return test1;
    }

    @Benchmark
    public boolean testNormalGet() throws Exception {
        return test2;
    }

    @Benchmark
    public int testAtomicGet() throws Exception {
        return int1.get();
    }

    @Benchmark
    public void testVolatileSet() throws Exception {
        test1 = true;
    }

    @Benchmark
    public void testAtomicSet() throws Exception {
        int1.set(2);
    }

    @Benchmark
    public void testAtomicSetLazy() throws Exception {
        int2.lazySet(3);
    }

    @Benchmark
    public void testNormalSet() throws Exception {
        test2 = true;
    }


    public static void main(final String[] args) throws Exception {
        // 容器或云虚拟机可能关掉PMU，无法使用perf采集硬件事件，参考perf.txt
        Options opt = new OptionsBuilder().include(VolatilePerfBenchmark.class.getSimpleName())
                //.addProfiler(LinuxPerfAsmProfiler.class, "hotThreshold=0.01;event=cpu-clock")
                //.addProfiler(LinuxPerfNormProfiler.class, "events=cpu-clock,task-clock,context-switches,page-faults")
                .result(VolatilePerfBenchmark.class.getSimpleName() + ".json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }

}
