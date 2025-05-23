package com.test;

import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.internal.InternalThreadLocalMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * Benchmark                             Mode  Cnt       Score       Error   Units
 * MapPerfBenchmark.testAttributeMap    thrpt    3        2.290 ±      0.165  ops/ms
 * MapPerfBenchmark.testNormalMap       thrpt    3      892.471 ±     51.090  ops/ms
 * MapPerfBenchmark.testThreadLocalMap  thrpt    3   919379.694 ±  32231.921  ops/ms
 *
 * with sink method:
 * Benchmark                             Mode  Cnt    Score     Error   Units
 * MapPerfBenchmark.testAttributeMap    thrpt    3    2.468 ±   1.049  ops/ms
 * MapPerfBenchmark.testNormalMap       thrpt    3  515.547 ±  57.960  ops/ms
 * MapPerfBenchmark.testThreadLocalMap  thrpt    3  705.655 ± 164.675  ops/ms
 *
 * with sink method and OperationsPerInvocation(放大分数):
 * Benchmark                             Mode  Cnt       Score        Error   Units
 * MapPerfBenchmark.testAttributeMap    thrpt    3    2521.143 ±     39.425  ops/ms
 * MapPerfBenchmark.testNormalMap       thrpt    3  518281.661 ±  11129.662  ops/ms
 * MapPerfBenchmark.testThreadLocalMap  thrpt    3  682836.030 ± 211749.701  ops/ms
 * */
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class MapPerfBenchmark {

    private Map<String, String> map;

    private DefaultAttributeMap arrayMap;

    private AttributeKey<String> key;

    private InternalThreadLocalMap localMap;

    private static final int LOOP = 1000;

    @Setup
    public void init() throws Exception {
        if (map == null) {
            map = new HashMap<>();
            for (int i = 0; i < LOOP; i++) {
                String val = "com/test" + i;
                map.put(val, val);
            }
            System.out.println("init map");
        }
        if (arrayMap == null) {
            arrayMap = new DefaultAttributeMap();
            for (int i = 0; i < LOOP; i++) {
                String val = "com/test" + i;
                key = AttributeKey.valueOf(val);
                arrayMap.attr(key).set(val);
            }
            System.out.println("init arrayMap");
        }
        if (localMap == null) {
            localMap = InternalThreadLocalMap.get();
            for (int i = 0; i < LOOP; i++) {
                String val = "com/test" + i;
                localMap.setIndexedVariable(i, val);
            }
            System.out.println("init localMap");
        }
    }


    @Benchmark
    @OperationsPerInvocation(LOOP)
    public void testNormalMap() throws Exception {
        for (int i = 0; i < LOOP; i++) {
            String vale = map.get("test999");
            sink(vale);
        }
    }

    @Benchmark
    @OperationsPerInvocation(LOOP)
    public void testAttributeMap() throws Exception {
        for (int i = 0; i < LOOP; i++) {
            String vale = arrayMap.attr(key).get();
            sink(vale);
        }
    }

    @Benchmark
    @OperationsPerInvocation(LOOP)
    public void testThreadLocalMap() throws Exception {
        for (int i = 0; i < LOOP; i++) {
            Object vale = localMap.indexedVariable(i);
            sink(vale);
        }
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private void sink(Object o) {

    }


    public static void main(final String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(MapPerfBenchmark.class.getSimpleName()).build();
        new Runner(opt).run();
    }

}
