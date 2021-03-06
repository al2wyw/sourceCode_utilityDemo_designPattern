package httpclient;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnny.ly on 2016/7/19.
 */
public class testHttpClient {
    public static void main(String args[]) throws Exception{
        //proxy ??? ssl???
        //proxy is a HttpHost, just need ip and port, set it to builder. The auth process is provided by CredentialsProvider and AuthSchemeProvider.
        //AuthScheme including Basic,Digest,NTLM,Kerberos ... , preemptively authenticate provided by AuthCache
        //AbstractConnPool has route to pool map, route has host and proxy, different host will generate different route
        //DefaultRoutePlanner to determine the proxy of route
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
                .setSocketTimeout(5000)
                .setConnectTimeout(3000)
                .build();
        PoolingHttpClientConnectionManager manager =  new PoolingHttpClientConnectionManager();//内含AbstractConnPool和PoolEntry
        manager.setDefaultConnectionConfig(connectionConfig);
        manager.setDefaultSocketConfig(socketConfig);
        manager.setMaxTotal(100);
        manager.setDefaultMaxPerRoute(10);
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(manager)
                .evictIdleConnections(10L, TimeUnit.SECONDS) //第一个参数是控制conn的最大idle的，closeIdle相关，会生成一个线程去清理
                .setConnectionTimeToLive(60, TimeUnit.SECONDS) //for connections in PoolingHttpClientConnectionManager, used only by PoolingHttpClientConnectionManager
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
                    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                        return 1000;
                    }
                })
                .build();//InternalHttpClient -> MainClientExec
        //PoolEntry.updateExpiry会更新timetolive, touch一下idle以达到closeIdle的目的
        //(getKeepAliveDuration -> ConnectionHolder.setValidFor -> ConnectionHolder.release -> PoolEntry.updateExpiry)
        //timetolive是conn的整个生命周期存活时间，默认为-1，closeExpire相关
        //KeepAliveDuration会影响conn的存活时间, 默认实现是从Keep-Alive报头取值
        HttpGet get = new HttpGet("http://baidu.com");
        HttpResponse response = httpClient.execute(get); //AbstractConnPool.getPoolEntryBlocking
        //HttpRequestExecutor.execute in the same thread, no asyn threadpool to execute
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            System.out.println("good");
        }
        HttpPost post = new HttpPost("https://interface.demo.gta-travel.com/rbscnapi/RequestListenerServlet");
        String postData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Request>\n" +
                "<Source>\n" +
                "<RequestorID Client=\"93\" EMailAddress=\"XML@ALITRIP.COM\" Password=\"PASS\"/>\n" +
                "<RequestorPreferences Country=\"CN\" Currency=\"GBP\" Language=\"en\">\n" +
                "<RequestMode>SYNCHRONOUS</RequestMode>\n" +
                "</RequestorPreferences>\n" +
                "</Source>\n" +
                "<RequestDetails>\n" +
                "<SearchHotelPricePaxRequest>\n" +
                "<ItemDestination DestinationCode = \"NCE\" DestinationType = \"city\"/>\n" +
                "<ItemCodes>\n" +
                "<ItemCode>ACO</ItemCode>\n" +
                "<ItemCode>BEL</ItemCode>\n" +
                "<ItemCode>COM</ItemCode>\n" +
                "</ItemCodes>\n" +
                "<PeriodOfStay>\n" +
                "<CheckInDate>2016-11-12</CheckInDate>\n" +
                "<Duration>2</Duration>\n" +
                "</PeriodOfStay>\n" +
                "<IncludePriceBreakdown/>\n" +
                "<IncludeChargeConditions/>\n" +
                "<PaxRooms>\n" +
                "<PaxRoom Adults=\"2\" Cots=\"0\" RoomIndex=\"1\"/>\n" +
                "</PaxRooms>\n" +
                "</SearchHotelPricePaxRequest>\n" +
                "</RequestDetails>\n" +
                "</Request>";
        StringEntity entity = new StringEntity(postData, ContentType.APPLICATION_XML);
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        HttpResponse response1 = httpClient.execute(post);
        HttpEntity responseEntity = response1.getEntity();
        if(responseEntity!=null){
            System.out.println(responseEntity.getContentType().getValue());
            //System.out.println(responseEntity.getContentEncoding().getValue());//may be null
            System.out.println(EntityUtils.toString(responseEntity));
        }
    }
}
