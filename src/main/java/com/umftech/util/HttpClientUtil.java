package com.umftech.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import com.umpay.api.log.SysOutLogger;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.charset.spi.CharsetProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;
import java.util.SortedMap;
import java.util.TreeMap;
import sun.misc.ASCIICaseInsensitiveComparator;
import sun.nio.cs.StandardCharsets;
import sun.nio.cs.ThreadLocalCoders;
import sun.security.action.GetPropertyAction;


public class HttpClientUtil {

	public static String doGet(String url, Map<String, String> param) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				//UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"GBK");
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"UTF-8");
				//entity.setContentEncoding("utf-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}
	
	

	 public static String enCodetoString(
		            final HttpEntity entity, final String defaultCharset) throws IOException, ParseException {
		        return enCodetoStringDo(entity, defaultCharset != null ? Charset.forName(defaultCharset) : null);
		    }
		
		  public static String enCodetoStringDo(
		            final HttpEntity entity, Charset defaultCharset) throws IOException, ParseException {	
		        if (entity == null) {
		            throw new IllegalArgumentException("HTTP entity may not be null");
		        }
		        InputStream instream = entity.getContent();
		        if (instream == null) {
		            return null;
		        }
		        try {
		            if (entity.getContentLength() > Integer.MAX_VALUE) {
		                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
		            }
		            int i = (int)entity.getContentLength();
		            if (i < 0) {
		                i = 4096;
		            }
		            Charset charset = null;
		            try {
//		                ContentType contentType = ContentType.get(entity);
//		                if (contentType != null) {
//		                    charset = contentType.getCharset();
//		                }
		            } catch (final UnsupportedCharsetException ex) {
		                throw new UnsupportedEncodingException(ex.getMessage());
		            }
		            if (charset == null) {
		                charset = defaultCharset;
		            }
		            if (charset == null) {
		                charset = HTTP.DEF_CONTENT_CHARSET;
		            }
		            Reader reader = new InputStreamReader(instream, charset);
		            CharArrayBuffer buffer = new CharArrayBuffer(i);
		            char[] tmp = new char[1024];
		            int l;
		            while((l = reader.read(tmp)) != -1) {
		                buffer.append(tmp, 0, l);
		            }
		            return buffer.toString();
		        } finally {
		            instream.close();
		        }
		    }
		  
		  
		  

	public static String doPost(String url) {
		return doPost(url, null);
	}
	
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}
}
