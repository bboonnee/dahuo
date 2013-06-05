package com.tuifi.quanzi.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tuifi.quanzi.model.UserInfos;

public class UserInfoDataHelper extends DataHelper{

	private static String LOG = "UserInfoDataHelper";
    private static String TB_NAME = "userinfo";

    public UserInfoDataHelper(Context context) {
		super(context, TB_NAME);
	}
    //获取users表中的UserID、Access Token、Access Secret的记录
    public List<UserInfos> GetList(Boolean isSimple)
    {
        List<UserInfos> userList = new ArrayList<UserInfos>();
        //Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null, null, UserInfos.uid+" DESC");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from "+TB_NAME, null);
        Log.i(LOG, LOG+"----------------GetUserList");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()&& (cursor.getString(1)!=null)){
            UserInfos user=new UserInfos();
            user.setuseinfoid(cursor.getString(0));
            user.setuid(cursor.getString(0));
            user.setinfoid(cursor.getString(1));
            user.setdatastr(cursor.getString(2));
            user.setctime(cursor.getString(3));
            user.setinfoname(cursor.getString(4));
            user.setinfodata(cursor.getString(5));
            user.setinfolevel(cursor.getString(6));               
            userList.add(user);
            Log.i(LOG, LOG+"add user="+user.getuid());
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }
    
    //判断users表中的是否包含某个UserID的记录
    public Boolean HaveInfo(String UserId)
    {
        Boolean b=false;
        Cursor cursor=db.query(TB_NAME, null, UserInfos.UID + "=" + UserId, null, null, null,null);
        b=cursor.moveToFirst();
       
        Log.i(LOG,"HaveUserInfo "+b.toString());
       
        cursor.close();
        return b;
    }
    
    
    //更新users表的记录
    public int UpdateInfo(UserInfos user)
    {
        ContentValues values = new ContentValues();
        values.put(UserInfos.UID, user.getuid());

        int id= db.update(TB_NAME, values, UserInfos.UID + "=" + user.getuid(), null);
        Log.i(LOG,"UpdateUserInfo "+id+"");
        return id;
    }
    
    //添加users表的记录
    public Long SaveInfo(UserInfos user)
    {
        ContentValues values = new ContentValues();
        values.put(UserInfos.UID, user.getuid());
        values.put(UserInfos.USERINFOID, user.getuseinfoid());
        values.put(UserInfos.INFOID, user.getinfoid());
        values.put(UserInfos.DATASTR, user.getdatastr());
        values.put(UserInfos.CTIME, user.getctime());
        values.put(UserInfos.INFONAME, user.getinfoname());
        values.put(UserInfos.INFODATA, user.getinfodata());
        values.put(UserInfos.INFOLEVEL, user.getinfolevel());        
        Long uid = db.insert(TB_NAME, UserInfos.UID, values);
        Log.i(LOG,"SaveUserInfo "+uid+"");
        return uid;
    }
    
    //删除users表的记录
    public int DelInfo(String UserInfoId){
        int id=  db.delete(TB_NAME, UserInfos.USERINFOID +"="+UserInfoId, null);
        Log.i(LOG,"DelUserInfo "+id+"");
        return id;
    }


    
}

