package com.redis;

import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.utils.DateUtils;
import freemarker.template.utility.DateUtil;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/16
 * Time: 16:44
 * Desc: hash tags to force {user001}.xxxx and {user001}.fff to store in the same slot
 */
@Controller
@RequestMapping("/cluster")
public class RedisClusterController {

    @Autowired
    private ClusterManager clusterManager;

    @Autowired
    private ThreadPoolExecutor taskPool;

    private boolean flag = true;

    @RequestMapping(value="redis/get", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object get(@RequestParam("key") String key) {
        RBucket bucket = clusterManager.getRedissonClient().getBucket(key);
        return bucket.get();
    }

    @RequestMapping(value="redis/set", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object set(@RequestParam("key") String key,@RequestParam("value") String value) {
        RBucket<Object> bucket = clusterManager.getRedissonClient().getBucket(key);
        bucket.set(value);
        return true;
    }

    @RequestMapping(value="redis/mget", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object mget(@RequestParam("key") String key) {
        List<String> keys = Splitter.on(",").omitEmptyStrings().splitToList(key);
        RBuckets buckets = clusterManager.getRedissonClient().getBuckets();
        return buckets.get(keys.toArray(new String[0]));
    }

    @RequestMapping(value="redis/mset", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object mset(@RequestParam("object") String object) {
        Map<String,String> keyValue = Splitter.on(",").omitEmptyStrings().withKeyValueSeparator("|").split(object);
        RBuckets buckets = clusterManager.getRedissonClient().getBuckets();
        buckets.set(keyValue);
        return "ok";
    }

    @RequestMapping(value="redis/rehash", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object rehash(@RequestParam("address") String address) {
        clusterManager.rehash(address);
        return "ok";
    }

    @RequestMapping(value="redis/run", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object run(@RequestParam("key") String key, @RequestParam("sleep") int sleep) {
        taskPool.submit(() -> {
            flag = true;
            while (flag) {
                try {
                    RBucket bucket = clusterManager.getRedissonClient().getBucket(key);
                    System.out.println(DateUtils.format(new Date(),DateUtils.DATE_TIME_MS) + " " + bucket.get());
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    System.out.println(DateUtils.format(new Date(), DateUtils.DATE_TIME_MS));
                    e.printStackTrace();
                }
            }
            System.out.println("run terminal");
        });
        return "ok";
    }

    @RequestMapping(value="redis/run2", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object run2(@RequestParam("key") String key, @RequestParam("sleep") int sleep) {
        taskPool.submit(() -> {
            flag = true;
            while (flag) {
                try {
                    RBucket bucket = clusterManager.getRedissonClient().getBucket(key);
                    Long value = System.currentTimeMillis();
                    bucket.set(value.toString());
                    System.out.println(DateUtils.format(new Date(),DateUtils.DATE_TIME_MS) + " " + value);
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    System.out.println(DateUtils.format(new Date(), DateUtils.DATE_TIME_MS));
                    e.printStackTrace();
                }
            }
            System.out.println("run terminal");
        });
        return "ok";
    }

    //getAndSet & set 并发效率, 差不多的效果, 并发受连接池大小影响较大
    //挂掉的node 停服时间太长 ??? 由于建立连接池后才更改引用
    //slot2entry 全部为null ???

    @RequestMapping(value="redis/test", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object conflict(@RequestParam("count") int count) {
        for(int i = 0; i < count; i ++) {
            final int value = i;
            taskPool.submit(() -> {
                RBucket<String> bucket = clusterManager.getRedissonClient().getBucket("conflict");
                Stopwatch stopwatch =  Stopwatch.createStarted();
                bucket.set("test" + value);
                stopwatch.stop();
                System.out.println(Thread.currentThread().getName() + " value " + " time " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
            });
        }
        return "ok";
    }

    @RequestMapping(value="redis/test2", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object conflict2(@RequestParam("count") int count) {
        for(int i = 0; i < count; i ++) {
            final int value = i;
            taskPool.submit(() -> {
                RBucket<String> bucket = clusterManager.getRedissonClient().getBucket("conflict");
                Stopwatch stopwatch =  Stopwatch.createStarted();
                String old = bucket.getAndSet("test" + value);
                stopwatch.stop();
                System.out.println(Thread.currentThread().getName() + " value " + old + " time " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
            });
        }
        return "ok";
    }

    @RequestMapping(value="redis/stop", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object stop() {
        flag = false;
        return "ok";
    }


    @RequestMapping(value="redis/hset", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object hset(@RequestParam("key") String key, @RequestParam("size") int size) {
        RMap<String, Integer> keyMap = clusterManager.getRedissonClient().getMap(key);
        for(int i = 0; i < size; i++){
            keyMap.put("test" + i, i);
        }
        return "ok";
    }

    @RequestMapping(value="redis/hset2", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object hset2(@RequestParam("key") String key, @RequestParam("size") int size) {
        RMap<String, Integer> keyMap = clusterManager.getRedissonClient().getMap(key);
        Map<String,Integer> map = Maps.newHashMapWithExpectedSize(size);
        for(int i = 0; i < size; i++){
            map.put("test" + i, i);
        }
        keyMap.putAll(map);
        return "ok";
    }
}
