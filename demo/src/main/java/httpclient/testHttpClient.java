package httpclient;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnny.ly on 2016/7/19.
 */
public class testHttpClient {
    public static void main(String args[]) throws Exception{
        ConnectionConfig connectionConfig = ConnectionConfig
                .custom()
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(MessageConstraints.custom().setMaxHeaderCount(20).build())
                .build();
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(4000)
                .setSoReuseAddress(true)
                .setSoKeepAlive(true)
                .setTcpNoDelay(true)
                .build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(4000)
                .setConnectTimeout(3000)
                .build();
        PoolingHttpClientConnectionManager manager =  new PoolingHttpClientConnectionManager();
        manager.setDefaultConnectionConfig(connectionConfig);
        manager.setDefaultSocketConfig(socketConfig);
        manager.setMaxTotal(100);
        manager.setDefaultMaxPerRoute(10);
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(manager)
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
                    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                        return 1000;
                    }
                })
                .build();
        HttpGet get = new HttpGet("http://baidu.com");
        HttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            System.out.print("good");
        }
    }
}
