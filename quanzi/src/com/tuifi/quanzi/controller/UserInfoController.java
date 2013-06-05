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
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuifi.quanzi.db.UserInfoDataHelper;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.model.UserInfos;
import com.tuifi.quanzi.util.HttpUtil;

public class UserInfoController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "UserInfoController";
	public static List<UserInfos> infoList = new ArrayList<UserInfos>();
	public static List<UserInfos> infoListUid = new ArrayList<UserInfos>();
	
	// 从网络去读圈子列表
	public List<UserInfos> readInfoFromJson(Context context, String myuid) {
		// 每次loadlist的时候即出发了修改button的事件
		JSONArray data;
		try {
			// 先清空
			// 使用Map封装请求参数
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "getinfolist");
			map.put("uid", myuid);
			Log.d(LOG, "handleMessage-----readInfoFromJson -uid =" + myuid);

			data = queryArray(map, 0);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Log.w(LOG, "Exception----overtime,URL = " + HttpUtil.BASE_URL);
				return null;
			} else {
				// 清空
				infoList = InputJsonArray(data);
				saveListtoDb(context);
			}// end else

		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, "Exception-----e=" + e.toString());
			return null;
		}
		return infoList;
	}

	public List<UserInfos> getCurrentUserinfo(List<UserInfos> totallist,
			String myuid) {
		List<UserInfos> list = new ArrayList<UserInfos>();
		for (UserInfos ui : totallist) {
			String uid = ui.getuid();
			if (uid.trim().equals(myuid)) {
				list.add(ui);
			}
		}
		return list;
	}

	public List<UserInfos> InputJsonArray(JSONArray data) throws JSONException {
		List<UserInfos> list = new ArrayList<UserInfos>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject d = data.getJSONObject(i);
			if (d != null) {
				String useinfoid = d.getString("user_info_id");
				String uid = d.getString("uid");
				String infoid = d.getString("info_id");
				String datastr = d.getString("datastr");
				String ctime = d.getString("ctime");
				String infoname = d.getString("info_name");
				String infodata = d.getString("info_data");
				String infolevel = d.getString("info_level");

				String time = "";
				try {
					SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
					if (!(ctime.equals("null") || (ctime.equals("")))) {
						Long l = Long.parseLong(ctime);
						time = sd.format(new Date(l));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (list == null) {
					list = new ArrayList<UserInfos>();
				}
				UserInfos w = new UserInfos();
				w.setuseinfoid(useinfoid);
				w.setuid(uid);
				w.setinfoid(infoid);
				w.setdatastr(datastr);
				w.setctime(ctime);
				w.setinfoname(infoname);
				w.setinfodata(infodata);
				w.setinfolevel(infolevel);

				list.add(w);
			}
		}
		return list;
	}

	// 存储
	public void saveListtoDb(Context context) {
		UserInfoDataHelper dbHelper = new UserInfoDataHelper(context);
		dbHelper.ClearInfo();
		for (int i = 0; i < infoList.size(); i++) {
			UserInfos info = infoList.get(i);
			dbHelper.SaveInfo(info);
		}	
		dbHelper.Close();
	}

	public List<UserInfos> loadUiList(Context context, String myuid, int reload) {
		// reload =0 否 realod =1 强制从网络重新读取
		List<UserInfos> list = new ArrayList<UserInfos>();
		// 如果为0，则先从本机数据库读取
		if ((infoList == null) || (infoList.isEmpty())) {
			UserInfoDataHelper dbHelper = new UserInfoDataHelper(context);
			list = dbHelper.GetList(false);
			dbHelper.Close();
			infoList = list;
			Log.d(LOG, "------QuanziDataHelper GetList");
		}
		// 如果本机数据库为空，则从网络读取
		list = readInfoFromJson(context, myuid);
		if (list != null)
			infoList = list;
		Log.d(LOG, "------readFromJson");

		return infoList;
	}
	public static ArrayList <UserInfos> getUserInfoFromUid(String uid)
	{
		ArrayList <UserInfos>  list = new ArrayList <UserInfos> ();
		for(UserInfos ui:infoList)
		{
			if (ui.getuid().equals(uid))
			{
				list.add(ui);
			}
		}
		return list;
	}
}
