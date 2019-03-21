package com.redis;

import com.google.common.base.Splitter;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/16
 * Time: 16:44
 * Desc:
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
        taskPool.submit(() ->  {
            flag = true;
            while (flag){
                try{
                    RBucket bucket = clusterManager.getRedissonClient().getBucket(key);
                    System.out.println(DateFormat.getDateTimeInstance().format(new Date())  + " " + bucket.get());
                    Thread.sleep(sleep);
                }catch (Exception e){
                    System.out.println(DateFormat.getDateTimeInstance().format(new Date()));
                    e.printStackTrace();
                }
            }
            System.out.println("run terminal");
        });
        return "ok";
    }

    @RequestMapping(value="redis/stop", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object stop() {
        flag = false;
        return "ok";
    }
}
