package com.tuifi.quanzi.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class NotifyController extends ModelController implements Serializable{
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "NotifyController";
	public boolean clearNotifyNumber(String myuid) {
		JSONObject jsonObj;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "clearNotifyNumber");
			map.put("uid", myuid);			
			jsonObj = queryObject(map, 0);			
			// 如果userId 大于0
			int number = jsonObj.getInt("result");
			if (number > 0) {
				return true;
			}
		} catch (Exception e) {
			//
			// Toast.makeText(getApplicationContext(), "服务器响应异常，请稍后再试！",
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return false;
	}
	

}
