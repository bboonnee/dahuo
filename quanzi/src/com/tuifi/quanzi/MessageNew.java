package com.tuifi.quanzi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tuifi.quanzi.util.MyActivity;

public class MessageNew extends MyActivity {
	
	private static String LOG = "MessageNew";
	EditText etmsg;
	Button bnsendnewmsg;
	String selected;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newmsg);
		initActivity();
	}

	private void initActivity() {
		initTopbar();
		// ��activity������
		bnnewmsg.setEnabled(false);
		title.setText("�½�֪ͨ");
		// bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		bnrefresh.setVisibility(View.GONE);
		bnsendnewmsg = (Button) findViewById(R.id.bnsendnewmsg);				
		etmsg = (EditText) findViewById(R.id.etmsg);
		// ������֤֮ǰ��Ҫ�ж�
		bnsendnewmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int sendresult = sendNewMsgByJson();
				if (sendresult>0)
				{
					String msg =  "�ɹ����͸�Ȧ�Ӻ���";
					Toast.makeText(getApplicationContext(),msg,
							Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
	}

	private int sendNewMsgByJson() { // �����ֻ��Ŷ�ȡ���磬�ж��Ƿ����
		JSONObject data;
		String msg = etmsg.getText().toString();
		String quanzi = "6";
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "savenewMessage");
			map.put("senduid",myuid);
			map.put("notifymsg", msg);
			map.put("qzid", quanzi);			
			map.put("type", "1");	//1 quanzi 2 huodong  3 int				
			//String ctime = Long.toString(System.currentTimeMillis());
			String ctime = Long.toString(new Date().getTime());
			map.put("ctime", ctime);
			
			data = queryObject(map, 0);
			String t = data.toString();
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
			Log.e(LOG, "sendNewMsgByJson " + e.toString());
		}
		Toast.makeText(getApplicationContext(), "����ʧ�ܣ��������硣",
				Toast.LENGTH_LONG).show();
		return 0;

	}
}
