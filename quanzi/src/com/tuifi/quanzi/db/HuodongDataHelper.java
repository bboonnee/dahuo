package com.tuifi.quanzi.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tuifi.quanzi.model.HuodongInfo;

public class HuodongDataHelper extends DataHelper {

	private static String LOG = "HuodongDataHelper";
    private static String TB_NAME = "huodong";
    
	public HuodongDataHelper(Context context) {
		super(context, TB_NAME);
		// TODO Auto-generated constructor stub
	}
    
    //获取表中的的记录
    public List<HuodongInfo> GetList(Boolean isSimple)
    {
        List<HuodongInfo> userList = new ArrayList<HuodongInfo>();
        //Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null, null, HuodongInfo.uid+" DESC");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from "+TB_NAME, null);
        Log.i(LOG, LOG+"----------------GethuodongList");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()&& (cursor.getString(1)!=null)){
            HuodongInfo info=new HuodongInfo();
            info.setID(cursor.getString(0));
            info.setname(cursor.getString(1));
            info.setfatherid(cursor.getString(2));
            info.setdescription(cursor.getString(3));
            info.setauthority(cursor.getString(4));
            info.settype(cursor.getString(5));
            info.setdetail(cursor.getString(6));
            info.setctime(cursor.getString(7));
            info.setcuid(cursor.getString(8));
            info.setftime(cursor.getString(9));
            info.setusernum(cursor.getString(10));         
            userList.add(info);
            Log.i(LOG, LOG+"add info="+info.getID());
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }
    
    //判断users表中的是否包含某个UserID的记录
    public Boolean HaveInfo(String Id)
    {
        Boolean b=false;
        Cursor cursor=db.query(TB_NAME, null, HuodongInfo.ID + "=" + Id, null, null, null,null);
        b=cursor.moveToFirst();
       
        Log.i(LOG,"HaveUserInfo "+b.toString());
       
        cursor.close();
        return b;
    }
    
   
    //更新users表的记录
    public int UpdateInfo(HuodongInfo info)
    {
        ContentValues values = new ContentValues();
        values.put(HuodongInfo.ID, info.getID());

        int id= db.update(TB_NAME, values, HuodongInfo.ID + "=" + info.getID(), null);
        Log.i(LOG,"UpdateUserInfo "+id+"");
        return id;
    }
    
    //添加表的记录
    public Long SaveInfo(HuodongInfo info)
    {
        ContentValues values = new ContentValues();
        values.put(HuodongInfo.ID, info.getID());
        values.put(HuodongInfo.NAME, info.getname());
        values.put(HuodongInfo.FATHERID, info.getfatherid());
        values.put(HuodongInfo.DESCRIPTION, info.getdescription());
        values.put(HuodongInfo.AUTHORITY, info.getauthority());
        values.put(HuodongInfo.TYPE, info.gettype());
        values.put(HuodongInfo.DETAIL, info.getdetail());
        values.put(HuodongInfo.CTIME, info.getctime());
        values.put(HuodongInfo.CUID, info.getcuid());
        values.put(HuodongInfo.FTIME, info.getftime());
        values.put(HuodongInfo.USERNUM, info.getusernum());
        Long uid = db.insert(TB_NAME, HuodongInfo.ID, values);
        Log.i(LOG,"SaveInfo "+uid+"");
        return uid;
    }
    
    //删除users表的记录
    public int DelInfo(String Id){
        int id=  db.delete(TB_NAME, HuodongInfo.ID +"="+Id, null);
        Log.i(LOG,"DelUserInfo "+id+"");
        return id;
    }
 
}

