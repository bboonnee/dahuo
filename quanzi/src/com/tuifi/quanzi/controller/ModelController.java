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
	
	// 定义发送请求的方法
	public static JSONArray queryArraystatic(Map<String, String> map,int posttype) throws Exception {
		// 使用Map封装请求参数
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONArray(HttpUtil.postRequest(url, map, posttype));
	}
	public  JSONArray queryArray(Map<String, String> map,int posttype) throws Exception {
		// 使用Map封装请求参数
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONArray(HttpUtil.postRequest(url, map, posttype));
	}

	// 定义发送请求的方法
	public static JSONObject queryObjectStatic(Map<String, String> map,int posttype) throws Exception {
		// 使用Map封装请求参数
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map, posttype));
	}
	// 定义发送请求的方法
	public  JSONObject queryObject(Map<String, String> map,int posttype) throws Exception {
		// 使用Map封装请求参数
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map, posttype));
	}		
}
