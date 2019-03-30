package com.redis;

import org.redisson.client.RedisException;
import org.redisson.command.AsyncDetails;
import org.redisson.command.CommandSyncService;
import org.redisson.connection.ConnectionManager;
import org.redisson.misc.RPromise;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/29
 * Time: 14:55
 * Desc:
 */
public class ClusterDownRetryService extends CommandSyncService {

    private static final String CLUSTER_DOWN = "CLUSTERDOWN";

    public ClusterDownRetryService(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected <V, R> void handleError(AsyncDetails<V, R> details, RPromise<R> mainPromise, Throwable cause) {
        if(cause instanceof RedisException){
            RedisException ex = (RedisException) cause;
            String mess = ex.getMessage();
            if(mess.contains(CLUSTER_DOWN) && details.getAttempt() < getConnectionManager().getConfig().getRetryAttempts()){
                System.out.println("cluster down");
                try {
                    Thread.sleep(1000);
                }catch (Exception e){

                }

                System.out.println("retry the cluster down " + details.getAttempt());
                async(details.isReadOnlyMode(), details.getSource(), details.getCodec(),
                        details.getCommand(), details.getParams(), details.getMainPromise(), details.getAttempt() + 1, false);
                AsyncDetails.release(details);
                return;
            }
        }
        super.handleError(details, mainPromise, cause);
    }
}
