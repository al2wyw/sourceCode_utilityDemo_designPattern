package demo;

/**
 * Created by johnny.ly on 2016/6/26.
 */
public class testCacheBuilder {
    //Cache -> LoadingCache -> LocalLoadingCache
    //Cache -> LocalManualCache -> LocalLoadingCache
    //cache builder的两种build方法分别产生LocalManualCache和LocalLoadingCache
    //Cache的所有方法委托于LocalCache类(并不继承于Cache)
    //cache的加载依赖于CacheLoader
    public static void main(String[] args) throws Exception {

    }
}
