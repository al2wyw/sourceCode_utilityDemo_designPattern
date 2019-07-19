package com.zk;

import com.utils.LoggerUtils;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/18
 * Time: 10:43
 * Desc:
 */
@RequestMapping("zk")
@Controller
public class ZkController {

    private static final String ROOT_PATH = "/root/";

    private static final String PROVIDER_PATH = "/provider";

    //node path format: /xx/xx/xx

    @Autowired
    private ZookeeperManager zookeeperManager;

    @Autowired
    private ThreadPoolExecutor taskPool;

    @RequestMapping(value="add", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String addProKey(@RequestParam("key") String key, @RequestParam("value") String value){
        String path = ROOT_PATH + key + PROVIDER_PATH;

        zookeeperManager.createNode(path, value);
        zookeeperManager.registerWatcherNodeChanged(path, new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                byte[] data = zookeeperManager.getNode(path);
                String value = null;
                if(data != null){
                    value = new String(data);
                }
                LoggerUtils.getLogger().info(path + " changed to new value: "+ value);
            }
        });
        return "ok";
    }

    @RequestMapping(value="update", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updateKey(@RequestParam("key") String key,@RequestParam("value") String value){
        zookeeperManager.updateNode(ROOT_PATH + key + PROVIDER_PATH, value);
        return "ok";
    }

    @RequestMapping(value="delete", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteNode(@RequestParam("key") String key){
        zookeeperManager.deleteNode(ROOT_PATH + key + PROVIDER_PATH);
        return "ok";
    }

    @RequestMapping(value="mutex", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String mutex(@RequestParam("key") String key){
        InterProcessMutex mutex = new InterProcessMutex(zookeeperManager.getZookeeperClient(),key);
        try {
            mutex.acquire();
            LoggerUtils.getLogger().info("get lock success");
            Thread.sleep(10000);
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
        }finally {
            try {
                mutex.release();
            }catch (Exception e){
                LoggerUtils.getLogger().error("",e);
            }
        }
        return "ok";
    }
}
