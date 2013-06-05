package com.tuifi.quanzi;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.tools.NetUtil;
import com.tuifi.quanzi.util.HttpUtil;
import com.tuifi.quanzi.util.MyActivity;

/**
 * 1 填写表单 2 验证表单是否合格 3 通过学号查询此学生信息，验证数据是否合格 4 合格后可以发送验证短信，绑定手机 5
 * 收到短信提示后，点击登录。首先去数据库监察是否已经绑定成功，成功则进入界面
 */

public class Loginin extends MyActivity implements IWeiboActivity {
	Button lbnlogin;
	EditText etloginmobile;
	public static final int REFRESH_LOGIN = 1;
	//public ProgressDialog pd;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		lbnlogin = (Button) findViewById(R.id.lbnlogin);
		etloginmobile = (EditText) findViewById(R.id.etloginmobile);
		// 获取本机的mobile
		preferences = getSharedPreferences("quanziinfo", MODE_PRIVATE);
		mobileme = preferences.getString("mobileme", "");
		mobileinput = preferences.getString("mobileinput", "");
		// 发送验证之前需要判定
		lbnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginButtonClick();
			}
		});

		initTopbar();
		title.setText("登录");
		bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		bnrefresh.setVisibility(View.GONE);
		// 读取SMS全部信息
		if (mobileme.equals(""))
			mobileme = getSmsInPhone();

		if (mobileme.equals(""))
			lbnlogin.setText("短信验证");
		else
			lbnlogin.setText("登录");

		if (!mobileinput.equals(""))
			etloginmobile.setText(mobileinput);
	}

	private void loginButtonClick() {
		mobileinput = etloginmobile.getText().toString().trim();
		mobileme = preferences.getString("mobileme", "");
/*		if (pd == null) {
			pd = new ProgressDialog(Loginin.this);

		}*/

		// 读取SMS全部信息
		if (mobileme.equals(""))
			mobileme = getSmsInPhone();

		if (mobileme.equals("")) {
			lbnlogin.setText("短信验证");
			//pd.setMessage("正在发送验证短信");
		} else {
			lbnlogin.setText("登录");
			//pd.setMessage("正在登录");
		}

		if (!mobileinput.equals(""))
			etloginmobile.setText(mobileinput);

		String bnstr = lbnlogin.getText().toString();

		//pd.show();
		if (bnstr.equals("短信验证")) {
			// 根据填写的手机号码获取网络信息，验证号码是否存在

			if (getLoginMobile(mobileinput) > 0) {
				// 如果手机号码存在
				// 发送短信给自己则判断是否本机号码
				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("mobile", mobileinput);
					jsonObj.put("说明", "本短信仅仅为了验证您的手机号码，您可以忽略。【MBA圈子】");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String body = jsonObj.toString();
				Toast.makeText(getApplicationContext(), body, Toast.LENGTH_LONG)
						.show();
				sendSMS(mobileinput, body);
				lbnlogin.setText("登录");
				//pd.cancel();// 关闭进度条
			}
		} else {
			// 如果取得相同，则验证成功。
			if (mobileinput.equals(mobileme)) {
				int res = getLoginMobile(mobileinput);
				//pd.cancel();// 关闭进度条
				if (res > 0) {
					// 存储到preferences
					Editor edit = preferences.edit();
					edit.putString("autologin", "true");
					edit.commit();
					myuid = UserController.readUidFromMobile(mobileme);
					MainService.msuid = myuid; 
					Intent mintent = new Intent(Loginin.this,
							MainTabActivity.class);
					startActivity(mintent);
					// 结束该Activity
					finish();
				} else if (res == 0) {
					Toast.makeText(getApplicationContext(), "您的号码尚未加入圈子，不可登录",
							Toast.LENGTH_LONG).show();
					return;
				}
			} else {
				//pd.cancel();// 关闭进度条
				Toast.makeText(getApplicationContext(), "您的号码不是本机号码，无法登录",
						Toast.LENGTH_LONG).show();
				return;
			}
		}
	}

	//
	// 通过学号查询此学生信息，验证数据是否合格
	private JSONArray queryLogin(String mobile) throws Exception {
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("do", "logincheck");
		map.put("mobile", mobile);
		// 定义发送请求的URL
		// String url = HttpUtil.BASE_URL + "login.jsp";
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONArray(HttpUtil.postRequest(url, map, 0));
	}

	private int getLoginMobile(String mobile) {
		// 根据手机号读取网络，判断是否存在
		JSONArray data;
		try {
			data = queryLogin(mobile);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Toast.makeText(getApplicationContext(), "数据读取超时，请检查网络",
						Toast.LENGTH_LONG).show();
				return -2;
			} else if (t.equals("connerr")) {
				Toast.makeText(getApplicationContext(), "服务器无法连接，请检查网络稍后再试",
						Toast.LENGTH_LONG).show();
				return -3;
			} else {
				JSONObject jsonObj = data.getJSONObject(0);
				String jmobile = jsonObj.getString("mobile");
				if (jmobile.equals(mobile)) {
					// 号码存在 则返回成功
					return 1;
				} else {
					return 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), "登录失败，您的手机号码对应用户不存在。",
				Toast.LENGTH_LONG).show();
		return 0;
	}

	// 发送短信
	private void sendSMS(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		// 监测短信发送后的处理
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "短信已发出",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "出现错误", Toast.LENGTH_SHORT)
							.show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		// 监测短信已传输完毕
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered",
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}

	// 获取所有短信
	public String getSmsInPhone() {
		final String SMS_URI_ALL = "content://sms/";
		final String SMS_URI_INBOX = "content://sms/inbox";
		final String SMS_URI_SEND = "content://sms/sent";
		final String SMS_URI_DRAFT = "content://sms/draft";

		StringBuilder smsBuilder = new StringBuilder();

		try {
			ContentResolver cr = getContentResolver();
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			Uri uri = Uri.parse(SMS_URI_INBOX);
			Cursor cur = cr.query(uri, projection, null, null, "date desc");

			if (cur.moveToFirst()) {
				String name;
				String phoneNumber;
				String smsbody;
				String date;
				String type;

				int nameColumn = cur.getColumnIndex("person");
				int phoneNumberColumn = cur.getColumnIndex("address");
				int smsbodyColumn = cur.getColumnIndex("body");
				int dateColumn = cur.getColumnIndex("date");
				int typeColumn = cur.getColumnIndex("type");

				do {
					name = cur.getString(nameColumn);
					phoneNumber = cur.getString(phoneNumberColumn);
					smsbody = cur.getString(smsbodyColumn);
					if (smsbody == null)
						smsbody = "";
					try {
						JSONObject jsonObj = new JSONObject(smsbody);
						String jmobile = jsonObj.getString("mobile");
						// 存储到preferences
						Editor edit = preferences.edit();
						edit.putString("mobileinput", mobileinput);
						edit.putString("mobileme", jmobile);
						edit.commit();
						//
						String msg = "本机号码取得成功";
						Toast.makeText(getApplicationContext(), msg,
								Toast.LENGTH_LONG).show();
						return jmobile;

					} catch (Exception e) {
						e.printStackTrace();
					}
				} while (cur.moveToNext());
			} else {
				smsBuilder.append("no result!");
			}

			smsBuilder.append("getSmsInPhone has executed!");
		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		} catch (Exception e) {
			Log.d("Exception in getSmsInPhone", e.getMessage());
		}
		return "";
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if (NetUtil.checkNet(this)) { // 如果网络检查成功
			Intent it = new Intent("com.tuifi.quanzi.logic.MainService");
			this.startService(it);
			// 自动登录

		} else { // 弹出网络异常对话框
			MainService.AlertNetError(this);

		}
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case -100://
			Toast.makeText(this, "登录失败", 1000).show();
			break;
		case REFRESH_LOGIN:
			//pd.cancel();// 关闭进度条
			Intent it = new Intent(this, MainTabActivity.class);
			this.startActivity(it);
			MainService.allActivity.remove(this);
			finish();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
	}
}
