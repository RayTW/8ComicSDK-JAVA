package raytw.sdk.comic.net;

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
 * 簡易讀取網頁工具class.
 *
 * @author Ray Lee Created on 2017/08/11
 */
public class EasyHttp {
  private static final Pattern sCharsetPattern =
      Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");

  private String url;
  private String method;
  private String writeCharset = "UTF-8"; // default
  private String readCharset = "UTF-8"; // default
  private Boolean isRedirect;
  private Long readTimeout;
  private Long connectTimeout;
  private int bufferReaderKb = 4; // 4kb
  private Map<String, String> requestHeader;
  private URLConnection urlConnection;

  /**
   * 連線.
   *
   * @return 連線結果
   * @throws IOException 連線失敗
   */
  public Response connect() throws IOException {
    boolean isGet = false;
    String queryString = null;
    URI uri = URI.create(url);

    if ("GET".equalsIgnoreCase(method)) {
      isGet = true;
    } else if ("POST".equalsIgnoreCase(method)) {
      isGet = false;
      url = uri.getScheme() + "//" + uri.getHost() + uri.getPath();
      queryString = uri.getQuery();
    }

    urlConnection = new URL(url).openConnection();
    HttpURLConnection connection = (HttpURLConnection) urlConnection;

    // 處理request header---begin---
    setUpRequestProperty(connection);
    // 處理request header---end---

    if (readTimeout != null) {
      connection.setReadTimeout(readTimeout.intValue());
    }

    if (connectTimeout != null) {
      connection.setConnectTimeout(connectTimeout.intValue());
    }

    connection.setDoInput(true);
    connection.setRequestMethod(method.toUpperCase());

    if (!isGet) {
      connection.setDoOutput(true);
    }

    connection.setUseCaches(false);
    connection.setAllowUserInteraction(true);

    if (isRedirect != null) {
      connection.setInstanceFollowRedirects(isRedirect);
    }

    if (!isGet && queryString != null && !queryString.isEmpty()) {
      BufferedOutputStream dos =
          new BufferedOutputStream(new DataOutputStream(connection.getOutputStream()));
      dos.write(queryString.getBytes(writeCharset));
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
    Iterator<Map.Entry<String, String>> iter = requestHeader.entrySet().iterator();
    Map.Entry<String, String> entry = null;

    while (iter.hasNext()) {
      entry = iter.next();
      connection.setRequestProperty(entry.getKey(), entry.getValue());
    }
  }

  private Response createResponse(HttpURLConnection connection, boolean newline)
      throws IOException {
    int code = connection.getResponseCode();
    InputStream inputStream = null;
    String body = null;
    String contentType = connection.getHeaderField("content-type");
    String useCharset = getCharsetFromContentType(contentType);

    // 取reponse取得的chsetat為null時，採用預設的charset
    if (useCharset == null) {
      useCharset = readCharset;
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

  private String readString(InputStream inputStream, String charset, boolean newline)
      throws IOException {
    StringBuffer buffer = new StringBuffer();
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(inputStream, charset), bufferReaderKb * 1024);
    String str = null;

    while ((str = reader.readLine()) != null) {
      buffer.append(str);
      if (newline) {
        buffer.append(System.lineSeparator());
      }
    }

    return buffer.toString();
  }

  private InputStream getInputStream(HttpURLConnection connection) throws IOException {
    if ("gzip".equalsIgnoreCase(connection.getContentEncoding())) {
      return new GZIPInputStream(connection.getInputStream());
    }

    if ("deflate".equals(connection.getContentEncoding())) {
      return new InflaterInputStream(connection.getInputStream());
    }
    return connection.getInputStream();
  }

  private InputStream getErrorStream(HttpURLConnection connection) throws IOException {
    if ("gzip".equalsIgnoreCase(connection.getContentEncoding())) {
      return new GZIPInputStream(connection.getErrorStream());
    }

    if ("deflate".equals(connection.getContentEncoding())) {
      return new InflaterInputStream(connection.getErrorStream());
    }
    return connection.getErrorStream();
  }

  private String getCharsetFromContentType(String contentType) {
    if (contentType == null) {
      return null;
    }

    Matcher m = sCharsetPattern.matcher(contentType);
    if (m.find()) {
      return m.group(1).trim().toUpperCase();
    }
    return null;
  }

  /**
   * 構建EasyHttp.
   *
   * @author ray
   */
  public static class Builder {
    private String url;
    private String method;
    private String writeCharset;
    private String readCharset;
    private Boolean isRedirect;
    private Long readTimeout;
    private Long connectTimeout;
    private Integer bufferReaderKb;
    private Map<String, String> requestHeader;

    public Builder() {
      requestHeader = new HashMap<String, String>();
    }

    public Builder setUrl(String url) {
      this.url = url;
      return this;
    }

    public Builder putHeader(String key, String value) {
      requestHeader.put(key, value);
      return this;
    }

    public Builder setCookie(String cookie) {
      putHeader("Cookie", cookie);
      return this;
    }

    public Builder setHost(String host) {
      putHeader("Host", host);
      return this;
    }

    public Builder setReferer(String referer) {
      putHeader("Referer", referer);
      return this;
    }

    public Builder setWriteCharset(String charset) {
      writeCharset = charset;
      return this;
    }

    public Builder setReadCharset(String charset) {
      readCharset = charset;
      return this;
    }

    public Builder setMethod(String method) {
      this.method = method;
      return this;
    }

    public Builder setUserAgent(String userAgent) {
      putHeader("User-Agent", userAgent);
      return this;
    }

    public Builder setIsRedirect(boolean isRedirect) {
      this.isRedirect = isRedirect;
      return this;
    }

    public Builder setReadTimeout(int time, TimeUnit unit) {
      readTimeout = unit.toMillis(time);
      return this;
    }

    public Builder setConnectTimeout(int time, TimeUnit unit) {
      connectTimeout = unit.toMillis(time);
      return this;
    }

    public Builder setBufferReadKb(int kb) {
      bufferReaderKb = kb;
      return this;
    }

    /**
     * 創建EasyHttp.
     *
     * @return EasyHttp
     */
    public EasyHttp build() {
      EasyHttp obj = new EasyHttp();
      obj.url = url;
      obj.method = method;

      if (writeCharset != null) {
        obj.writeCharset = writeCharset;
      }

      if (readCharset != null) {
        obj.readCharset = readCharset;
      }

      if (isRedirect != null) {
        obj.isRedirect = isRedirect;
      }

      if (readTimeout != null) {
        obj.readTimeout = readTimeout;
      }

      if (connectTimeout != null) {
        obj.connectTimeout = connectTimeout;
      }

      if (bufferReaderKb != null && bufferReaderKb.intValue() > 0) {
        obj.bufferReaderKb = bufferReaderKb;
      }

      obj.requestHeader = requestHeader;

      return obj;
    }
  }

  /**
   * 網路回覆封包.
   *
   * @author ray
   */
  public static class Response {
    private int statusCode;
    private String body;
    private Map<String, List<String>> headers;

    Response(int statusCode, String body, Map<String, List<String>> header) {
      this.statusCode = statusCode;
      this.body = body;
      this.headers = header;
    }

    public Map<String, List<String>> getHeaders() {
      return headers;
    }

    public Object getHeader(String name) {
      return headers.get(name);
    }

    public int getStatusCode() {
      return statusCode;
    }

    public String getBody() {
      return body != null ? body : "";
    }
  }
}
