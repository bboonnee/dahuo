package com.tuifi.quanzi.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tuifi.quanzi.db.QuanziDataHelper;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.util.HttpUtil;

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
