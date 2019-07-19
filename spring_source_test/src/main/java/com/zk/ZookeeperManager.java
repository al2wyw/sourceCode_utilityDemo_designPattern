package com.zk;

import com.google.common.collect.Maps;
import com.utils.LoggerUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/17
 * Time: 20:49
 * Desc:
 */
@Component
public class ZookeeperManager {

    @Value("${zk.address}")
    private String address;

    private CuratorFramework zookeeperClient;

    private Map<String,NodeCache> map = Maps.newHashMap();

    private Map<String,PathChildrenCache> pathMap = Maps.newHashMap();

    public CuratorFramework getZookeeperClient() {
        return zookeeperClient;
    }

    public byte[] getNode(String path){
        try {
            if(isNodeExist(path)) {
                return zookeeperClient.getData().forPath(path);
            }
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
        }
        return null;
    }

    public List<String> getChildren(String path){
        try {
            if(isNodeExist(path)) {
                return zookeeperClient.getChildren().forPath(path);
            }
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
        }
        return null;
    }

    public boolean isNodeExist(String path){
        try {
            Stat stat = zookeeperClient.checkExists().forPath(path);
            return stat != null;
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return false;
        }
    }

    public boolean deleteNode(String path){
        try {
            if(isNodeExist(path)) {
                zookeeperClient.delete().deletingChildrenIfNeeded().forPath(path);
            }else{
                LoggerUtils.getLogger().info(path + " does not exist");
            }
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return false;
        }
        return true;
    }

    public String createNode(String path, String value){
        try {
            if(isNodeExist(path)) {
                LoggerUtils.getLogger().info(path + " exists");
                return path;
            }
            return zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, value.getBytes());
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return null;
        }
    }

    public String createSeqNode(String path, String value){
        try {
            if(isNodeExist(path)) {
                LoggerUtils.getLogger().info(path + " exists");
                return path;
            }
            return zookeeperClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, value.getBytes());
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return null;
        }
    }

    public String createTempNode(String path, String value){
        try {
            if(isNodeExist(path)) {
                LoggerUtils.getLogger().info(path + " exists");
                return path;
            }
            return zookeeperClient.create().withTtl(3000).creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, value.getBytes());
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return null;
        }
    }

    public boolean updateNode(String path,String value){
        try {
            if(isNodeExist(path)) {
                Stat stat = zookeeperClient.checkExists().forPath(path);
                int ver = stat.getVersion();
                zookeeperClient.setData().withVersion(ver).forPath(path, value.getBytes());
            }else{
                LoggerUtils.getLogger().info(path + " does not exist");
            }
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return false;
        }
        return true;
    }

    public boolean registerWatcherNodeChanged(String path, NodeCacheListener nodeCacheListener) {
        try {
            if(map.containsKey(path)){
                map.get(path).getListenable().addListener(nodeCacheListener);
                return true;
            }
            NodeCache nodeCache = new NodeCache(zookeeperClient, path, false);
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start();
            map.put(path,nodeCache);
        } catch (Exception e) {
            LoggerUtils.getLogger().error("", e);
            return false;
        }
        return true;
    }

    public boolean registerWatcherNodeChanged(String path, PathChildrenCacheListener nodeCacheListener) {
        try {
            if(pathMap.containsKey(path)){
                pathMap.get(path).getListenable().addListener(nodeCacheListener);
                return true;
            }
            PathChildrenCache nodeCache = new PathChildrenCache(zookeeperClient, path, false);
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            pathMap.put(path,nodeCache);
        } catch (Exception e) {
            LoggerUtils.getLogger().error("", e);
            return false;
        }
        return true;
    }

    @PostConstruct
    public void init(){
        zookeeperClient = CuratorFrameworkFactory.newClient(address, 30 * 1000, 5 * 1000, new RetryNTimes(1, 1000));

        zookeeperClient.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                if (newState == ConnectionState.RECONNECTED) {
                    LoggerUtils.getLogger().info(client + " recon");
                }
                if(newState == ConnectionState.CONNECTED) {
                    LoggerUtils.getLogger().info(client + " con");
                }
            }
        });
        zookeeperClient.start();
        LoggerUtils.getLogger().info("zk start");
    }
}
