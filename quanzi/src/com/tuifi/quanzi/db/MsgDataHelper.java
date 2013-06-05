package com.tuifi.quanzi.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tuifi.quanzi.model.MsgInfo;

public class MsgDataHelper extends DataHelper {

	private static String LOG = "MsgDataHelper";
	private static String TB_NAME = "message";

	public MsgDataHelper(Context context) {
		super(context, TB_NAME);
		// TODO Auto-generated constructor stub
	}

	// 获取表中的的记录
	public List<MsgInfo> GetList(Boolean isSimple) {
		List<MsgInfo> userList = new ArrayList<MsgInfo>();

		// Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null, null, MsgInfo.uid+" DESC");
		try {
			Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
					"select * from " + TB_NAME, null);

			Log.i(LOG, LOG + "----------------GetmsgList");
			cursor.moveToFirst();
			while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
				MsgInfo info = new MsgInfo();
				info.setid(cursor.getString(0));
				info.setcontent(cursor.getString(1));			
				info.setsenduid(cursor.getString(2));
				info.setreceiveuid(cursor.getString(3));
				info.setctime(cursor.getString(4));
				info.setqzid(cursor.getString(5));
				info.sethdid(cursor.getString(6));			
				info.settype(cursor.getString(7));
				userList.add(info);
				Log.i(LOG, LOG + "add info=" + info.getid());
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return userList;
	}

	// 添加表的记录
	public Long SaveInfo(MsgInfo info) {
		ContentValues values = new ContentValues();
		values.put(MsgInfo.ID, info.getid());
		values.put(MsgInfo.CONTENT, info.getcontent());
		values.put(MsgInfo.TYPE, info.gettype());
		values.put(MsgInfo.SENDUID, info.getsenduid());
		values.put(MsgInfo.RECEIVEUID, info.getreceiveuid());
		values.put(MsgInfo.QZID, info.getqzid());
		values.put(MsgInfo.HDID, info.gethdid());
		values.put(MsgInfo.CTIME, info.getctime());
		Long uid = db.insert(TB_NAME, MsgInfo.ID, values);
		Log.i(LOG, "SaveInfo " + uid + "");
		return uid;
	}

	// 删除users表的记录
	public int DelUserInfo(String Id) {
		int id = db.delete(TB_NAME, MsgInfo.ID + "=" + Id, null);
		Log.i(LOG, "DelUserInfo " + id + "");
		return id;
	}
}
