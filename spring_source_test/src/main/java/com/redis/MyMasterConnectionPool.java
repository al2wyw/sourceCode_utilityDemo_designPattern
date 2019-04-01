package com.redis;

import org.redisson.api.RFuture;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.connection.ClientConnectionsEntry;
import org.redisson.connection.ConnectionManager;
import org.redisson.connection.MasterSlaveEntry;
import org.redisson.connection.pool.MasterConnectionPool;
import org.redisson.misc.RPromise;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/1
 * Time: 15:51
 * Desc:
 */
public class MyMasterConnectionPool extends MasterConnectionPool {

    public MyMasterConnectionPool(MasterSlaveServersConfig config, ConnectionManager connectionManager, MasterSlaveEntry masterSlaveEntry,ClientConnectionsEntry clientConnectionsEntry) {
        super(config, connectionManager, masterSlaveEntry);
        entries.add(clientConnectionsEntry);
    }

    @Override
    public RFuture<Void> add(ClientConnectionsEntry entry) {
        entries.clear();
        entries.add(entry);
        RFuture<Void> future = super.add(entry);
        future.onComplete((t, e) -> {
            System.out.println("entries size " + entries.size());
            if(entries.size() > 1) {
                entries.remove(entry);
            }
        });
        return future;
    }
}
