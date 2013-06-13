package com.tuifi.dahuo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.tuifi.dahuo.controller.RegionController;
import com.tuifi.dahuo.model.User;
import com.tuifi.dahuo.tools.formActivity;
import com.tuifi.dahuo.zaker.MainZakerActivity;

public class SpalshActivity extends formActivity {
	private final int SPLASH_DISPLAY_LENGHT = 1000;
	boolean autologin;
	private LocationClient mLocClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spalsh);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		mLocClient = ((ApplicationMap) getApplication()).mLocationClient;
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				getPreferences();
				ApplicationMap.regionList = RegionController
						.getResionList(getResources());
				provinceList = RegionController
						.getProvinceRegion(ApplicationMap.regionList);	
				ApplicationMap.setLocationOption(mLocClient);
				mLocClient.start();

				// 发起定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
				if (mLocClient != null && mLocClient.isStarted())
					mLocClient.requestLocation();
				else
					Log.d("LocSDK3", "locClient is null or not started");

				// Intent mainIntent = new
				// Intent(SpalshActivity.this,MainActivity.class);
				Intent mainIntent = new Intent(SpalshActivity.this,
						MainZakerActivity.class);
				SpalshActivity.this.startActivity(mainIntent);
				SpalshActivity.this.finish();
				
				
				/*if ((autologin) && (!ApplicationMap.currentUser.id.equals(""))) {
					ApplicationMap.setLocationOption(mLocClient);
					mLocClient.start();

					// 发起定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
					if (mLocClient != null && mLocClient.isStarted())
						mLocClient.requestLocation();
					else
						Log.d("LocSDK3", "locClient is null or not started");

					// Intent mainIntent = new
					// Intent(SpalshActivity.this,MainActivity.class);
					Intent mainIntent = new Intent(SpalshActivity.this,
							MainZakerActivity.class);
					SpalshActivity.this.startActivity(mainIntent);
					SpalshActivity.this.finish();
				} else {
					Intent mainIntent = new Intent(SpalshActivity.this,
							LoginActivity.class);
					SpalshActivity.this.startActivity(mainIntent);
					SpalshActivity.this.finish();
				}*/
			}
		}, SPLASH_DISPLAY_LENGHT);
	}

	private void getPreferences() {

		// autologin = preferences.getString("autologin", "");
		autologin = preferences.getBoolean("autologin", false);
		if (ApplicationMap.currentUser == null)
			ApplicationMap.currentUser = new User();
		ApplicationMap.currentUser.mobile = preferences.getString("mobile", "");
		ApplicationMap.currentUser.id = preferences.getString("uid", "");
		ApplicationMap.currentUser.name = preferences.getString("uname", "");
		ApplicationMap.currentUser.type = preferences.getString("type", "");
	}
}
