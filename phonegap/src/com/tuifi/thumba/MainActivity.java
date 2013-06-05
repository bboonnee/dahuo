package com.tuifi.thumba;

import org.apache.cordova.DroidGap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setIntegerProperty("loadUrlTimeoutValue", 60000); 
		super.loadUrl("file:///android_asset/www/index.html");
		/*UpdateManager updateMan = new UpdateManager(MainActivity.this);
		updateMan.checkUpdate(false);*/
		//super.loadUrl("http://54.251.118.17/senchamba");
		//super.loadUrl("http://docs.sencha.com/touch/2-1/#!/example/kitchen-sink");
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			promptExit(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public static void promptExit(final Context con) {
		// 创建对话框
		LayoutInflater li = LayoutInflater.from(con);
		View exitV = li.inflate(R.layout.exitdialog, null);
		AlertDialog.Builder ab = new AlertDialog.Builder(con);
		ab.setView(exitV);// 设定对话框显示的View对象
		ab.setPositiveButton("退出",
				new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub		
						System.exit(0);
					}
				});
		ab.setNegativeButton("取消", null);
		// 显示对话框
		ab.show();
	}
	
}


