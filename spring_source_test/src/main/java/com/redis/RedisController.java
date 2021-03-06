package com.redis;

import com.componentScan.MyConditional;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/13
 * Time: 20:49
 * Desc:
 */
@Controller
@MyConditional(trigger="redis")
public class RedisController {

    @Autowired
    @Qualifier("redissonClient")
    private RedissonClient redissonClient;

    @Autowired
    private ThreadPoolExecutor taskPool;

    @RequestMapping(value="redis/list/get", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getList(@RequestParam("key") String key) {
        RList<Object> list = redissonClient.getList(key);
        if(CollectionUtils.isEmpty(list)){
            return "EMPTY";
        }
        return list.stream().map(Objects::toString).collect(Collectors.joining("-"));
    }

    @RequestMapping(value="redis/list/set", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object setList(@RequestParam("key") String key,@RequestParam("value") String value) {
        RList<Object> list = redissonClient.getList(key);
        List<String> values = Stream.of(value)
                .map(value1 -> Splitter.on(",").omitEmptyStrings().split(value1))
                .map(Lists::newArrayList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        list.addAll(values);
        return true;
    }

    @RequestMapping(value="redis/get", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object get(@RequestParam("key") String key) {
        RBucket bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @RequestMapping(value="redis/set", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object set(@RequestParam("key") String key,@RequestParam("value") String value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);
        return true;
    }

    @RequestMapping(value="redis/incr", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object incr(@RequestParam("key") String key,@RequestParam("value") long value) {
        RAtomicLong  bucket = redissonClient.getAtomicLong(key);
        long old = bucket.get();
        return bucket.compareAndSet(old, old + value);
    }

    @RequestMapping(value="redis/mget", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object mget(@RequestParam("key") String key) {
        List<String> keys = Splitter.on(",").omitEmptyStrings().splitToList(key);
        RBuckets buckets = redissonClient.getBuckets();
        return buckets.get(keys.toArray(new String[0]));
    }

    @RequestMapping(value="redis/mset", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object mset(@RequestParam("object") String object) {
        Map<String,String> keyValue = Splitter.on(",").omitEmptyStrings().withKeyValueSeparator("|").split(object);
        RBuckets buckets = redissonClient.getBuckets();
        buckets.set(keyValue);
        return "ok";
    }

    @RequestMapping(value="redis/test", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object conflict(@RequestParam("count") int count) {
        for(int i = 0; i < count; i ++) {
            taskPool.submit(() -> {
                RAtomicLong  bucket = redissonClient.getAtomicLong("conflict");
                Stopwatch stopwatch =  Stopwatch.createStarted();
                long old = bucket.getAndAdd(1);
                stopwatch.stop();
                System.out.println(Thread.currentThread().getName() + " value " + old + " time " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
            });
        }
        return "ok";
    }

    @RequestMapping(value="redis/load", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object load(@RequestParam("count") int count) {
        RList<Object> list = redissonClient.getList("inventory");
        List<Long> values = Lists.newArrayListWithCapacity(count);
        long base = System.nanoTime();
        for(int i = 0; i < count; i ++) {
            values.add(base + i);
        }
        list.addAll(values);
        return "ok";
    }

    @RequestMapping(value="redis/unload", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object unload(@RequestParam("count") int count) {
        for(int i = 0; i < count; i ++) {
            taskPool.submit(() -> {
                RList<Object> list = redissonClient.getList("inventory");
                Stopwatch stopwatch = Stopwatch.createStarted();
                Object old = list.remove(0);//1500ms delay
                stopwatch.stop();
                System.out.println(Thread.currentThread().getName() + " value " + old + " time " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
            });
        }
        return "ok";
    }
}
