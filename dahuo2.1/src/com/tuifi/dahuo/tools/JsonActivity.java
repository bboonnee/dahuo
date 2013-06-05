package com.tuifi.dahuo.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tuifi.dahuo.R;

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


	// ���巢������ķ���
	public JSONArray queryArray(Map<String, String> map,int posttype) throws Exception {
		// ʹ��Map��װ�������
		// ���巢�������URL
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONArray(HttpUtil.postRequest(url, map, posttype));
	}

	// ���巢������ķ���
	public JSONObject queryObject(Map<String, String> map,int posttype) throws Exception {
		// ʹ��Map��װ�������
		// ���巢�������URL
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map, posttype));
	}

	
	// ��ȡ�汾����
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
