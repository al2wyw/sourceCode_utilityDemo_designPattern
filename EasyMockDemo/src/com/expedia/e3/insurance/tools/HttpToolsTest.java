package com.expedia.e3.insurance.tools;

import org.junit.Assert;
import org.junit.Test;
import org.easymock.EasyMock;

import com.expedia.e3.insurance.tools.HttpTools;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLConnection;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

/**
 * 
 * Unit Test for Class HttpTools
 * 
 * @author <a href="mailto:v-yangli@expedia.com">v-yangli</a>
 * 
 */
public class HttpToolsTest
{
    private final static String responseHeadLine = "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n";
    private final static String data = "test1\ntest2\ntest3\n";
    private URLConnection con;
    private ByteArrayInputStream arrays;
    private ServerSocket serverSocket;

    public void mockObject() throws IOException
    {
        arrays = new ByteArrayInputStream(data.getBytes("UTF8"));
        con = EasyMock.createMock(URLConnection.class);
        EasyMock.expect(con.getOutputStream()).andReturn(new ByteArrayOutputStream()).anyTimes();
        EasyMock.expect(con.getInputStream()).andReturn(arrays).anyTimes();
        EasyMock.replay(con);
    }

    public void setUp(final Boolean exceptionFlag)
    {
        new Thread()
            {
                public void run()
                {
                    try
                    {
                        serverSocket = new ServerSocket();
                        serverSocket.setReuseAddress(true);
                        serverSocket.bind(new InetSocketAddress(8089));
                        Socket clientSocket = serverSocket.accept();
                        OutputStream out = clientSocket.getOutputStream();
                        if (!exceptionFlag)
                            out.write(responseHeadLine.getBytes("UTF8"));
                        out.write(data.getBytes("UTF8"));
                        out.flush();
                        try
                        {
                            Thread.sleep(500);
                        }
                        catch (Exception e)
                        {

                        }
                        out.close();
                        clientSocket.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }.start();
    }

    public void tearDown() throws IOException
    {
        if (serverSocket != null && !serverSocket.isClosed())
        {
            serverSocket.close();
        }
    }

    @Test
    public void testDoPost() throws IOException
    {
        mockObject();
        String expected = "test1test2test3";
        String result = HttpTools.doPost(con, "test");
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testDoGet() throws IOException
    {
        mockObject();
        String expected = "test1test2test3";
        String result = HttpTools.doGet(con);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testPostRequestByGlobalURLConnectionWithHttpGet() throws IOException
    {
        setUp(false);
        String expected = "test1test2test3";
        String result = HttpTools.postRequestByGlobalURLConnection("http://localhost:8089/test", "request=help", "GET", -1, -1);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
        tearDown();
    }

    @Test
    public void testPostRequestByGlobalURLConnectionWithHttpPost() throws IOException
    {
        setUp(false);
        String expected = "test1test2test3";
        String result = HttpTools.postRequestByGlobalURLConnection("http://localhost:8089/test", "request=help", "POST", 5000, 5000);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
        tearDown();
    }

    @Test(expected = IllegalStateException.class)
    public void testPostRequestByGlobalURLConnectionWithException() throws IOException
    {
        setUp(true);
        String expected = "test1test2test3";
        String result = HttpTools.postRequestByGlobalURLConnection("http://localhost:8089/test", "request=help", "POST", -1, -1);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
        tearDown();
    }

}
