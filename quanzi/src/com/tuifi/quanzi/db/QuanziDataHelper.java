package com.tuifi.quanzi.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tuifi.quanzi.model.QuanziInfo;

public class QuanziDataHelper extends DataHelper {
	private static String LOG = "QuanziDataHelper";
	private static String TBNAME = "quanzi";

	public QuanziDataHelper(Context context) {
		super(context, TBNAME);
	}

	// 获取表中的的记录
	public List<QuanziInfo> GetList(Boolean isSimple) {
		List<QuanziInfo> list = new ArrayList<QuanziInfo>();
		// Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null,
		// null, QuanziInfo.uid+" DESC");
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + TB_NAME+" order by id desc", null);
		Log.i(LOG, LOG + "----------------GetquanziList");
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			QuanziInfo info = new QuanziInfo();
			info.setID(cursor.getString(0));
			info.setname(cursor.getString(1));
			info.setfatherid(cursor.getString(2));
			String fathername = cursor.getString(3);
			info.setdescription(cursor.getString(4));
			info.setauthority(cursor.getString(5));
			info.settype(cursor.getString(6));
			info.setdetail(cursor.getString(7));
			info.setctime(cursor.getString(8));
			info.setcuid(cursor.getString(9));
			info.setftime(cursor.getString(10));
			info.setusernum(cursor.getString(11));
			list.add(info);
			Log.i(LOG, LOG + "add info=" + info.getID());
			cursor.moveToNext();
		}
		cursor.close();
		return list;
	}

	// 判断users表中的是否包含某个UserID的记录
	public Boolean HaveInfo(String Id) {
		Boolean b = false;
		Cursor cursor = db.query(TB_NAME, null, QuanziInfo.ID + "=" + Id, null,
				null, null, null);
		b = cursor.moveToFirst();

		Log.i(LOG, "HaveUserInfo " + b.toString());

		cursor.close();
		return b;
	}

	// 更新users表的记录
	public int UpdateUserInfo(QuanziInfo info) {
		ContentValues values = new ContentValues();
		values.put(QuanziInfo.ID, info.getID());

		int id = db.update(TB_NAME, values, QuanziInfo.ID + "=" + info.getID(),
				null);
		Log.i(LOG, "UpdateUserInfo " + id + "");
		return id;
	}

	// 添加表的记录
	public Long SaveInfo(QuanziInfo info) {
		ContentValues values = new ContentValues();
		values.put(QuanziInfo.ID, info.getID());
		values.put(QuanziInfo.NAME, info.getname());
		values.put(QuanziInfo.FATHERID, info.getfatherid());
		values.put(QuanziInfo.DESCRIPTION, info.getdescription());
		values.put(QuanziInfo.AUTHORITY, info.getauthority());
		values.put(QuanziInfo.TYPE, info.gettype());
		values.put(QuanziInfo.DETAIL, info.getdetail());
		values.put(QuanziInfo.CTIME, info.getctime());
		values.put(QuanziInfo.CUID, info.getcuid());
		values.put(QuanziInfo.FTIME, info.getftime());
		values.put(QuanziInfo.USERNUM, info.getusernum());
		Long rid = db.insert(TB_NAME, QuanziInfo.ID, values);
		Log.i(LOG, "SaveInfo info.getID() " + info.getID() + "");
		Log.i(LOG, "SaveInfo " + rid + "");
		return rid;
	}

	// 删除users表的记录
	public int DelUserInfo(String Id) {
		int id = db.delete(TB_NAME, QuanziInfo.ID + "=" + Id, null);
		Log.i(LOG, "DelUserInfo " + id + "");
		return id;
	}

}
