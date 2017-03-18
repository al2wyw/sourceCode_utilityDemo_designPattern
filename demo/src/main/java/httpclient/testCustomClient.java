package httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/18
 * Time: 9:38
 * Desc:
 */
public class testCustomClient {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String args[]) throws Exception{
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .build();
        final CloseableHttpClient httpClient = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(4)//default is 2
                .evictIdleConnections(10L, TimeUnit.SECONDS)
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
                    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                        return 1000;
                    }
                })
                .build();
        final HttpGet get = new HttpGet("http://remote.alibaba.net:8081/hiqcDirect/avail?httpclient=1");//remote host sleeps for 5 seconds
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable run = new Runnable(){
            public void run() {
                try {
                    HttpResponse response = httpClient.execute(get);
                    int i = count.addAndGet(1);
                    System.out.println(i);
                    System.out.println(EntityUtils.toString(response.getEntity()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        executorService.submit(run);
        executorService.submit(run);
        executorService.submit(run);
        executorService.submit(run);

        executorService.shutdown();

        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
