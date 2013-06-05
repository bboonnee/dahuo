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

    //获取users表中的UserID、Access Token、Access Secret的记录
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
    
    //判断users表中的是否包含某个UserID的记录
    public Boolean HaveUserInfo(String UserId)
    {
        Boolean b=false;
        Cursor cursor=db.query(TB_NAME, null, User.UID + "=" + UserId, null, null, null,null);
        b=cursor.moveToFirst();
       
        Log.i(LOG,"HaveUserInfo "+b.toString());
       
        cursor.close();
        return b;
    }
    
    //更新users表的记录，根据UserId更新用户昵称和用户图标
    public int UpdateUserIcon(Bitmap userIcon,String UserId)
    {
        ContentValues values = new ContentValues();
        // BLOB类型  
        final ByteArrayOutputStream os = new ByteArrayOutputStream();  
        // 将Bitmap压缩成PNG编码，质量为100%存储          
        userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);   
        // 构造SQLite的Content对象，这里也可以使用raw  
        values.put(User.USERICON, os.toByteArray());
        int id= db.update(TB_NAME, values, User.UID + "=" + UserId, null);
        Log.i(LOG,"UpdateUserIcon "+TB_NAME+" "+UserId);
        return id;
    }
    
    //更新users表的记录
    public int UpdateUserInfo(User user)
    {
        ContentValues values = new ContentValues();
        values.put(User.UID, user.getuid());

        int id= db.update(TB_NAME, values, User.UID + "=" + user.getuid(), null);
        Log.i(LOG,"UpdateUserInfo "+id+"");
        return id;
    }
    
    //添加users表的记录
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
    
    //删除users表的记录
    public int DelUserInfo(String UserId){
        int id=  db.delete(TB_NAME, User.UID +"="+UserId, null);
        Log.i(LOG,"DelUserInfo "+id+"");
        return id;
    }

}

