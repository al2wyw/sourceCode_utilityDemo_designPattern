package com.expedia.e3.insurance.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class HttpTools {

	private HttpTools() {
	}

	static public String postRequestByGlobalURLConnection(String urlString,
			String requestString, String method, int connectTimeout,
			int readTimeout) {
		final int DEFAULT_CONNECT_TIMEOUT = 5000;
		final int DEFAULT_READ_TIMEOUT = 120000;

		String responseString;
		requestString = requestString.replaceAll("(\\r|\\n)", "");

		String requestLog = "URLConnection [" + urlString
				+ "] sending request:" + requestString;
		try {
			URL url = new URL(urlString);

			URLConnection con = null;
			if (urlString.indexOf("https:") != -1) {
				HttpsURLConnection connectS = (HttpsURLConnection) url
						.openConnection();
				connectS.setRequestMethod(method);
				con = connectS;
			} else {
				HttpURLConnection connect = (HttpURLConnection) url
						.openConnection();
				connect.setRequestMethod(method);
				con = connect;
			}
			con.setReadTimeout(readTimeout > 0 ? readTimeout
					: DEFAULT_READ_TIMEOUT);
			con.setConnectTimeout(connectTimeout > 0 ? connectTimeout
					: DEFAULT_CONNECT_TIMEOUT);
			con.setDoOutput(true);

			if (method.equalsIgnoreCase("POST")) {
				responseString = doPost(con, requestString);
			} else {
				responseString = doGet(con);
			}
		} catch (Exception e) {
			throw new IllegalStateException("Error: " + requestLog, e);
		}
		return responseString;
	}

	static public String doPost(URLConnection con, String requestString)
			throws IOException {
		StringBuffer resposneMessage = new StringBuffer();
		if (con != null) {
			try {
				DataOutputStream requestWriteStream = new DataOutputStream(
						con.getOutputStream());
				requestWriteStream.write(requestString.getBytes());
				requestWriteStream.close();
				requestWriteStream = null;

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				String stringOneLine = reader.readLine();

				while (null != stringOneLine) {
					resposneMessage.append(stringOneLine);
					stringOneLine = reader.readLine();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return resposneMessage.toString();
	}

	static public String doGet(URLConnection con) throws IOException {
		StringBuffer resposneMessage = new StringBuffer();
		if (con != null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				String stringOneLine = reader.readLine();

				while (null != stringOneLine) {
					resposneMessage.append(stringOneLine);
					System.out.println("test2");
					stringOneLine = reader.readLine();
					System.out.println("test1");
				}
				System.out.println("test end");
			} catch (IOException e) {
				throw e;
			}
		}
		return resposneMessage.toString();
	}

}
