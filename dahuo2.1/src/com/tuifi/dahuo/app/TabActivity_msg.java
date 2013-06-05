package com.tuifi.dahuo.app;

import android.os.Bundle;
import android.widget.TextView;

import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.R;
import com.tuifi.dahuo.tools.formActivity;

public class TabActivity_msg extends formActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example_activity);
		ApplicationMap.allActivity.add(this);
		
		TextView tv = (TextView) findViewById(R.id.example_tv);		
		tv.setText(((ApplicationMap)getApplication()).mAdd);
		((ApplicationMap)getApplication()).mTv = tv;
		
	}
}
