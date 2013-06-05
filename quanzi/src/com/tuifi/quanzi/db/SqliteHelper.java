package com.tuifi.quanzi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tuifi.quanzi.model.AffairsInfo;
import com.tuifi.quanzi.model.HuodongInfo;
import com.tuifi.quanzi.model.MsgInfo;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.model.User;
import com.tuifi.quanzi.model.UserInfos;
/**
 * 创建数据库  用来保存所需要的信息
 * @author yibo
 *
 */
public class SqliteHelper extends SQLiteOpenHelper {

	private static String LOG = "SqliteHelper";
	public static String TABLE_NAME;

	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version,String tablename) {
		super(context, name, factory, version);
		TABLE_NAME = tablename;
		Log.i(LOG,"--------SqliteHelper() tablename="+tablename+" version="+version);
	}

	//创建表
	@Override
	public void onCreate(SQLiteDatabase arg0) {
/*		if(TABLE_NAME.equals("users"))
		arg0.execSQL("CREATE TABLE IF NOT EXISTS "+
	                TABLE_NAME+"("+
	                UserInfo.UID+" integer primary key,"+
	                UserInfo.UNUM+" varchar,"+
	                UserInfo.UNAME+" varchar,"+
	                UserInfo.UENAME+" varchar,"+
	                UserInfo.EMAIL+" varchar,"+
	                UserInfo.MOBILE+" varchar,"+
	                UserInfo.GRADE+" varchar,"+
	                UserInfo.PROJECT+" varchar,"+
	                UserInfo.CLASSNUM+" varchar,"+
	                UserInfo.PASSWORD+" varchar,"+
	                UserInfo.SEX+" varchar,"+
	                UserInfo.COUNTRY+" varchar,"+
	                UserInfo.LOCATION+" varchar,"+
	                UserInfo.BIRTHDAY+" varchar,"+
	                UserInfo.ADDRESS+" varchar,"+
	                UserInfo.COMPANY+" varchar,"+
	                UserInfo.TITLE+" varchar,"+
	                UserInfo.INDUSTRY+" varchar,"+
	                UserInfo.HOBBY+" varchar,"+
	                UserInfo.CARNUMBER+" varchar,"+
	                UserInfo.DESCRIPTION+" varchar,"+
	                UserInfo.CTIME+" varchar"+               	                
	                ")"
	                );*/		
			arg0.execSQL("CREATE TABLE IF NOT EXISTS user ( "+
		                User.UID+" integer primary key,"+		                
		                User.UNAME+" varchar,"+		                
		                User.EMAIL+" varchar,"+		                
		                User.PASSWORD+" varchar,"+
		                User.CTIME+" varchar,"+
		                User.MOBILE+" varchar,"+
		                User.IMAGEURL+" varchar,"+
		                User.USERICON+" blob"+
		                ")"
		                );		
			arg0.execSQL("CREATE TABLE IF NOT EXISTS userinfo("+
		                UserInfos.USERINFOID+" integer primary key,"+		                
		                UserInfos.UID+" varchar,"+		                
		                UserInfos.INFOID+" varchar,"+		                
		                UserInfos.DATASTR+" varchar,"+
		                UserInfos.CTIME+" varchar,"+
		                UserInfos.INFONAME+" varchar,"+
		                UserInfos.INFODATA+" varchar,"+
		                UserInfos.INFOLEVEL+" varchar"+		                
		                ")"
		                );		
	        
				String sql = "CREATE TABLE IF NOT EXISTS quanzi("+
		                QuanziInfo.ID+" integer primary key,"+		                
		                QuanziInfo.NAME+" varchar,"+		                
		                QuanziInfo.FATHERID+" varchar,"+		                
		                QuanziInfo.FATHERNAME+" varchar,"+		                
		                QuanziInfo.DESCRIPTION+" varchar,"+
		                QuanziInfo.AUTHORITY+" varchar,"+
		                QuanziInfo.TYPE+" varchar,"+		                
		                QuanziInfo.DETAIL+" varchar,"+
		                QuanziInfo.CUID+" varchar,"+
		                QuanziInfo.FTIME+" varchar,"+
		                QuanziInfo.USERNUM+" varchar,"+		
		                QuanziInfo.CTIME+" varchar,"+
		                User.IMAGEURL+" varchar,"+
		                QuanziInfo.ICON+" blob"+		                		                		                
		                ")";
				arg0.execSQL(sql);
				Log.d(LOG, "-----CREATE TABLE "+sql);
						
				arg0.execSQL("CREATE TABLE IF NOT EXISTS huodong("+
			                HuodongInfo.ID+" integer primary key,"+		                
			                HuodongInfo.NAME+" varchar,"+		                
			                HuodongInfo.FATHERID+" varchar,"+		                
			                HuodongInfo.FATHERNAME+" varchar,"+			                
			                HuodongInfo.DESCRIPTION+" varchar,"+
			                HuodongInfo.AUTHORITY+" varchar,"+
			                HuodongInfo.TYPE+" varchar,"+		                
			                HuodongInfo.DETAIL+" varchar,"+
			                HuodongInfo.CUID+" varchar,"+
			                HuodongInfo.FTIME+" varchar,"+
			                HuodongInfo.USERNUM+" varchar,"+		
			                HuodongInfo.CTIME+" varchar,"+
			                User.IMAGEURL+" varchar,"+
			                HuodongInfo.ICON+" blob"+
			                ")"
			                );				
				arg0.execSQL("CREATE TABLE IF NOT EXISTS message("+
			                MsgInfo.ID+" integer primary key,"+		                
			                MsgInfo.CONTENT+" varchar,"+		                
			                MsgInfo.SENDUID+" varchar,"+		                
			                MsgInfo.RECEIVEUID+" varchar,"+
			                MsgInfo.CTIME+" varchar,"+
			                MsgInfo.QZID+" varchar,"+
			                MsgInfo.HDID+" varchar,"+
			                MsgInfo.TYPE+" varchar"+		                		                
			                ")"
			                );	
				arg0.execSQL("CREATE TABLE IF NOT EXISTS affairs("+
						AffairsInfo.ID+" integer primary key,"+		                
						AffairsInfo.AID+" varchar,"+		                
						AffairsInfo.NAME+" varchar,"+		                
						AffairsInfo.DEADTIME+" varchar,"+
						AffairsInfo.CRAWLTIME+" varchar,"+
						AffairsInfo.CONTENT+" varchar,"+
						AffairsInfo.URL+" varchar,"+
						AffairsInfo.LIKENUM+" varchar,"+
						AffairsInfo.SOURCESITE+" varchar,"+
						AffairsInfo.QUANZI+" varchar"+	                		                
		                ")"
		                );					
		        Log.i(LOG,"Database"+TABLE_NAME+"onCreate");	        	        	       
	}
	//更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS user");
		db.execSQL("DROP TABLE IF EXISTS userinfo");
		db.execSQL("DROP TABLE IF EXISTS quanzi");
		db.execSQL("DROP TABLE IF EXISTS huodong");
		db.execSQL("DROP TABLE IF EXISTS message");			
        onCreate(db);
        Log.e(LOG,"onUpgrade");
	}
	//更新列
	public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
        try{
            db.execSQL("ALTER TABLE " +
                    TABLE_NAME + " CHANGE " +
                    oldColumn + " "+ newColumn +
                    " " + typeColumn
            );
        }catch(Exception e){
            e.printStackTrace();
            Log.e(LOG, e.toString());
        }
        Log.e(LOG,"updateColumn "+oldColumn+" "+newColumn);
    }

}
