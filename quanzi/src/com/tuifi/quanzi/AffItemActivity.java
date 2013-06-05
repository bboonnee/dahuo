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
		// ��ȡ������Result��Intent
		Intent intent = getIntent();
		// ��ȡ��intent��Я��������
		Bundle data = intent.getExtras();
		// ��Bundle���ݰ���ȡ������
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
		// ��activity������
		try {
			title.setText("֪ͨ��ϸ");
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
		String time = "ʱ�䣺"+aiitem.getdeadtime();
		String name = aiitem.getName();
		
		afftime.setText(time);		
		affname.setText(name);
		affcontent.setText(aiitem.getcontent());		
		//piuserpic.setImageDrawable();
	}

}
