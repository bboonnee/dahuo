package com.tuifi.quanzi.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.tuifi.quanzi.R;

public class Config {
	 private static final String TAG = "Config";
     
     public static final String UPDATE_SERVER = "http://122.248.254.151/download/";
     public static final String UPDATE_APKNAME = "MBAÈ¦×Ó";
     public static final String UPDATE_VERJSON = "ver.json";
     public static final String UPDATE_SAVENAME = "Quanzi.apk";
     
     
     public static int getVerCode(Context context) {
             int verCode = -1;
             try {
                     verCode = context.getPackageManager().getPackageInfo(
                                     "com.tuifi.quanzi", 0).versionCode;
             } catch (NameNotFoundException e) {
                     Log.e(TAG, e.getMessage());
             }
             return verCode;
     }
     
     public static String getVerName(Context context) {
             String verName = "";
             try {
                     verName = context.getPackageManager().getPackageInfo(
                                     "com.tuifi.quanzi", 0).versionName;
             } catch (NameNotFoundException e) {
                     Log.e(TAG, e.getMessage());
             }
             return verName; 

     }
     public static String getVerName2(Context context) {  
         String verName = context.getResources()  
         .getText(R.string.app_versionName).toString();  
         return verName;  
     } 
     public static int getVerCode2(Context context) {  
         int verName = Integer.parseInt(context.getResources()  
         .getText(R.string.app_versionCode).toString());  
         return verName;  
     }      
     public static String getAppName(Context context) {
             String verName = context.getResources()
             .getText(R.string.app_name).toString();
             return verName;
     }
}
