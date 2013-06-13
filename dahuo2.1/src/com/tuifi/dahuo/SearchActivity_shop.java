package com.tuifi.dahuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity_shop extends Activity {
	TextView tv_searchshop_city,shop_add;
	RelativeLayout searchshop_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_shop);
		tv_searchshop_city = (TextView) findViewById(R.id.tv_searchshop_city);		
		
		shop_add  = (TextView) findViewById(R.id.shop_add);
		if(ApplicationMap.mAdd!=null)
			shop_add.setText(ApplicationMap.mAdd);
		searchshop_layout = (RelativeLayout) findViewById(R.id.searchshop_layout);
		searchshop_layout.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(shop_add.getText().equals("正在定位..."))
						{
							if(ApplicationMap.mAdd!=null)
							{
								shop_add.setText(ApplicationMap.mAdd);
								Intent intent = new Intent(SearchActivity_shop.this,
										DetailActivity_shoplist.class);
								String s = tv_searchshop_city.getText().toString();
								intent.putExtra("add", s);		
								startActivity(intent);
							}else
							{
								Toast.makeText(getApplicationContext(), "暂时无法获取您手机的定位，请在下面选择城市搜索！", 
								Toast.LENGTH_SHORT).show();	
							}
								
						}else
						{
							Intent intent = new Intent(SearchActivity_shop.this,
									DetailActivity_shoplist.class);
							String s = tv_searchshop_city.getText().toString();
							intent.putExtra("add", s);		
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
		Intent intent = new Intent(SearchActivity_shop.this,
				DetailActivity_shoplist.class);
		String s = tv_searchshop_city.getText().toString();
		intent.putExtra("add", s);		
		startActivity(intent);
	}

	// 设置城市
	public void select_shopcity(View v) {
		Intent intent = new Intent(SearchActivity_shop.this,
				CityActivity.class);	
		String s = tv_searchshop_city.getText().toString();
		intent.putExtra("add", s);
		intent.putExtra("type", "shopciy");
		startActivity(intent);
	}
	@Override
	public void onResume() {
		super.onResume();		
		if(ApplicationMap.shopRegion!=null)
		{
			String add=tv_searchshop_city.getText().toString();
				try{
					add =ApplicationMap.shopRegion.get(0).REGION_NAME;
					add +=ApplicationMap.shopRegion.get(1).REGION_NAME;
					add +=ApplicationMap.shopRegion.get(2).REGION_NAME;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				tv_searchshop_city.setText(add);						
		}		
	}
}
