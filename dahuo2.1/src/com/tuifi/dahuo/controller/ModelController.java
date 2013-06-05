package com.tuifi.dahuo.controller;

import java.io.Serializable;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tuifi.dahuo.tools.HttpUtil;



public class ModelController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "ModelController";
	
	// ���巢������ķ���
	public static JSONArray queryArraystatic(Map<String, String> map,int posttype) throws Exception {
		// ʹ��Map��װ�������
		// ���巢�������URL
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONArray(HttpUtil.postRequest(url, map, posttype));
	}
	public  JSONArray queryArray(Map<String, String> map,int posttype) throws Exception {
		// ʹ��Map��װ�������
		// ���巢�������URL
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONArray(HttpUtil.postRequest(url, map, posttype));
	}
	//��json file���ݶ�ȡ��ת��
	public static  JSONArray fileArray(String content) throws Exception {		
		return new JSONArray(content);
	}

	// ���巢������ķ���
	public static JSONObject queryObjectStatic(Map<String, String> map,int posttype) throws Exception {
		// ʹ��Map��װ�������
		// ���巢�������URL
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map, posttype));
	}
	// ���巢������ķ���
	public  JSONObject queryObject(Map<String, String> map,int posttype) throws Exception {
		// ʹ��Map��װ�������
		// ���巢�������URL
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map, posttype));
	}		
}
