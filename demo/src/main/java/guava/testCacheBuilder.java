package guava;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by johnny.ly on 2016/6/26.
 */
public class testCacheBuilder {
    //Cache -> LoadingCache -> LocalLoadingCache
    //Cache -> LocalManualCache -> LocalLoadingCache
    //cache builder的两种build方法分别产生LocalManualCache和LocalLoadingCache
    //Cache的所有方法委托于LocalCache类(并不继承于Cache)
    //cache的加载依赖于CacheLoader
    //expireAfterWrite + refreshAfterWrite 保证cache可以自动更新，同时避免长时间未更新获取到太旧的值(refresh需要get操作来激发)
    //Cache 使用 LRU evict, getNextEvictable -> accessQueue(get和put都算access)
    public static void main(String[] args) throws Exception {
        LoadingCache<String,Object> cache = CacheBuilder.newBuilder()
                .initialCapacity(8)
                .concurrencyLevel(8)
                .expireAfterWrite(1000, TimeUnit.MILLISECONDS)//线程阻塞，只有一个线程去load
                .refreshAfterWrite(500, TimeUnit.MILLISECONDS)//线程不阻塞，只有一个线程去load，其他线程直接返回
                .maximumSize(10)
                .recordStats()
                .removalListener(new RemovalListener<String, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, Object> notification) {
                        System.out.println("remove from cache: " + notification.getKey() + " " + notification.getValue());
                    }
                })
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        System.out.println("load from outside: " + key);
                        return key;
                    }
                });
        for(int i = 0; i < 20; i++) {
            cache.put("test"+i,i);
            cache.getIfPresent("test3");//make it fresh
        }
        System.out.println(cache.getIfPresent("test12"));
        Thread.sleep(1100);
        System.out.println(cache.getIfPresent("test12"));
        CacheStats cacheStats = cache.stats();
        cache.get("test19");// LocalCache.Segment.get -> LocalCache.Segment.scheduleRefresh(需要get来refresh !!!)
                            //                        -> LocalCache.Segment.lockedGetOrLoad

        Cache<String,Object> cache1 = CacheBuilder.newBuilder()
                .maximumSize(10)
                .build();
        for(int i = 0; i < 20; i++) {
            cache1.put("test"+i,i);
        }
    }
}
