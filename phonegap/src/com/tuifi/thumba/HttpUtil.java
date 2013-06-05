/**
 * 
 */
package com.tuifi.thumba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 */
public class HttpUtil {
	private static String LOG = "HttpUtil";
	// 创建HttpClient对象
	public static HttpClient httpClient;
	//public static final String SERVER_URL = "http://192.168.1.102/";
	public static final String SERVER_URL = "http://54.251.118.17/";
	public static final String BASE_URL = SERVER_URL+"mba/home/";	
	public static final String UPDATE_URL = SERVER_URL+"down/version.json";
	//public static final String BASE_URL ="http://54.251.118.17/mba/home";
	//public static final String BASE_URL ="http://yibo.eayun.net/think/Quanzi/";
	//
	private static int timeoutConnection = 6000;
	private static int timeoutSocket = 6000;

	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String getRequest(String url) throws Exception {
		// 设置连接超时时间和数据读取超时时间
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams
				.setConnectionTimeout(httpParams, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
		httpClient = new DefaultHttpClient(httpParams);
		// 创建HttpGet对象。
		HttpGet get = new HttpGet(url);
		// 发送GET请求
		HttpResponse httpResponse = httpClient.execute(get);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}
	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String postRequest(String url, Map<String, String> rawParams,
			int type) {
		String sbs = "";
		// 设置连接超时时间和数据读取超时时间
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams
				.setConnectionTimeout(httpParams, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
		httpClient = new DefaultHttpClient(httpParams);
		// 创建HttpPost对象。
		HttpPost post = new HttpPost(url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// 设置请求参数
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// 发送POST请求
			HttpResponse httpResponse = httpClient.execute(post);
			if (type == 99) {
				sbs = responseString(httpResponse);
			}
			// 如果服务器成功地返回响应
			int gt = httpResponse.getStatusLine().getStatusCode();
			if (gt == 200) {
				// 获取服务器响应字符串
				String response =EntityUtils.toString(httpResponse.getEntity()); 	
				Log.w(LOG, response);
				return response;

			}
		} catch (SocketTimeoutException e) {
			// 这一步表示超时了
			e.printStackTrace();
			if(type ==0 )			return "['overtime']";
			else return "{'result': 'overtime'}";
		} catch (ConnectTimeoutException e) {
			// 这一步表示超时了
			e.printStackTrace();
			if(type ==0 )			return "['overtime']";
			else return "{'result': 'overtime'}";
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, "-------error "+e.toString());
			if(type ==0 )			return "['connerr']";
			else return "{'result': 'connerr'}";
		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, "-------error "+e.toString());
			if(type ==0 )			return "['connerr']";
			else return "{'result': 'connerr'}";
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		if(type ==1 )			return "['null']";
		else return "null";
		
	}

	public static String responseString(HttpResponse response) throws Exception {
		String sbs = "";

		if (response != null) {
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			sbs = sb.toString();

		}

		return sbs;

	}
}
