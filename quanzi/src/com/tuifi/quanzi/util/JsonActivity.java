package com.tuifi.quanzi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tuifi.quanzi.R;
import com.tuifi.quanzi.util.HttpUtil;

public class JsonActivity extends Activity {
	public static List<Activity> activityList = new ArrayList<Activity>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityList.add(this);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		activityList.remove(this);

	}

	public static void killMyProcess() {

		for (Activity activity : activityList) {
			activity.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());

	}


	// 定义发送请求的方法
	public JSONArray queryArray(Map<String, String> map,int posttype) throws Exception {
		// 使用Map封装请求参数
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONArray(HttpUtil.postRequest(url, map, posttype));
	}

	// 定义发送请求的方法
	public JSONObject queryObject(Map<String, String> map,int posttype) throws Exception {
		// 使用Map封装请求参数
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map, posttype));
	}

	
	// 获取版本代码
	public static String getVerName(Context context) {
		String verName = context.getResources()
				.getText(R.string.app_versionName).toString();
		return verName;
	}

	public static String getAppName(Context context) {
		String verName = context.getResources().getText(R.string.app_name)
				.toString();
		return verName;
	}
}
