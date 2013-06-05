package com.tuifi.quanzi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataHelper {
	private static String LOG = "DataHelper";
    //数据库名称
	public static String DB_NAME = "quanzi.db";   
	public static String TB_NAME = "";
    //数据库版本
	public static int DB_VERSION = 7;
	public SQLiteDatabase db;
	public SqliteHelper dbHelper;
    
    public DataHelper(Context context,String tbname){
    	TB_NAME = tbname;
        dbHelper=new SqliteHelper(context,DB_NAME, null, DB_VERSION,TB_NAME);
        db= dbHelper.getWritableDatabase();
        Log.i(LOG, LOG+"--------dbHelper getWritableDatabase()"+DB_NAME+" "+TB_NAME+" "+DB_VERSION);
    }
    
	public void Close()
    {
    	if(db.isOpen())
        db.close();    	
    	if((dbHelper.getWritableDatabase().isOpen())||(dbHelper.getReadableDatabase().isOpen()))
        dbHelper.close();
    	Log.i(LOG, LOG+"----------------dbHelper close()");
    }
       
    //清空user 表
    public void ClearInfo(){
    	try{
        db.execSQL("delete from "+TB_NAME);
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		Log.e(LOG, e.toString());
    	}
        Log.i(LOG,"ClearInfo");        
    }
    
}

