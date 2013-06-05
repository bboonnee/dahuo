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
	// ����HttpClient����
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
	 *            ���������URL
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static String getRequest(String url) throws Exception {
		// �������ӳ�ʱʱ������ݶ�ȡ��ʱʱ��
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams
				.setConnectionTimeout(httpParams, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
		httpClient = new DefaultHttpClient(httpParams);
		// ����HttpGet����
		HttpGet get = new HttpGet(url);
		// ����GET����
		HttpResponse httpResponse = httpClient.execute(get);
		// ����������ɹ��ط�����Ӧ
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// ��ȡ��������Ӧ�ַ���
			String result = EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}
	/**
	 * 
	 * @param url
	 *            ���������URL
	 * @param params
	 *            �������
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static String postRequest(String url, Map<String, String> rawParams,
			int type) {
		String sbs = "";
		// �������ӳ�ʱʱ������ݶ�ȡ��ʱʱ��
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams
				.setConnectionTimeout(httpParams, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
		httpClient = new DefaultHttpClient(httpParams);
		// ����HttpPost����
		HttpPost post = new HttpPost(url);
		// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// ��װ�������
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// �����������
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// ����POST����
			HttpResponse httpResponse = httpClient.execute(post);
			if (type == 99) {
				sbs = responseString(httpResponse);
			}
			// ����������ɹ��ط�����Ӧ
			int gt = httpResponse.getStatusLine().getStatusCode();
			if (gt == 200) {
				// ��ȡ��������Ӧ�ַ���
				String response =EntityUtils.toString(httpResponse.getEntity()); 	
				Log.w(LOG, response);
				return response;

			}
		} catch (SocketTimeoutException e) {
			// ��һ����ʾ��ʱ��
			e.printStackTrace();
			if(type ==0 )			return "['overtime']";
			else return "{'result': 'overtime'}";
		} catch (ConnectTimeoutException e) {
			// ��һ����ʾ��ʱ��
			e.printStackTrace();
			if(type ==0 )			return "['overtime']";
			else return "{'result': 'overtime'}";
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			// DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
			Log.w(LOG, "-------error "+e.toString());
			if(type ==0 )			return "['connerr']";
			else return "{'result': 'connerr'}";
		} catch (Exception e) {
			// DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
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
