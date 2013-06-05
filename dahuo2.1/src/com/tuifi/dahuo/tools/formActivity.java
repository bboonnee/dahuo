package com.tuifi.dahuo.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.LoginActivity;
import com.tuifi.dahuo.R;

public class formActivity extends constActivity {
	public final int PLAIN_MY = 0X11a;
	public final int PLAIN_SETTING = 0X11b;
	public final int PLAIN_EXIT = 0X11c;
	public final int PLAIN_ABOUT = 0X11d;

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			promptExit(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, PLAIN_MY, 0, "我的资料");
		menu.add(0, PLAIN_SETTING, 0, "版本更新");
		menu.add(0, PLAIN_EXIT, 0, "注销登录");
		menu.add(0, PLAIN_ABOUT, 0, "软件介绍");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = false;
		switch (item.getItemId()) {
		case PLAIN_MY:
			/*//O
			Intent intent = new Intent(formActivity.this,
					InfoActivity.class);
			Bundle b = new Bundle();			
			b.putSerializable("item", currentUser.getuid());
			intent.putExtras(b);
			startActivity(intent);
			result = true;*/
			break;
		case PLAIN_SETTING:
			//
			/*UpdateManager updateMan = new UpdateManager(formActivity.this);
			updateMan.checkUpdate(true);
			result = true;*/
			break;
		case PLAIN_EXIT:
			//
			exitlogin();
			result = true;
		case PLAIN_ABOUT:
			/*Intent intent2 = new Intent(formActivity.this,
					aboutActivity.class);			
			startActivity(intent2);
			result = true;*/
			break;		
		}
		return result;
	}

	public void exitlogin() {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		//preferences = getSharedPreferences("dahuo", MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean("autologin", false);
		edit.commit();
		Intent mintent = new Intent(formActivity.this, LoginActivity.class);
		startActivity(mintent);
		// 结束该Activity
		finish();
	}

	public  void promptExit(final Context con) {
		// 创建对话框
		LayoutInflater li = LayoutInflater.from(con);
		View exitV = li.inflate(R.layout.exitdialog, null);
		AlertDialog.Builder ab = new AlertDialog.Builder(con);
		ab.setView(exitV);// 设定对话框显示的View对象
		ab.setPositiveButton(R.string.exit,
				new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						((ApplicationMap) getApplication()).exitApp(con);
					}
				});
		ab.setNegativeButton(R.string.cancel, null);
		// 显示对话框
		ab.show();
		
	}

	
	
}
