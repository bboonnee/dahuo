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
			// ���userId ����0
			int number = jsonObj.getInt("result");
			if (number > 0) {
				return true;
			}
		} catch (Exception e) {
			//
			// Toast.makeText(getApplicationContext(), "��������Ӧ�쳣�����Ժ����ԣ�",
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return false;
	}
	

}
