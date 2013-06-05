package com.tuifi.quanzi.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tuifi.quanzi.model.AffairsInfo;

public class AffairsDataHelper extends DataHelper {

	private static String LOG = "AffairsDataHelper";
	private static String TB_NAME = "affairs";

	public AffairsDataHelper(Context context) {
		super(context, TB_NAME);
		// TODO Auto-generated constructor stub
	}

	// 获取表中的的记录
	public List<AffairsInfo> GetList(Boolean isSimple) {
		List<AffairsInfo> userList = new ArrayList<AffairsInfo>();

		// Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null, null, AffairsInfo.uid+" DESC");
		try {
			Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
					"select * from " + TB_NAME+" order by deadtime desc", null);

			Log.i(LOG, LOG + "----------------GetmsgList");
			cursor.moveToFirst();
			while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
				AffairsInfo info = new AffairsInfo();
				info.setid(cursor.getString(0));
				info.setaid(cursor.getString(1));
				info.setName(cursor.getString(2));
				info.setdeadtime(cursor.getString(3));
				info.setcrawltime(cursor.getString(4));
				info.setcontent(cursor.getString(5));
				info.seturl(cursor.getString(6));
				info.setlikenum(cursor.getString(7));
				info.setsourcesite(cursor.getString(8));
				info.setquanzi(cursor.getString(9));				
				userList.add(info);
				Log.i(LOG, LOG + "add Affairs info=" + info.getid());
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return userList;
	}

	// 添加表的记录
	public Long SaveInfo(AffairsInfo info) {
		ContentValues values = new ContentValues();
		values.put(AffairsInfo.ID, info.getid());
		values.put(AffairsInfo.AID, info.getaid());
		values.put(AffairsInfo.NAME, info.getName());
		values.put(AffairsInfo.DEADTIME, info.getdeadtime());
		values.put(AffairsInfo.CRAWLTIME, info.getcrawltime());
		values.put(AffairsInfo.CONTENT, info.getcontent());
		values.put(AffairsInfo.URL, info.geturl());
		values.put(AffairsInfo.LIKENUM, info.getlikenum());
		values.put(AffairsInfo.SOURCESITE, info.getsourcesite());
		values.put(AffairsInfo.QUANZI, info.getquanzi());		
		Long uid = db.insert(TB_NAME, AffairsInfo.ID, values);
		Log.i(LOG, "SaveAffairsInfo " + uid + "");
		return uid;
	}

	// 删除users表的记录
	public int DelUserInfo(String Id) {
		int id = db.delete(TB_NAME, AffairsInfo.ID + "=" + Id, null);
		Log.i(LOG, "DelAffairsInfo " + id + "");
		return id;
	}
}
