package test1;

/**
 * Copyright 2013 Expedia, Inc. All rights reserved.
 * EXPEDIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.easymock.EasyMock;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 
 * Unit Test for Class HttpTools
 * 
 * @author <a href="mailto:v-yangli@expedia.com">v-yangli</a>
 * 
 */
public class HttpToolsTest
{
    private final static String data = "test1\ntest2\ntest3\n";
    private URLConnection con;
    private HttpURLConnection httpCon;
    private ByteArrayInputStream arrays;
    private urlWrapper url;

    @Before
    public void mockObject() throws IOException
    {
        arrays = new ByteArrayInputStream(data.getBytes("UTF8"));
        con = EasyMock.createMock(URLConnection.class);
        url = EasyMock.createMock(urlWrapper.class);
        httpCon = EasyMock.createMock(HttpURLConnection.class);
        EasyMock.expect(url.openConnection()).andReturn(httpCon).anyTimes();
        httpCon.setRequestMethod(EasyMock.anyObject(String.class));
        httpCon.setReadTimeout(EasyMock.anyInt());
        httpCon.setConnectTimeout(EasyMock.anyInt());
        httpCon.setDoOutput(EasyMock.anyBoolean());
        EasyMock.expect(httpCon.getOutputStream()).andReturn(new ByteArrayOutputStream()).anyTimes();
        EasyMock.expect(httpCon.getInputStream()).andReturn(arrays).anyTimes();
        
        EasyMock.expect(con.getOutputStream()).andReturn(new ByteArrayOutputStream()).anyTimes();
        EasyMock.expect(con.getInputStream()).andReturn(arrays).anyTimes();
        EasyMock.replay(url);
        EasyMock.replay(httpCon);
        EasyMock.replay(con);
    }

    @Test
    public void testDoPost() throws IOException
    {
        String expected = "test1test2test3";
        String result = HttpTools.doPost(con, "test");
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testDoGet() throws IOException
    {
        String expected = "test1test2test3";
        String result = HttpTools.doGet(con);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testPostRequestByGlobalURLConnectionWithHttpGet() throws IOException
    {
        String expected = "test1test2test3";
        String result = HttpTools.postRequest(url, "request=help", "GET", -1, -1);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testPostRequestByGlobalURLConnectionWithHttpPost() throws IOException
    {
        String expected = "test1test2test3";
        String result = HttpTools.postRequest(url, "request=help", "POST", 5000, 5000);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

//    @Test(expected = IllegalStateException.class)
    public void testPostRequestByGlobalURLConnectionWithException() throws IOException
    {
        String expected = "test1test2test3";
        String result = HttpTools.postRequest(url, "request=help", "POST", -1, -1);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

}
