package com.tuifi.thumba;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

@SuppressLint("DrawAllocation")
public class Welcome extends Activity {
	private static String LOG = "Welcome";
	protected int _splashTime = 1000;
	String autologin = "";
	String classStr = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.welcome);
		RelativeLayout iv = (RelativeLayout) this
				.findViewById(R.id.welcomelayout);
		// 检查是否有更新
		// 如果有更新提示下载
/*		UpdateManager updateMan = new UpdateManager(Welcome.this);
		updateMan.checkUpdate(false);*/
		final Intent intent = new Intent(Welcome.this, MainActivity.class);
		// 结束该Activity
		//finish();
		// 返回
		RelativeLayout welcomelayout =(RelativeLayout) findViewById(R.id.welcomelayout);
		
		welcomelayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {	
						startActivity(intent);
						finish();
					}
				});
	}
}
