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
	//讲json file数据读取并转换
	public static  JSONArray fileArray(String content) throws Exception {		
		return new JSONArray(content);
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
