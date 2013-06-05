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
		// 本activity的设置
		bnnewmsg.setEnabled(false);
		title.setText("新建通知");
		// bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		bnrefresh.setVisibility(View.GONE);
		bnsendnewmsg = (Button) findViewById(R.id.bnsendnewmsg);				
		etmsg = (EditText) findViewById(R.id.etmsg);
		// 发送验证之前需要判定
		bnsendnewmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int sendresult = sendNewMsgByJson();
				if (sendresult>0)
				{
					String msg =  "成功发送给圈子好友";
					Toast.makeText(getApplicationContext(),msg,
							Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
	}

	private int sendNewMsgByJson() { // 根据手机号读取网络，判断是否存在
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
				Toast.makeText(getApplicationContext(), "数据读取超时，请检查网络",
						Toast.LENGTH_LONG).show();
				return -2;
			} else if (t.equals("connerr")) {
				Toast.makeText(getApplicationContext(), "服务器无法连接，请检查网络稍后再试",
						Toast.LENGTH_LONG).show();
				return -3;
			} else {		
				int result = data.getInt("result");
				if (t.equals("")) {
					// 号码存在 则返回成功
					return 0;
				} else {
					return result;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG, "sendNewMsgByJson " + e.toString());
		}
		Toast.makeText(getApplicationContext(), "发送失败，请检查网络。",
				Toast.LENGTH_LONG).show();
		return 0;

	}
}
