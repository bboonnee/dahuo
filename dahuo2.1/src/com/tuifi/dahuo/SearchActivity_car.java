package com.tuifi.dahuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchActivity_car extends Activity {
	TextView tv_fromcity, tv_tocity,car_add;
	RelativeLayout searchcar_layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_car);
		tv_fromcity = (TextView) findViewById(R.id.tv_searchcar_fromcity);
		tv_tocity = (TextView) findViewById(R.id.tv_searchcar_tocity);
		
		car_add  = (TextView) findViewById(R.id.car_add);
		if(ApplicationMap.mAdd!=null)
			car_add.setText(ApplicationMap.mAdd);
		searchcar_layout=(RelativeLayout)findViewById(R.id.searchcar_layout);
		searchcar_layout.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
						Toast.LENGTH_SHORT).show();	*/
			}
		});
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
