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

import com.tuifi.quanzi.db.QuanziDataHelper;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.util.HttpUtil;

public class QuanziController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "QuanziController";
	public static List<QuanziInfo> qzList = new ArrayList<QuanziInfo>();

	// 从网络去读圈子列表
	public List<QuanziInfo> readQzFromJson(Context context, String myuid) {
		// 每次loadlist的时候即出发了修改button的事件
		JSONArray data;
		try {
			// 使用Map封装请求参数
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "getquanzilist");
			map.put("uid", myuid);				
			Log.d(LOG, "handleMessage-----readQuanziFromJson -uid =" + myuid);

			data = queryArray(map, 0);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Log.w(LOG, "Exception----overtime,URL = " + HttpUtil.BASE_URL);
				return null;
			} else {
				// 清空
				Log.d(LOG, "handleMessage-----InputJsonArray ");
				qzList = InputJsonArray(data);
				setQuanzi();
				saveListtoDb(context);
			}// end else

		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, "Exception-----e=" + e.toString());
			return null;
		}
		return qzList;
	}

	public List<QuanziInfo> InputJsonArray(JSONArray data) throws JSONException {
		List<QuanziInfo> list = new ArrayList<QuanziInfo>();
		for (int i = 0; i < data.length(); i++) {
			JSONObject d = data.getJSONObject(i);
			if (d != null) {
				String qz_id = d.getString("qz_id");
				String name = d.getString("name");
				String fatherid = d.getString("fatherid");
				String description = d.getString("description");
				int type = d.getInt("type");
				String qz_authority = d.getString("authority");
				int authority = 0;
				try {
					authority = Integer.parseInt(qz_authority);
				} catch (Exception e) {
					e.printStackTrace();
				}
				switch (authority) {
				case 1:
					qz_authority = "固定人员";
					break;
				case 2:
					qz_authority = "圈内人员1/5认证";
					break;
				case 3:
					qz_authority = "自由加入";
					break;
				default:
					qz_authority = "固定人员";
					break;

				}

				String qz_detail = d.getString("detail");
				String usernum = d.getString("usernum");
				String detail = qz_detail;
				String createtime = d.getString("ctime");
				String finishtime = d.getString("ftime");
				String createruid = d.getString("cuid");
				// String cuid = getNameFromUid(createruid);
				String cuid = createruid;
				String ctime = "";
				String ftime = "";
				try {
					SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
					if (createtime != null) {
						Long l = Long.parseLong(createtime);
						ctime = sd.format(new Date(l));
					}
					if (finishtime != null) {
						Long l = Long.parseLong(finishtime);
						ftime = sd.format(new Date(l));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				// Date startDate = new Date(dateTime);

				if (list == null) {
					list = new ArrayList<QuanziInfo>();
				}
				QuanziInfo w = new QuanziInfo();
				w.setctime(ctime);
				w.setcuid(cuid);
				w.setftime(finishtime);
				w.setID(qz_id);
				w.setname(name);
				w.setfatherid(fatherid);
				w.setauthority(qz_authority);
				w.setdescription(description);
				String qztype = Integer.toString(type);
				w.settype(qztype);
				w.setdetail(detail);
				w.setusernum(usernum);
				list.add(w);
			}
		}
		return list;
	}

	// 设置圈子数据信息
	public void setQuanzi() {
		if (qzList != null)
			for (QuanziInfo qs : qzList) {
				qs.setfathername(ReadQuanziName(qs.getfatherid()));
				String detail = " 圈子：" + qs.getdescription() + "  \n 宗旨："
						+ qs.getdetail() + "　\n 圈子成员：　" + qs.getusernum()
						+ "人  \n 加入方式：" + qs.getauthority() + "  \n 父圈子："
						+ qs.getfathername();
				qs.setdetail(detail);
			}
	}

	// 通过圈子id获取圈子名称
	public String ReadQuanziName(String id) {
		for (QuanziInfo qs : qzList) {
			if (qs.id.equals(id)) {

				return qs.name;
			}
		}
		return "";
	}

	// 存储user list
	public void saveListtoDb(Context context) {
		QuanziDataHelper dbHelper = new QuanziDataHelper(context);
		dbHelper.ClearInfo();
		Log.d(LOG, "------saveListtoDb qzListsize=" + qzList.size());
		for (int i = 0; i < qzList.size(); i++) {
			QuanziInfo info = qzList.get(i);
			dbHelper.SaveInfo(info);
		}		
			dbHelper.Close();
	}

	public List<QuanziInfo> loadqzList(Context context, String myuid, int reload) {
		// reload =0 否 realod =1 强制从网络重新读取
		List<QuanziInfo> list = new ArrayList<QuanziInfo>();
		// 如果为空，则为第一次登录，先从本机数据库读取
		if ((qzList == null) || (qzList.isEmpty())) {
			QuanziDataHelper dbHelper = new QuanziDataHelper(context);
			list = dbHelper.GetList(false);			
				dbHelper.Close();
			qzList = list;
			Log.d(LOG, "------QuanziDataHelper GetList");
		}

		// 然后从网络读取,并存储数据库
		if ((qzList == null) || (qzList.isEmpty()) || (reload == 1)) {
			list = readQzFromJson(context, myuid);
			Log.d(LOG, "-----loadqzList-readFromJson");
		}
		
		//如果网络数据不为空，则赋值
		if ((list != null) && (!list.isEmpty())) {
			qzList = list;
		}	

		return qzList;
	}
}
