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

import com.tuifi.quanzi.db.MsgDataHelper;
import com.tuifi.quanzi.model.MsgInfo;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.util.HttpUtil;

public class MsgController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "MsgController";
	public static List<MsgInfo> MsgList = new ArrayList<MsgInfo>();

	// 从网络去读列表
	public List<MsgInfo> readMsgFromJson(Context context, String uid,
			List<QuanziInfo> qzList) {
		//
		JSONArray data;
		try {
			// 使用Map封装请求参数
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "getmsglist");
			map.put("uid", uid);
			String qzid = "";
			if (qzList != null)
				for (int i = 0; i < qzList.size(); i++) {
					QuanziInfo qi = qzList.get(i);
					if (i == qzList.size() - 1)
						qzid = qzid + qi.getID();
					else
						qzid = qzid + qi.getID() + ",";
				}
			map.put("qzid", qzid);
			Log.d(LOG, LOG + "-----readMsgFromJson -uid =" + uid + " qzid="
					+ qzid);
			data = queryArray(map, 0);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Log.w(LOG, "Exception----overtime,URL = " + HttpUtil.BASE_URL);
				return null;
			} else {
				// 清空
				MsgList = InputJsonArray(data);
				saveListtoDb(context);
			}// end else

		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, "Exception-----e=" + e.toString());
			return null;
		}
		return MsgList;
	}
	// 从网络去读列表
		public List<MsgInfo> readChatMsgFromJson(Context context, String uid,
				String qzid,String last) {
			//
			JSONArray data;
			try {
				// 使用Map封装请求参数
				Map<String, String> map = new HashMap<String, String>();
				map.put("do", "getchatmsglist");
				map.put("uid", uid);
				map.put("qzid", qzid);
				map.put("last", last);
				Log.d(LOG, LOG + "-----readMsgFromJson -uid =" + uid + " qzid="
						+ qzid);
				data = queryArray(map, 0);
				String t = data.getString(0);
				if (t.equals("overtime")) {
					Log.w(LOG, "Exception----overtime,URL = " + HttpUtil.BASE_URL);
					return null;
				} else {
					// 清空
					MsgList = InputJsonArray(data);
					saveListtoDb(context);
				}// end else

			} catch (Exception e) {
				// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
				e.printStackTrace();
				Log.w(LOG, "Exception-----e=" + e.toString());
				return null;
			}
			return MsgList;
		}

	public List<MsgInfo> InputJsonArray(JSONArray data) throws JSONException {
		List<MsgInfo> list = new ArrayList<MsgInfo>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject d = data.getJSONObject(i);
			if (d != null) {
				String id = d.getString("id");
				String content = d.getString("content");
				String type = d.getString("type");
				String senduid = d.getString("send_uid");
				String sendUser = UserController.getNameFromUid(senduid);
				String receiveuid = d.getString("receive_uid");
				String qzid = d.getString("qz_id");
				String hdid = d.getString("hd_id");
				String time = d.getString("ctime");
				String ctime = "";
				try {
					SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
					if (!(time.equals("null") || (time.equals("")))) {
						Long l = Long.parseLong(time);
						ctime = sd.format(new Date(l));
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(LOG, "InputJsonArray " + e.toString());
				}
				if (list == null) {
					list = new ArrayList<MsgInfo>();
				}
				MsgInfo w = new MsgInfo();
				w.setid(id);
				w.setcontent(content);
				w.settype(type);
				w.setsenduid(senduid);
				w.setreceiveuid(receiveuid);
				w.setqzid(qzid);
				w.sethdid(hdid);
				w.setctime(ctime);
				list.add(w);
			}
		}
		return list;
	}

	public List<MsgInfo> loadList(Context context, String uid,
			List<QuanziInfo> qzList, int reload) {
		// reload =0 否 realod =1 强制从网络重新读取
		List<MsgInfo> List = new ArrayList<MsgInfo>();

		// 先从本机数据库读取
		if ((MsgList == null) || (MsgList.isEmpty())) {
			MsgDataHelper dbHelper = new MsgDataHelper(context);
			List = dbHelper.GetList(false);
			dbHelper.Close();
			Log.d(LOG, "----MsgController-- GetList "+List.size());
			MsgList = List;
		}
		// 从网络读取，更新本地数据库
		List = readMsgFromJson(context, uid, qzList);
		Log.d(LOG, "----MsgController--readMsgFromJson"+List.size());
		if (List != null)
			MsgList = List;
		return MsgList;
	}
	public List<MsgInfo> loadChatList(Context context, String uid,
			String qzid, String last,int reload) {
		// reload =0 否 realod =1 强制从网络重新读取
		List<MsgInfo> List = new ArrayList<MsgInfo>();

		// 先从本机数据库读取
		if ((MsgList == null) || (MsgList.isEmpty())) {
			MsgDataHelper dbHelper = new MsgDataHelper(context);
			List = dbHelper.GetList(false);
			dbHelper.Close();
			Log.d(LOG, "----MsgController-- GetList "+List.size());
			MsgList = List;
		}
		// 从网络读取，更新本地数据库
		List = readChatMsgFromJson(context, uid, qzid,last);
		Log.d(LOG, "----MsgController--readMsgFromJson"+List.size());
		if (List != null)
			MsgList = List;
		return MsgList;
	}
	// 存储user list
	public void saveListtoDb(Context context) {
		MsgDataHelper dbHelper = new MsgDataHelper(context);
		dbHelper.ClearInfo();
		for (int i = 0; i < MsgList.size(); i++) {
			MsgInfo info = MsgList.get(i);
			dbHelper.SaveInfo(info);
		}
		dbHelper.Close();
	}

	public static List<MsgInfo> loadquanzimsg(String qzid) {
		List<MsgInfo> list = new ArrayList<MsgInfo>();
		for (MsgInfo ms : MsgList) {
			if (ms.qzid.equals(qzid)) {
				list.add(ms);
			}
		}
		return list;
	}

}
