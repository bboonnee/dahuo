package com.tuifi.dahuo.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tuifi.dahuo.model.Msg;

public class MsgController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "UserController";
	public static List<Msg> MsgList;
	
	// search
	public static List<Msg> getmsglistFromJson(Context context,String startadd,String endadd) {
		// 每次loadlist的时候即出发了修改button的事件
		Log.d(LOG, "getmsglistFromJson");
		List<Msg> list = new ArrayList<Msg>();
		JSONArray data;
		try {
			// 使用Map封装请求参数
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "getmsglist");
			if(startadd!=null)
			map.put("startadd", startadd);
			if(endadd!=null)
			map.put("endadd", endadd);			
			
			data = queryArraystatic(map, 0);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				// Toast.makeText(context, "连接超时 ，请检查网络，稍后刷新！",
				// Toast.LENGTH_SHORT).show();
				return null;
			} else {
				// 清空
				for (int i = 0; i < data.length(); i++) {
					JSONObject d = data.getJSONObject(i);
					Msg w = convertJSONObj(d);
					list.add(w);
				}
				Log.i(LOG, "User list searchFromJson success" + list.size());
				// saveUserstoDb(context, list);
			}// end else

		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, e.toString());
			return null;
		}
		return list;
	}
//
	// search
		public static List<Msg> getInfoshoplistFromJson(Context context,String startadd,String endadd) {
			// 每次loadlist的时候即出发了修改button的事件
			Log.d(LOG, "getInfoshoplistFromJson");
			List<Msg> list = new ArrayList<Msg>();
			JSONArray data;
			try {
				// 使用Map封装请求参数
				Map<String, String> map = new HashMap<String, String>();
				map.put("do", "getshoplist");
				if(startadd!=null)
				map.put("startadd", startadd);
				if(endadd!=null)
				map.put("endadd", endadd);			
				
				data = queryArraystatic(map, 0);
				String t = data.getString(0);
				if (t.equals("overtime")) {
					// Toast.makeText(context, "连接超时 ，请检查网络，稍后刷新！",
					// Toast.LENGTH_SHORT).show();
					return null;
				} else {
					// 清空
					for (int i = 0; i < data.length(); i++) {
						JSONObject d = data.getJSONObject(i);
						Msg w = convertJSONObj(d);
						list.add(w);
					}
					Log.i(LOG, "User list searchFromJson success" + list.size());
					// saveUserstoDb(context, list);
				}// end else

			} catch (Exception e) {
				// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
				e.printStackTrace();
				Log.w(LOG, e.toString());
				return null;
			}
			return list;
		}
	//
	public static String savemsgFromJson(Context context,Msg msg) {
		// 每次loadlist的时候即出发了修改button的事件
		Log.d(LOG, "savemsgFromJson");		
		String result = null;
		try {
			String jsonstr = msg.toJSON();
			// 使用Map封装请求参数
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "savemsg");
			map.put("startadd", msg.getstartadd());			
			map.put("endadd", msg.getendadd());
			map.put("remark", msg.getremark());
			map.put("weight", msg.getweight());
			map.put("deadline", msg.getdeadline());
			
			JSONObject jb = queryObjectStatic(map, 99);
			result = jb.getString("result");
			if (result.equals("overtime")) {
				return "overtime";
			}
		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, e.toString());			
		}
		return result;		
	}

	public static Msg convertJSONObj(JSONObject d) {
		try {
			if (d != null) {
				String id = d.getString("id");
				String uid = d.getString("uid");
				String startadd = d.getString("startadd");
				String starttime = d.getString("starttime");
				String endadd = d.getString("endadd");
				String endtime = d.getString("endtime");
				String deadline = d.getString("deadline");
				String sendtime = d.getString("sendtime");
				String msgtype = d.getString("msgtype");
				String cartype = d.getString("cartype");

				String middlecity = d.getString("middlecity");
				String twoway = d.getString("twoway");
				String remark = d.getString("remark");
				String weight = d.getString("weight");
				String price = d.getString("price");
				String carlength = d.getString("carlength");
				String carno = d.getString("carno");
				
				
				Msg w = new Msg();
				w.setId(id);
				w.setuid(uid);
				w.setstartadd(startadd);
				w.setstarttime(starttime);
				w.setendadd(endadd);
				w.setendtime(endtime);
				w.setdeadline(deadline);
				w.setsendtime(sendtime);
				w.setmsgtype(msgtype);
				w.setcartype(cartype);
				w.setmiddlecity(middlecity);
				w.settwoway(twoway);
				w.setremark(remark);
				w.setweight(weight);
				w.setprice(price);
				w.setcarlength(carlength);
				w.setcarno(carno);											
				return w;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

