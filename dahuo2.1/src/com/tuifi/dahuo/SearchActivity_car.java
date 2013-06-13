package com.tuifi.dahuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
				if(car_add.getText().equals("正在定位..."))
				{
					if(ApplicationMap.mAdd!=null)
					{
						car_add.setText(ApplicationMap.mAdd);
						Intent intent = new Intent(SearchActivity_car.this,
								DetailActivity_carlist.class);
						String start = tv_fromcity.getText().toString();
						String to = tv_tocity.getText().toString();
						intent.putExtra("startcity", start);		
						intent.putExtra("tocity", to);
						startActivity(intent);
					}else
					{
						Toast.makeText(getApplicationContext(), "暂时无法获取您手机的定位，请在下面选择城市搜索！", 
						Toast.LENGTH_SHORT).show();	
					}
						
				}else
				{
					Intent intent = new Intent(SearchActivity_car.this,
							DetailActivity_carlist.class);
					String start = tv_fromcity.getText().toString();
					String to = tv_tocity.getText().toString();
					intent.putExtra("startcity", start);		
					intent.putExtra("tocity", to);	
					startActivity(intent);
				}
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
		Intent intent = new Intent(SearchActivity_car.this,
				DetailActivity_carlist.class);
		String start = tv_fromcity.getText().toString();
		String to = tv_tocity.getText().toString();
		intent.putExtra("startcity", start);		
		intent.putExtra("tocity", to);	
		startActivity(intent);
	}
	// 设置城市
		public void select_fromcity(View v) {
			Intent intent = new Intent(SearchActivity_car.this,
					CityActivity.class);	
			String s = tv_fromcity.getText().toString();
			intent.putExtra("add", s);
			intent.putExtra("type", "SearchStartAdd");
			startActivity(intent);
		}
	// 设置城市
	public void select_tocity(View v) {
		Intent intent = new Intent(SearchActivity_car.this,
				CityActivity.class);	
		String s = tv_tocity.getText().toString();
		intent.putExtra("add", s);
		intent.putExtra("type", "SearchEndAdd");
		startActivity(intent);
	}

	
	@Override
	public void onResume() {
		super.onResume();		
		if(ApplicationMap.SearchStartAdd!=null)
		{
			String add=tv_fromcity.getText().toString();
				try{
					add =ApplicationMap.SearchStartAdd.get(0).REGION_NAME;
					add +=ApplicationMap.SearchStartAdd.get(1).REGION_NAME;
					add +=ApplicationMap.SearchStartAdd.get(2).REGION_NAME;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				tv_fromcity.setText(add);						
		}	
		if(ApplicationMap.SearchEndAdd!=null)
		{
			String add=tv_tocity.getText().toString();
				try{
					add =ApplicationMap.SearchEndAdd.get(0).REGION_NAME;
					add +=ApplicationMap.SearchEndAdd.get(1).REGION_NAME;
					add +=ApplicationMap.SearchEndAdd.get(2).REGION_NAME;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				tv_tocity.setText(add);						
		}		
	}
}
