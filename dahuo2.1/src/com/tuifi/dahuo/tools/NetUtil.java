package com.tuifi.dahuo.tools;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetUtil {
	public static boolean checkNet(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("NetUtil", e.toString());
		}
		return false;
	}

	// 从一个URL取得一个图片
	public static BitmapDrawable getImageFromUrl(URL url) {
		BitmapDrawable icon = null;
		try {
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			icon = new BitmapDrawable(hc.getInputStream());
			hc.disconnect();
		} catch (Exception e) {
		}
		return icon;
	}
}
