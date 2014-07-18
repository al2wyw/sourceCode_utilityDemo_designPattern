package com.expedia.e3.insurance.tools;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.easymock.EasyMock;







import java.net.Socket;
import java.net.URLConnection;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
/**
 * 
 * TODO v-yangli Describe HttpToolsTest
 * 
 * @author <a href="mailto:v-yangli@expedia.com">v-yangli</a>
 *
 */
public class HttpToolsTest
{
	private final static String data = "test1\ntest2\ntest3\n";
	private static URLConnection con;
	private static ByteArrayInputStream arrays;
	private static ServerSocket serverSocket;
	
	@BeforeClass
    public static void setUp() throws IOException {
    	arrays = new ByteArrayInputStream(data.getBytes("UTF8"));
		con = EasyMock.createMock(URLConnection.class);
        EasyMock.expect(con.getOutputStream()).andReturn(new ByteArrayOutputStream()).anyTimes();
        EasyMock.expect(con.getInputStream()).andReturn(arrays).anyTimes();
        EasyMock.replay(con);
        System.out.println("test");
        
        new Thread(){
        	public void run(){
        		try {
					serverSocket = new ServerSocket(8088);
					serverSocket.setReuseAddress(true);
					Socket socket = serverSocket.accept();
	                OutputStream out = socket.getOutputStream();
	                out.write(data.getBytes("UTF8"));
	                out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }.run();
    }
    
	@AfterClass
	public static void tearDown() throws IOException {
		if(serverSocket != null){
			serverSocket.close();
		}
	}
	
    @Test
    public void testDoPost() throws IOException {
    	String expected = "test1test2test3";
    	String result = HttpTools.doPost(con, "test");
    	Assert.assertNotNull(result);
    	Assert.assertEquals(expected,result);
    }
    
    @Test
    public void testDoGet() throws IOException {
    	String expected = "test1test2test3";
    	String result = HttpTools.doGet(con);
    	Assert.assertNotNull(result);
    	Assert.assertEquals(expected,result);
    }
    
    @Test
    public void testPostRequestByGlobalURLConnectionWithHttp() throws IOException{
//    	URL url = EasyMock.createMock(URL.class);
//    	HttpURLConnection httpCon = EasyMock.createMock(HttpURLConnection.class);
//    	EasyMock.expect(url.openConnection()).andReturn(httpCon).anyTimes();
//    	EasyMock.replay();
    	String expected = "test1test2test3";
    	while(serverSocket==null);
    	String result = HttpTools.postRequestByGlobalURLConnection("http://localhost:8088", "request=help", "GET", -1, -1);
    	Assert.assertNotNull(result);
    	Assert.assertEquals(expected,result);
    }
    
//    @Test
//    public void testPostRequestByGlobalURLConnectionWithHttps() throws IOException{
//    	URL url = EasyMock.createMock(URL.class);
//    	HttpsURLConnection httpsCon = EasyMock.createMock(HttpsURLConnection.class);
//    	EasyMock.expect(url.openConnection()).andReturn(httpsCon).anyTimes();
//    	EasyMock.replay();
//    	String expected = "test1test2test3";
//    	String result = HttpTools.postRequestByGlobalURLConnection("https://testforfun.com", "request=help", "get", 45, 43);
//    	Assert.assertNotNull(result);
//    	Assert.assertEquals(expected,result);
//    }
}
