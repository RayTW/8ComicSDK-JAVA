package net.xuite.blog.ray00000test.library.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * 簡易讀取網頁工具class
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class EasyHttp {
	private static final Pattern sCharsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");

	private String mUrl;
	private String mMethod;
	private String mWriteCharset = "UTF-8";//default
	private String mReadCharset = "UTF-8";//default
	private Boolean mIsRedirect;
	private Long mReadTimeout;
	private Long mConnectTimeout;
	private int mBufferReaderKB = 4;// 4kb
	private Map<String, String> mRequestHeader;
	private URLConnection mURLConnection;

	public Response connect() throws IOException {
		boolean isGet = false;
		String queryString = null;
		URI uri = URI.create(mUrl);

		if ("GET".equalsIgnoreCase(mMethod)) {
			isGet = true;
		} else if ("POST".equalsIgnoreCase(mMethod)) {
			isGet = false;
			mUrl = uri.getScheme() + "//" + uri.getHost() + uri.getPath();
			queryString = uri.getQuery();
		}

		mURLConnection = new URL(mUrl).openConnection();
		HttpURLConnection connection = (HttpURLConnection) mURLConnection;

		// 處理request header---begin---
		setUpRequestProperty(connection);
		// 處理request header---end---

		if (mReadTimeout != null) {
			connection.setReadTimeout(mReadTimeout.intValue());
		}

		if (mConnectTimeout != null) {
			connection.setConnectTimeout(mConnectTimeout.intValue());
		}

		connection.setDoInput(true);
		connection.setRequestMethod(mMethod.toUpperCase());

		if (!isGet) {
			connection.setDoOutput(true);
		}

		connection.setUseCaches(false);
		connection.setAllowUserInteraction(true);

		if (mIsRedirect != null) {
			connection.setInstanceFollowRedirects(mIsRedirect);
		}

		if (!isGet && queryString != null && !queryString.isEmpty()) {
			BufferedOutputStream dos = new BufferedOutputStream(
					new DataOutputStream(connection.getOutputStream()));
			dos.write(queryString.getBytes(mWriteCharset));
			dos.flush();
			dos.close();
		}

		try {
			return createResponse(connection, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection.getInputStream() != null) {
				connection.getInputStream().close();
			}
		}
		return null;
	}

	private void setUpRequestProperty(URLConnection connection) {
		Iterator<Map.Entry<String, String>> iter = mRequestHeader.entrySet()
				.iterator();
		Map.Entry<String, String> entry = null;

		while (iter.hasNext()) {
			entry = iter.next();
			connection.setRequestProperty(entry.getKey(), entry.getValue());
		}
	}

	private Response createResponse(HttpURLConnection connection,
			boolean newline) throws IOException {
		int code = connection.getResponseCode();
		InputStream inputStream = null;
		String body = null;
		String contentType = connection.getHeaderField("content-type");
		String useCharset = getCharsetFromContentType(contentType);
		
		//取reponse取得的chsetat為null時，採用預設的charset
		if(useCharset == null){
			useCharset = mReadCharset;
		}
		
		try {
			inputStream = getInputStream(connection);
			body = readString(inputStream, useCharset, newline);
		} catch (IOException e) {
			e.printStackTrace();
			inputStream = getErrorStream(connection);
			body = readString(inputStream, useCharset, newline);
		}

		return new Response(code, body, connection.getHeaderFields());
	}

	private String readString(InputStream inputStream, String charset,
			boolean newline) throws IOException {
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream, charset), mBufferReaderKB * 1024);
		String str = null;

		while ((str = reader.readLine()) != null) {
			buffer.append(str);
			if (newline) {
				buffer.append(System.lineSeparator());
			}
		}

		return buffer.toString();
	}

	private InputStream getInputStream(HttpURLConnection connection)
			throws IOException {
		if ("gzip".equalsIgnoreCase(connection.getContentEncoding())) {
			return new GZIPInputStream(connection.getInputStream());
		}

		if ("deflate".equals(connection.getContentEncoding())) {
			return new InflaterInputStream(connection.getInputStream());
		}
		return connection.getInputStream();
	}

	private InputStream getErrorStream(HttpURLConnection connection)
			throws IOException {
		if ("gzip".equalsIgnoreCase(connection.getContentEncoding())) {
			return new GZIPInputStream(connection.getErrorStream());
		}

		if ("deflate".equals(connection.getContentEncoding())) {
			return new InflaterInputStream(connection.getErrorStream());
		}
		return connection.getErrorStream();
	}
	
	  private String getCharsetFromContentType(String contentType) {
	    if (contentType == null){
	    	return null;
	    }

	    Matcher m = sCharsetPattern.matcher(contentType);
	    if (m.find()) {
	      return m.group(1).trim().toUpperCase();
	    }
	    return null;
	  }

	public static class Builder {
		private String mUrl;
		private String mMethod;
		private String mWriteCharset;
		private String mReadCharset;
		private Boolean mIsRedirect;
		private Long mReadTimeout;
		private Long mConnectTimeout;
		private Integer mBufferReaderKB;
		private Map<String, String> mRequestHeader;

		public Builder() {
			mRequestHeader = new HashMap<String, String>();
		}

		public Builder setUrl(String url) {
			mUrl = url;
			return this;
		}

		public Builder putHeader(String key, String value) {
			mRequestHeader.put(key, value);
			return this;
		}

		public Builder setCookie(String cookie) {
			putHeader("Cookie", cookie);
			return this;
		}

		public Builder setReferer(String referer) {
			putHeader("Referer", referer);
			return this;
		}

		public Builder setWriteCharset(String charset) {
			mWriteCharset = charset;
			return this;
		}
		
		public Builder setReadCharset(String charset) {
			mReadCharset = charset;
			return this;
		}

		public Builder setMethod(String method) {
			mMethod = method;
			return this;
		}

		public Builder setUserAgent(String userAgent) {
			putHeader("User-Agent", userAgent);
			return this;
		}

		public Builder setIsRedirect(boolean isRedirect) {
			mIsRedirect = isRedirect;
			return this;
		}

		public Builder setReadTimeout(int time, TimeUnit unit) {
			mReadTimeout = unit.toMillis(time);
			return this;
		}

		public Builder setConnectTimeout(int time, TimeUnit unit) {
			mConnectTimeout = unit.toMillis(time);
			return this;
		}

		public Builder setBufferReadKB(int kb) {
			mBufferReaderKB = kb;
			return this;
		}

		public EasyHttp build() {
			EasyHttp obj = new EasyHttp();
			obj.mUrl = mUrl;
			obj.mMethod = mMethod;
			
			if(mWriteCharset != null){
				obj.mWriteCharset = mWriteCharset;
			}
			
			if(mReadCharset != null){
				obj.mReadCharset = mReadCharset;
			}

			if (mIsRedirect != null) {
				obj.mIsRedirect = mIsRedirect;
			}

			if (mReadTimeout != null) {
				obj.mReadTimeout = mReadTimeout;
			}

			if (mConnectTimeout != null) {
				obj.mConnectTimeout = mConnectTimeout;
			}

			if (mBufferReaderKB != null && mBufferReaderKB.intValue() > 0) {
				obj.mBufferReaderKB = mBufferReaderKB;
			}

			obj.mRequestHeader = mRequestHeader;

			return obj;
		}
	}

	public static class Response {
		private int mStatusCode;
		private String mBody;
		private Map<String, List<String>> mHeaders;

		Response(int statusCode, String body, Map<String, List<String>> header) {
			mStatusCode = statusCode;
			mBody = body;
			mHeaders = header;
		}

		public Map<String, List<String>> getHeaders() {
			return mHeaders;
		}

		public Object getHeader(String name) {
			return mHeaders.get(name);
		}

		public int getStatusCode() {
			return mStatusCode;
		}

		public String getBody() {
			return mBody != null ? mBody : "";
		}
	}

	public static interface OnResponseListener {
		public boolean onResponse(Response response);
	}
}
