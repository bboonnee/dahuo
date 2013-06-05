package com.tuifi.dahuo.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.tuifi.dahuo.model.Msg;
import com.tuifi.dahuo.model.User;
import com.tuifi.dahuo.tools.MD5Utils;

public class LoginController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "LoginController";		

	public static User register(String mobile, String password,
			String realname,String usertype) {
		//
		User user = new User();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "register");
			map.put("mobile", mobile);
			map.put("password", password);
			map.put("name", realname);				
			map.put("type", usertype);
			JSONObject jb = queryObjectStatic(map, 1);
			user = convertJSONObj(jb);			
			if (user.result.equals("overtime")) {
				return null;
			}
		} catch (Exception e) {
			//
			e.printStackTrace();
			Log.e(LOG, e.toString());
		}
		return user;
	}
	
	public static User login(String mobile, String password) {
		//
		User user = new User();		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "login");
			map.put("mobile", mobile);				
			JSONObject jb = queryObjectStatic(map, 1);			
			user = convertJSONObj(jb);						

		} catch (Exception e) {
			//
			e.printStackTrace();
		}
		return user;
	}	
	public static User convertJSONObj(JSONObject d) {
		User w = new User();
		try {			
			if (d != null) {	
				w.result = d.getString("result");
				w.id = d.getString("id");
				w.name = d.getString("name");
				w.mobile = d.getString("mobile");
				w.password = d.getString("password");		
				w.result = d.getString("result"); 				
				w.type = d.getString("type");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w;
	}
}
