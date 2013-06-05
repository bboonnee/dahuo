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

import com.tuifi.quanzi.db.AffairsDataHelper;
import com.tuifi.quanzi.model.AffairsInfo;
import com.tuifi.quanzi.model.User;
import com.tuifi.quanzi.util.HttpUtil;

public class AffairsController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "MsgController";
	public static List<AffairsInfo> AffairsList = new ArrayList<AffairsInfo>();

	// 从网络去读列表
	public List<AffairsInfo> readFromJson(Context context) {
		//
		JSONArray data;
		try {
			// 使用Map封装请求参数
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "getAffairs");
			Log.d(LOG, LOG + "-----readFromJson");

			data = queryArray(map, 0);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Log.w(LOG, "Exception----overtime,URL = " + HttpUtil.BASE_URL);
				return null;
			} else {
				// 清空
				AffairsList = InputJsonArray(data);
				saveListtoDb(context);
			}// end else

		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, "Exception-----e=" + e.toString());
			return null;
		}
		return AffairsList;
	}

	public List<AffairsInfo> InputJsonArray(JSONArray data) throws JSONException {
		List<AffairsInfo> list = new ArrayList<AffairsInfo>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject d = data.getJSONObject(i);
			if (d != null) {
				String id = d.getString("id");
				String aid = d.getString("aid");
				String name = d.getString("name");
				String deadtime = d.getString("deadtime");
				String crawltime = d.getString("crawltime");
				String content = d.getString("content");
				String url = d.getString("url");				
				String likenum = d.getString("likenum");
				String sourcesite = d.getString("sourcesite");				
				String quanzi = d.getString("quanzi");

				String ctime = "";
				try {
					SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
					if (!(deadtime.equals("null") || (deadtime.equals("")))) {
						Long l = Long.parseLong(deadtime);
						ctime = sd.format(new Date(l));
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(LOG, "InputJsonArray " + e.toString());
				}
				if (list == null) {
					list = new ArrayList<AffairsInfo>();
				}
				AffairsInfo w = new AffairsInfo();
				w.setid(id);
				w.setaid(aid);
				w.setName(name);
				w.setdeadtime(deadtime);
				w.setcrawltime(crawltime);
				w.setcontent(content);
				w.seturl(url);
				w.setlikenum(likenum);
				w.setsourcesite(sourcesite);
				w.setquanzi(quanzi);
			
				list.add(w);
			}
		}
		return list;
	}

	public List<AffairsInfo> loadList(Context context, int reload) {
		// reload =0 否 realod =1 强制从网络重新读取
		List<AffairsInfo> List = new ArrayList<AffairsInfo>();

		// 先从本机数据库读取
		if ((AffairsList == null) || (AffairsList.isEmpty())) {
			AffairsDataHelper dbHelper = new AffairsDataHelper(context);
			List = dbHelper.GetList(false);
			dbHelper.Close();
			Log.d(LOG, "---Affairs Controller-- GetList");
			AffairsList = List;
		}
		// 从网络读取，更新本地数据库

			List = readFromJson(context);			
			if (List != null)
				AffairsList = List;
			Log.d(LOG, "----Affairs Controller--readFromJson");
		
		
		
		return AffairsList;
	}

	// 存储user list
	public void saveListtoDb(Context context) {
		AffairsDataHelper dbHelper = new AffairsDataHelper(context);
		dbHelper.ClearInfo();
		for (int i = 0; i < AffairsList.size(); i++) {
			AffairsInfo info = AffairsList.get(i);
			dbHelper.SaveInfo(info);
		}
		dbHelper.Close();
	}

	// 存储user list
	public static AffairsInfo getAffairsFromId(String  aid) {
		for (AffairsInfo ai : AffairsList) {
			if (ai.getaid().equals(aid))
				return ai;
		}
		return null;
	}	

}
