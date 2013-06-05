package com.tuifi.quanzi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.Toast;

import com.tuifi.quanzi.db.UserHelper;
import com.tuifi.quanzi.model.User;
import com.tuifi.quanzi.util.Download;

public class UserController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "UserController";
	public static List<User> userList = new ArrayList<User>();

	public static List<User> readUserFromJson(Context context, String userid) {
		// ÿ��loadlist��ʱ�򼴳������޸�button���¼�
		List<User> list = new ArrayList<User>();
		JSONArray data;
		try {
			// ʹ��Map��װ�������
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "getuserlist");
			map.put("uid", userid);
			data = queryArraystatic(map, 0);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Toast.makeText(context, "���ӳ�ʱ ���������磬�Ժ�ˢ�£�", Toast.LENGTH_SHORT)
						.show();
				return null;
			} else {
				// ���

				for (int i = 0; i < data.length(); i++) {
					JSONObject d = data.getJSONObject(i);
					if (d != null) {
						String uid = d.getString("uid");
						String uname = d.getString("uname");
						String email = d.getString("email");
						String mobile = d.getString("mobile");
						String password = d.getString("password");
						String imageurl = d.getString("imageurl");

						String ctime = d.getString("ctime");

						if (list == null) {
							list = new ArrayList<User>();
						}
						User w = new User();
						w.setuid(uid);
						w.setuname(uname);
						w.setemail(email);
						w.setMobile(mobile);
						w.setpassword(password);
						w.setctime(ctime);
						w.setimageurl(imageurl);

						list.add(w);
					}
				}
				Log.i(LOG, "User list readFromJson success" + list.size());
				saveUserListtoDb(context, list);
			}// end else

		} catch (Exception e) {
			// DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
			Log.w(LOG, e.toString());
			return null;
		}
		return list;
	}

	public static List<User> downUserIcon(Context context, List<User> list) {
		List<User> relist = new ArrayList<User>();
		UserHelper dbHelper = new UserHelper(context);
		try {
			for (User user : list) {
				Bitmap userIcon = null;
				String imageurl = user.getimageurl();
				if ((imageurl != null) && (!imageurl.trim().equals(""))) {
					userIcon = new Download().DownloadImg(imageurl);
				}
				if (userIcon != null) {
					BitmapDrawable bd = new BitmapDrawable(userIcon);
					user.setUserIcon(bd);
					dbHelper.UpdateUserIcon(userIcon, user.getuid());
				}else
				{
					Log.e(LOG, "downUserIcon is null user:" + user.getuid());
				}
				relist.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG, "downUserIcon " + e.toString());
		} finally {
			dbHelper.Close();
		}
		return relist;
	}

	// �洢user list
	public static void saveUserListtoDb(Context context, List<User> list) {
		UserHelper dbHelper = new UserHelper(context);
		dbHelper.ClearInfo();
		for (int i = 0; i < list.size(); i++) {
			User ui = list.get(i);
			dbHelper.SaveUserInfo(ui);
		}
		Log.i(LOG, "saveUserListtoDb success");
		int imagecount = 0;

		Log.i(LOG, "UpdateUserIcon to db success imagecount =" + imagecount);
		dbHelper.Close();
	}

	// ͨ��mobileme���myuid
	public static String getMyuid(Context context, String mobile) {

		String uid = "";
		if (userList == null) {
			userList = loadList(context, "", 0);
		}
		if (userList != null) {
			for (User user : userList) {
				if (user.getMobile().equals(mobile)) {
					uid = user.getuid();
					SharedPreferences preferences = context
							.getSharedPreferences("quanziinfo",
									context.MODE_PRIVATE);
					Editor edit = preferences.edit();
					edit.putString("myuid", uid);
					edit.commit();
					break;
				}
			}
		}
		Log.i(LOG, "getMyuid " + mobile + " " + uid);
		return uid;

	}

	// ͨ��mobileme���myuid
	public static String getMyuid(String mobile) {

		String uid = "";
		if (userList != null) {
			for (User user : userList) {
				if (user.getMobile().equals(mobile)) {
					uid = user.getuid();
					break;
				}
			}
		}
		Log.i(LOG, "getMyuid " + mobile + " " + uid);
		return uid;

	}

	// ͨ��ĳ�ֶβ��������ֶ�
	public static String getNameFromUid(String uid) {

		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				if (user.getuid().equals(uid)) {
					return user.getuname();
				}
			}
		}
		return null;
	}

	public static List<User> loadList(Context context, String myuid, int reload) {
		// reload =0 �� realod =1 ǿ�ƴ��������¶�ȡ
		List<User> listjson = new ArrayList<User>();
		// ���Ϊ�գ����ȴӱ������ݿ��ȡ

		Log.i(LOG, "userList size" + userList.size());
		if ((userList == null) || (userList.isEmpty())) {
			userList = readuserFromDB(context);
			Log.d(LOG, "-----user- GetList from db");
		}
		// ����������ݿ�Ϊ�գ���������ȡ
		if ((userList == null) || (userList.isEmpty()) || (reload == 1)) {
			listjson = readUserFromJson(context, myuid);
			Log.d(LOG, "-----user- GetList readUserFromJson");
			// �����������¶�ȡ���������Ǵ����ݿ��ȡ
		}
		if ((listjson != null) && (!listjson.isEmpty())) {
			userList = listjson;
		}
		Log.d(LOG, "---user---loadList");
		return userList;
	}

	public static List<User> readuserFromDB(Context context) {
		List<User> list = new ArrayList<User>();
		UserHelper dbHelper = new UserHelper(context);
		try {
			list = dbHelper.GetUserList(false);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG, "user loadList error" + e.toString());
		} finally {
			dbHelper.Close();
		}
		return list;

	}

	public static String readUidFromMobile(String mobile) {
		//
		String result = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "getuidfrommobile");
			map.put("mobile", mobile);
			JSONObject jb = queryObjectStatic(map, 1);
			result = jb.getString("uid");
			if (result.equals("overtime")) {
				return "overtime";
			}
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
		return result;
	}

	public static User getUserItemFromUid(String uid) {
		for (User u : userList) {
			if (u.getuid().equals(uid))
				return u;
		}
		return null;
	}
}
