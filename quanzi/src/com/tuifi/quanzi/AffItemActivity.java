package com.tuifi.quanzi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.tuifi.quanzi.controller.AffairsController;
import com.tuifi.quanzi.model.AffairsInfo;
import com.tuifi.quanzi.util.MyActivity;

public class AffItemActivity extends MyActivity {

	AffairsInfo aiitem;	
	TextView affname, afftime, affcontent;
	String currentUid;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.affairsitem);
		initActivity();
		// 获取启动该Result的Intent
		Intent intent = getIntent();
		// 获取该intent所携带的数据
		Bundle data = intent.getExtras();
		// 从Bundle数据包中取出数据
		try {
			 currentUid= (String) data.getSerializable("item");
			 aiitem = AffairsController.getAffairsFromId(currentUid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (aiitem == null)
			aiitem = myaffList.get(0);
		setValues();
	}

	private void initActivity() {
		initTopbar();
		initforms();
		// 本activity的设置
		try {
			title.setText("通知详细");
			bnrefresh.setVisibility(View.GONE);
			bnnewmsg.setVisibility(View.GONE);			
			bnback.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						finish();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void initforms() {	 
		affname = (TextView) findViewById(R.id.affname);
		afftime = (TextView) findViewById(R.id.afftime);
		affcontent = (TextView) findViewById(R.id.affcontent);
	}
	void setValues() {
		String time = "时间："+aiitem.getdeadtime();
		String name = aiitem.getName();
		
		afftime.setText(time);		
		affname.setText(name);
		affcontent.setText(aiitem.getcontent());		
		//piuserpic.setImageDrawable();
	}

}
