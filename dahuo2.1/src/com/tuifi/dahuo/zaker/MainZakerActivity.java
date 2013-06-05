package com.tuifi.dahuo.zaker;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

import com.tuifi.dahuo.R;

public class MainZakerActivity extends Activity {
	ArrayList<ImageInfo> data; // 菜单数据
	private static TextView mynum; // 页码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mynum = (TextView) findViewById(R.id.mynum);
		// 初始化数据
		initData();
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
		data.add(new ImageInfo("搜索货源", R.drawable.icon14, R.drawable.icon_bg01));
		data.add(new ImageInfo("搜索车辆", R.drawable.icon6, R.drawable.icon_bg01));
		data.add(new ImageInfo("搜信息部", R.drawable.icon11, R.drawable.icon_bg01));
		data.add(new ImageInfo("发布信息", R.drawable.icon4, R.drawable.icon_bg01));
		data.add(new ImageInfo("周边地图", R.drawable.icon7, R.drawable.icon_bg02));
		data.add(new ImageInfo("个人中心", R.drawable.icon5, R.drawable.icon_bg02));
		data.add(new ImageInfo("收藏历史", R.drawable.icon9, R.drawable.icon_bg02));
		data.add(new ImageInfo("推送信息", R.drawable.icon1, R.drawable.icon_bg02));		


	}


}
