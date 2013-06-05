package com.tuifi.quanzi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tuifi.quanzi.util.MyActivity;

public class HuodongNew extends MyActivity {
	Button bnsendnewhuodong;
	String hdnames, hdlocs, hdtimes, hdcontents;
	EditText hdname, hdloc, hdtime, hdcontent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newhuodong);
		initActivity();

	}

	private void initActivity() {
		initTopbar();		
		//bnback.setVisibility(View.GONE);
	    bnnewmsg.setVisibility(View.GONE);
		bnrefresh.setVisibility(View.GONE);
		title.setText("�½��");
		hdname = (EditText) findViewById(R.id.hdname);
		hdloc = (EditText) findViewById(R.id.hdloc);
		hdtime = (EditText) findViewById(R.id.hdtime);
		hdcontent = (EditText) findViewById(R.id.hdcontent);
		// ������֤֮ǰ��Ҫ�ж�
		bnsendnewhuodong = (Button) findViewById(R.id.bnsendnewhuodong);
		bnsendnewhuodong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkEditinput()) {
					int sendresult = sendNewHuodongByJson();
					if (sendresult > 0) {
						String msg = "�½���ɹ�";
						Toast.makeText(getApplicationContext(), msg,
								Toast.LENGTH_LONG).show();
						finish();
					}

				}

			}
		});
	}

	private boolean checkEditinput() {
		hdnames = hdname.getText().toString();
		hdlocs = hdloc.getText().toString();
		hdtimes = hdtime.getText().toString();
		hdcontents = hdcontent.getText().toString();
		if ((hdnames == null) || (hdnames.trim().equals(""))) {
			Toast.makeText(getApplicationContext(), "���Ʋ���Ϊ��", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		if ((hdlocs == null) || (hdlocs.trim().equals(""))) {
			Toast.makeText(getApplicationContext(), "�ص㲻��Ϊ��", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		if ((hdtimes == null) || (hdtimes.trim().equals(""))) {
			Toast.makeText(getApplicationContext(), "�ʱ�䲻��Ϊ��",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if ((hdcontents == null) || (hdcontents.trim().equals(""))) {
			Toast.makeText(getApplicationContext(), "����ݲ���Ϊ��",
					Toast.LENGTH_LONG).show();
			return false;
		}

		return true;

	}

	private int sendNewHuodongByJson() { // �����ֻ��Ŷ�ȡ���磬�ж��Ƿ����
		JSONObject data;
		hdnames = hdname.getText().toString();
		hdlocs = hdloc.getText().toString();
		hdtimes = hdtime.getText().toString();
		hdcontents = hdcontent.getText().toString();
		String quanzi = "6";
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "saveNewHuodong");
			map.put("cuid", myuid);
			map.put("name", hdnames);
			map.put("fatherid", quanzi);
			map.put("description", hdcontents);
			map.put("authority", "1");
			map.put("detail", hdcontents);
			map.put("ftime", hdtimes);
			// String ctime = Long.toString(System.currentTimeMillis());
			String ctime = Long.toString(new Date().getTime());
			map.put("ctime", ctime);

			data = queryObject(map, 1);
			String t = data.getString("result");
			if (t.equals("overtime")) {
				Toast.makeText(getApplicationContext(), "���ݶ�ȡ��ʱ����������",
						Toast.LENGTH_LONG).show();
				return -2;
			} else if (t.equals("connerr")) {
				Toast.makeText(getApplicationContext(), "�������޷����ӣ����������Ժ�����",
						Toast.LENGTH_LONG).show();
				return -3;
			} else {
				int result = data.getInt("result");
				if (t.equals("")) {
					// ������� �򷵻سɹ�
					return 0;
				} else {
					return result;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), "����ʧ�ܣ��������硣",
				Toast.LENGTH_LONG).show();
		return 0;

	}

}