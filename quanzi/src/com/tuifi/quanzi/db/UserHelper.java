package com.tuifi.quanzi.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.tuifi.quanzi.model.User;

public class UserHelper extends DataHelper{
	private static String LOG = "UserHelper";
    private static String TB_NAME = "user";
    public UserHelper(Context context){
    	super(context, TB_NAME);
    }

    //��ȡusers���е�UserID��Access Token��Access Secret�ļ�¼
    public List<User> GetUserList(Boolean isSimple)
    {
        List<User> userList = new ArrayList<User>();
        //Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null, null, User.uid+" DESC");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from user", null);
        Log.i(LOG, LOG+"----------------GetUserList");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()&& (cursor.getString(1)!=null)){
            User user=new User();
            user.setuid(cursor.getString(0));            
            user.setuname(cursor.getString(1));            
            user.setemail(cursor.getString(2));           
            user.setpassword(cursor.getString(3));
            user.setctime(cursor.getString(4));
            user.setMobile(cursor.getString(5));
            user.setimageurl(cursor.getString(6));
            //add by yibo 2011.11.16
            try
            {
                ByteArrayInputStream stream = new ByteArrayInputStream(cursor.getBlob(7)); 
                Drawable icon= Drawable.createFromStream(stream, "image");
                user.setUserIcon(icon);   
            }catch(Exception e )
            {
            	e.printStackTrace();
            	Log.i(LOG, "read image from db error:"+e.toString()+user.getuid());
            }          
         
            userList.add(user);
            Log.i(LOG, LOG+" read user="+user.getuid());
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }
    
    //�ж�users���е��Ƿ����ĳ��UserID�ļ�¼
    public Boolean HaveUserInfo(String UserId)
    {
        Boolean b=false;
        Cursor cursor=db.query(TB_NAME, null, User.UID + "=" + UserId, null, null, null,null);
        b=cursor.moveToFirst();
       
        Log.i(LOG,"HaveUserInfo "+b.toString());
       
        cursor.close();
        return b;
    }
    
    //����users��ļ�¼������UserId�����û��ǳƺ��û�ͼ��
    public int UpdateUserIcon(Bitmap userIcon,String UserId)
    {
        ContentValues values = new ContentValues();
        // BLOB����  
        final ByteArrayOutputStream os = new ByteArrayOutputStream();  
        // ��Bitmapѹ����PNG���룬����Ϊ100%�洢          
        userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);   
        // ����SQLite��Content��������Ҳ����ʹ��raw  
        values.put(User.USERICON, os.toByteArray());
        int id= db.update(TB_NAME, values, User.UID + "=" + UserId, null);
        Log.i(LOG,"UpdateUserIcon "+TB_NAME+" "+UserId);
        return id;
    }
    
    //����users��ļ�¼
    public int UpdateUserInfo(User user)
    {
        ContentValues values = new ContentValues();
        values.put(User.UID, user.getuid());

        int id= db.update(TB_NAME, values, User.UID + "=" + user.getuid(), null);
        Log.i(LOG,"UpdateUserInfo "+id+"");
        return id;
    }
    
    //���users��ļ�¼
    public Long SaveUserInfo(User user)
    {
        ContentValues values = new ContentValues();
        values.put(User.UID, user.getuid());        
        values.put(User.UNAME, user.getuname());        
        values.put(User.EMAIL, user.getemail());
        values.put(User.MOBILE, user.getMobile());
        values.put(User.PASSWORD, user.getpassword());
        values.put(User.CTIME, user.getctime());
        values.put(User.IMAGEURL, user.getimageurl());
        Long uid = db.insert(TB_NAME, User.UID, values);
        Log.i(LOG,"SaveUserInfo "+uid+"");
        return uid;
    }
    
    //ɾ��users��ļ�¼
    public int DelUserInfo(String UserId){
        int id=  db.delete(TB_NAME, User.UID +"="+UserId, null);
        Log.i(LOG,"DelUserInfo "+id+"");
        return id;
    }

}

