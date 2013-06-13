package com.tuifi.dahuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity_post extends Activity {
	TextView tv_fromcity, tv_tocity,post_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_post);
		tv_fromcity = (TextView) findViewById(R.id.tv_searchpost_fromcity);
		tv_tocity = (TextView) findViewById(R.id.tv_searchpost_tocity);
		
		post_add  = (TextView) findViewById(R.id.post_add);
		if(ApplicationMap.mAdd!=null)
			post_add.setText(ApplicationMap.mAdd);
		findViewById(R.id.searchpost_layout).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(post_add.getText().equals("正在定位..."))
						{
							if(ApplicationMap.mAdd!=null)
							{
								post_add.setText(ApplicationMap.mAdd);
								Intent intent = new Intent(SearchActivity_post.this,
										DetailActivity_postlist.class);
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
							Intent intent = new Intent(SearchActivity_post.this,
									DetailActivity_postlist.class);
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
		Intent intent = new Intent(SearchActivity_post.this,
				DetailActivity_postlist.class);
		String start = tv_fromcity.getText().toString();
		String to = tv_tocity.getText().toString();
		intent.putExtra("startcity", start);		
		intent.putExtra("tocity", to);	
		startActivity(intent);
	}
	// 设置城市
		public void select_fromcity(View v) {
			Intent intent = new Intent(SearchActivity_post.this,
					CityActivity.class);	
			String s = tv_fromcity.getText().toString();
			intent.putExtra("add", s);
			intent.putExtra("type", "postStartAdd");
			startActivity(intent);
		}
	// 设置城市
	public void select_tocity(View v) {
		Intent intent = new Intent(SearchActivity_post.this,
				CityActivity.class);	
		String s = tv_tocity.getText().toString();
		intent.putExtra("add", s);
		intent.putExtra("type", "postEndAdd");
		startActivity(intent);
	}

	
	@Override
	public void onResume() {
		super.onResume();		
		if(ApplicationMap.postStartAdd!=null)
		{
			String add=tv_fromcity.getText().toString();
				try{
					add =ApplicationMap.postStartAdd.get(0).REGION_NAME;
					add +=ApplicationMap.postStartAdd.get(1).REGION_NAME;
					add +=ApplicationMap.postStartAdd.get(2).REGION_NAME;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				tv_fromcity.setText(add);						
		}	
		if(ApplicationMap.postEndAdd!=null)
		{
			String add=tv_tocity.getText().toString();
				try{
					add =ApplicationMap.postEndAdd.get(0).REGION_NAME;
					add +=ApplicationMap.postEndAdd.get(1).REGION_NAME;
					add +=ApplicationMap.postEndAdd.get(2).REGION_NAME;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				tv_tocity.setText(add);						
		}		
	}
}
