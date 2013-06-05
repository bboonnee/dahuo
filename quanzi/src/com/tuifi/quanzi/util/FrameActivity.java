package com.tuifi.quanzi.util;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.MainService;


public class FrameActivity extends MyActivity {
	private static String LOG = "FrameActivity";
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MainService.promptExit(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainService.allActivity.add(this);
		preferences = getSharedPreferences("quanziinfo", MODE_PRIVATE);
		mobileme = preferences.getString("mobileme", "");
		mobileinput = preferences.getString("mobileinput", "");
	    myuid = preferences.getString("myuid", "");
		if (myuid.equals("")) myuid =UserController.getMyuid(this, mobileme);		
		MainService.msuid = myuid;
		MainService.msmobile= mobileme;
		Log.i(LOG, LOG+" onCreate");
	}
}
