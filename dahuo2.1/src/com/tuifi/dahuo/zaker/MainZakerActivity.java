package com.tuifi.dahuo.zaker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.LoginActivity;
import com.tuifi.dahuo.R;
import com.tuifi.dahuo.app.TabActivity_pref;

public class MainZakerActivity extends Activity {
	ArrayList<ImageInfo> data; // 菜单数据
	private static TextView mynum; // 页码
	Button set;
	private LocationClient mLocClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mynum = (TextView) findViewById(R.id.mynum);
		set= (Button) findViewById(R.id.set);
		findViewById(R.id.set).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(MainZakerActivity.this,
								TabActivity_pref.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.add).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(MainZakerActivity.this,
								LoginActivity.class);
						startActivity(intent);
					}
				});
		// 初始化数据
		initData();
		getGeo();
		ViewPager vpager = (ViewPager) findViewById(R.id.vPager);
		vpager.setAdapter(new MyPagerAdapter(MainZakerActivity.this, data));
		vpager.setPageMargin(50);
		vpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mynum.setText("" + (int) (arg0 + 1));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initData() {
		data = new ArrayList<ImageInfo>();
		mynum.setText("1");
		data.add(new ImageInfo("货源", R.drawable.icon14, R.drawable.icon_bg01));
		data.add(new ImageInfo("车辆", R.drawable.icon6, R.drawable.icon_bg01));
		data.add(new ImageInfo("信息部", R.drawable.icon11, R.drawable.icon_bg01));
		data.add(new ImageInfo("发布", R.drawable.icon4, R.drawable.icon_bg01));
		data.add(new ImageInfo("地图", R.drawable.icon7, R.drawable.icon_bg02));
		data.add(new ImageInfo("个人", R.drawable.icon5, R.drawable.icon_bg02));
		data.add(new ImageInfo("收藏", R.drawable.icon9, R.drawable.icon_bg02));
		data.add(new ImageInfo("通知", R.drawable.icon1, R.drawable.icon_bg02));

		
	}
	public void getGeo()
	{
		mLocClient = ((ApplicationMap) getApplication()).mLocationClient;

		ApplicationMap.setLocationOption(mLocClient);
		mLocClient.start();

		// 发起定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
		if (mLocClient != null && mLocClient.isStarted())
			mLocClient.requestLocation();
		else
			Log.d("LocSDK3", "locClient is null or not started");
	}
	@Override
	public void onDestroy() {
		mLocClient.stop();		
		super.onDestroy();
	}
	@Override
	public void onResume() {
		super.onResume();
		getGeo();
	}
}
