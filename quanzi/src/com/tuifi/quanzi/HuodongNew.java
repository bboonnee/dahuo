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
		title.setText("新建活动");
		hdname = (EditText) findViewById(R.id.hdname);
		hdloc = (EditText) findViewById(R.id.hdloc);
		hdtime = (EditText) findViewById(R.id.hdtime);
		hdcontent = (EditText) findViewById(R.id.hdcontent);
		// 发送验证之前需要判定
		bnsendnewhuodong = (Button) findViewById(R.id.bnsendnewhuodong);
		bnsendnewhuodong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkEditinput()) {
					int sendresult = sendNewHuodongByJson();
					if (sendresult > 0) {
						String msg = "新建活动成功";
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
			Toast.makeText(getApplicationContext(), "名称不能为空", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		if ((hdlocs == null) || (hdlocs.trim().equals(""))) {
			Toast.makeText(getApplicationContext(), "地点不能为空", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		if ((hdtimes == null) || (hdtimes.trim().equals(""))) {
			Toast.makeText(getApplicationContext(), "活动时间不能为空",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if ((hdcontents == null) || (hdcontents.trim().equals(""))) {
			Toast.makeText(getApplicationContext(), "活动内容不能为空",
					Toast.LENGTH_LONG).show();
			return false;
		}

		return true;

	}

	private int sendNewHuodongByJson() { // 根据手机号读取网络，判断是否存在
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
		}
		Toast.makeText(getApplicationContext(), "发送失败，请检查网络。",
				Toast.LENGTH_LONG).show();
		return 0;

	}

}