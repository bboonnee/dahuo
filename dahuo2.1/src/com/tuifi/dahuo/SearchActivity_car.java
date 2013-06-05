package com.tuifi.dahuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SearchActivity_car extends Activity {
	TextView tv_fromcity, tv_tocity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_car);
		tv_fromcity = (TextView) findViewById(R.id.tv_fromcity);
		tv_tocity = (TextView) findViewById(R.id.tv_tocity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_activity_shop, menu);
		return true;
	}

	// 设置标题栏左侧返回按钮
	public void shop_back_home(View v) {
		finish();
	}

	// 设置搜索按钮
	public void search_shop(View v) {

	}

	// 设置城市
	public void select_tocity(View v) {
		Intent intent = new Intent(SearchActivity_car.this,
				CityActivity.class);	
		String s = tv_tocity.getText().toString();
		intent.putExtra("add", s);
		intent.putExtra("type", "shopAdd");
		startActivity(intent);
	}

	// 设置城市
	public void select_fromcity(View v) {
		Intent intent = new Intent(SearchActivity_car.this,
				CityActivity.class);	
		String s = tv_fromcity.getText().toString();
		intent.putExtra("add", s);
		intent.putExtra("type", "postEndAdd");
		startActivity(intent);
	}
}
