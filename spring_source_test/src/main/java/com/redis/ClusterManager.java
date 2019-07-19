package com.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.cluster.ClusterConnectionManager;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.TransportMode;
import org.redisson.connection.ClientConnectionsEntry;
import org.redisson.connection.MasterSlaveConnectionManager;
import org.redisson.connection.MasterSlaveEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/18
 * Time: 18:10
 * Desc:
 */
@Component
public class ClusterManager {

    @Value("${redis.cluster.address1}")
    private String address1;

    @Value("${redis.cluster.address2}")
    private String address2;

    @Value("${redis.cluster.address3}")
    private String address3;

    @Value("${redis.pool.size}")
    private int poolSize;

    private RedissonClient redissonClient;

    private static final Field COMMAND_EXECUTOR_FIELD = ReflectionUtils.findField(MasterSlaveConnectionManager.class, "commandExecutor");

    private static final Field WRITE_CONNECTION_POOL_FIELD = ReflectionUtils.findField(MasterSlaveEntry.class, "writeConnectionPool");

    private static final Field CLIENT_CONNECTIONS_ENTRY_FIELD = ReflectionUtils.findField(MasterSlaveEntry.class, "masterEntry");

    public RedissonClient getRedissonClient() {
        if(redissonClient == null) {
            Config config = buildConfig(null);
            this.redissonClient = Redisson.create(config);

            Redisson redisson = (Redisson) this.redissonClient;
            ClusterConnectionManager manager = (ClusterConnectionManager)redisson.getConnectionManager();

            ClusterDownRetryService exe = new ClusterDownRetryService(manager);
            if(!COMMAND_EXECUTOR_FIELD.isAccessible()){
                COMMAND_EXECUTOR_FIELD.setAccessible(true);
            }
            ReflectionUtils.setField(COMMAND_EXECUTOR_FIELD, manager, exe);

            /*MasterSlaveEntry entry = manager.getEntry(0);
            if(!CLIENT_CONNECTIONS_ENTRY_FIELD.isAccessible()){
                CLIENT_CONNECTIONS_ENTRY_FIELD.setAccessible(true);
            }
            ClientConnectionsEntry clientConnectionsEntry = (ClientConnectionsEntry)ReflectionUtils.getField(CLIENT_CONNECTIONS_ENTRY_FIELD, entry);

            MyMasterConnectionPool pool = new MyMasterConnectionPool(manager.getConfig(), manager, entry, clientConnectionsEntry);
            if(!WRITE_CONNECTION_POOL_FIELD.isAccessible()){
                WRITE_CONNECTION_POOL_FIELD.setAccessible(true);
            }
            ReflectionUtils.setField(WRITE_CONNECTION_POOL_FIELD, entry, pool);*/
        }
        return redissonClient;
    }

    public void rehash(String address){//no need to rehash by hand
        RedissonClient old = redissonClient;
        Config config = buildConfig(address);
        this.redissonClient = Redisson.create(config);
        old.shutdown();
    }

    private Config buildConfig(String address){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        clusterServersConfig.setScanInterval(1000) //do not interfere with capture data
                .setReadMode(ReadMode.MASTER)
                .setMasterConnectionPoolSize(poolSize)
                .setMasterConnectionMinimumIdleSize(poolSize)
                .setSlaveConnectionMinimumIdleSize(poolSize)
                .setSlaveConnectionPoolSize(poolSize)
                .addNodeAddress(address1)
                .addNodeAddress(address2)
                .addNodeAddress(address3);

        if(address != null) {
            clusterServersConfig.addNodeAddress(address);
        }
        return config;
    }
}
