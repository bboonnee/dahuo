package com.tuifi.dahuo.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.CityActivity;
import com.tuifi.dahuo.R;

public class TabActivity_search extends Activity {
	public SharedPreferences preferences;
	EditText startcity, tocity;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabactivity_search);
		ApplicationMap.allActivity.add(this);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		RelativeLayout layout_search=(RelativeLayout) findViewById(R.id.layout_search);
		layout_search.setBackgroundResource(R.color.white);
		
		startcity = (EditText) findViewById(R.id.startcity);
		tocity = (EditText) findViewById(R.id.tocity);
		startcity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(TabActivity_search.this,
						CityActivity.class);
				String startpcode = preferences.getString("startpcode", null);
				String startccode = preferences.getString("startccode", null);
				String startxcode = preferences.getString("startxcode", null);
				intent.putExtra("pcode", startpcode);
				intent.putExtra("ccode", startccode);
				intent.putExtra("xcode", startxcode);
				intent.putExtra("selectRegionType", true);

				startActivity(intent);

			}
		});
		tocity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(TabActivity_search.this,
						CityActivity.class);
				String topcode = preferences.getString("topcode", null);
				String toccode = preferences.getString("toccode", null);
				String toxcode = preferences.getString("toxcode", null);
				intent.putExtra("pcode", topcode);
				intent.putExtra("ccode", toccode);
				intent.putExtra("xcode", toxcode);
				intent.putExtra("selectRegionType", false);
				startActivity(intent);

			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		// 把数据保存到类似于Session之类的存储集合里面

		Editor edit = preferences.edit();
		edit.putString("startregion", startcity.getText().toString());
		edit.putString("toregion", tocity.getText().toString());
		edit.commit();
	}

	// 重启：onStart()->onResume()
	@Override
	public void onResume() {
		super.onResume();

		// 从共享数据存储对象中获取所需的数据
		// preferences = getSharedPreferences("dahuo", MODE_PRIVATE);
		String start = preferences.getString("startregion", null);
		String to = preferences.getString("toregion", null);
		startcity.setText(start);
		tocity.setText(to);

	}
}
