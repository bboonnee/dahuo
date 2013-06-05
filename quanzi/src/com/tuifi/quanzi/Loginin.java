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
 * 1 ��д�� 2 ��֤���Ƿ�ϸ� 3 ͨ��ѧ�Ų�ѯ��ѧ����Ϣ����֤�����Ƿ�ϸ� 4 �ϸ����Է�����֤���ţ����ֻ� 5
 * �յ�������ʾ�󣬵����¼������ȥ���ݿ����Ƿ��Ѿ��󶨳ɹ����ɹ���������
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
		// ��ȡ������mobile
		preferences = getSharedPreferences("quanziinfo", MODE_PRIVATE);
		mobileme = preferences.getString("mobileme", "");
		mobileinput = preferences.getString("mobileinput", "");
		// ������֤֮ǰ��Ҫ�ж�
		lbnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginButtonClick();
			}
		});

		initTopbar();
		title.setText("��¼");
		bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		bnrefresh.setVisibility(View.GONE);
		// ��ȡSMSȫ����Ϣ
		if (mobileme.equals(""))
			mobileme = getSmsInPhone();

		if (mobileme.equals(""))
			lbnlogin.setText("������֤");
		else
			lbnlogin.setText("��¼");

		if (!mobileinput.equals(""))
			etloginmobile.setText(mobileinput);
	}

	private void loginButtonClick() {
		mobileinput = etloginmobile.getText().toString().trim();
		mobileme = preferences.getString("mobileme", "");
/*		if (pd == null) {
			pd = new ProgressDialog(Loginin.this);

		}*/

		// ��ȡSMSȫ����Ϣ
		if (mobileme.equals(""))
			mobileme = getSmsInPhone();

		if (mobileme.equals("")) {
			lbnlogin.setText("������֤");
			//pd.setMessage("���ڷ�����֤����");
		} else {
			lbnlogin.setText("��¼");
			//pd.setMessage("���ڵ�¼");
		}

		if (!mobileinput.equals(""))
			etloginmobile.setText(mobileinput);

		String bnstr = lbnlogin.getText().toString();

		//pd.show();
		if (bnstr.equals("������֤")) {
			// ������д���ֻ������ȡ������Ϣ����֤�����Ƿ����

			if (getLoginMobile(mobileinput) > 0) {
				// ����ֻ��������
				// ���Ͷ��Ÿ��Լ����ж��Ƿ񱾻�����
				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("mobile", mobileinput);
					jsonObj.put("˵��", "�����Ž���Ϊ����֤�����ֻ����룬�����Ժ��ԡ���MBAȦ�ӡ�");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String body = jsonObj.toString();
				Toast.makeText(getApplicationContext(), body, Toast.LENGTH_LONG)
						.show();
				sendSMS(mobileinput, body);
				lbnlogin.setText("��¼");
				//pd.cancel();// �رս�����
			}
		} else {
			// ���ȡ����ͬ������֤�ɹ���
			if (mobileinput.equals(mobileme)) {
				int res = getLoginMobile(mobileinput);
				//pd.cancel();// �رս�����
				if (res > 0) {
					// �洢��preferences
					Editor edit = preferences.edit();
					edit.putString("autologin", "true");
					edit.commit();
					myuid = UserController.readUidFromMobile(mobileme);
					MainService.msuid = myuid; 
					Intent mintent = new Intent(Loginin.this,
							MainTabActivity.class);
					startActivity(mintent);
					// ������Activity
					finish();
				} else if (res == 0) {
					Toast.makeText(getApplicationContext(), "���ĺ�����δ����Ȧ�ӣ����ɵ�¼",
							Toast.LENGTH_LONG).show();
					return;
				}
			} else {
				//pd.cancel();// �رս�����
				Toast.makeText(getApplicationContext(), "���ĺ��벻�Ǳ������룬�޷���¼",
						Toast.LENGTH_LONG).show();
				return;
			}
		}
	}

	//
	// ͨ��ѧ�Ų�ѯ��ѧ����Ϣ����֤�����Ƿ�ϸ�
	private JSONArray queryLogin(String mobile) throws Exception {
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("do", "logincheck");
		map.put("mobile", mobile);
		// ���巢�������URL
		// String url = HttpUtil.BASE_URL + "login.jsp";
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONArray(HttpUtil.postRequest(url, map, 0));
	}

	private int getLoginMobile(String mobile) {
		// �����ֻ��Ŷ�ȡ���磬�ж��Ƿ����
		JSONArray data;
		try {
			data = queryLogin(mobile);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Toast.makeText(getApplicationContext(), "���ݶ�ȡ��ʱ����������",
						Toast.LENGTH_LONG).show();
				return -2;
			} else if (t.equals("connerr")) {
				Toast.makeText(getApplicationContext(), "�������޷����ӣ����������Ժ�����",
						Toast.LENGTH_LONG).show();
				return -3;
			} else {
				JSONObject jsonObj = data.getJSONObject(0);
				String jmobile = jsonObj.getString("mobile");
				if (jmobile.equals(mobile)) {
					// ������� �򷵻سɹ�
					return 1;
				} else {
					return 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), "��¼ʧ�ܣ������ֻ������Ӧ�û������ڡ�",
				Toast.LENGTH_LONG).show();
		return 0;
	}

	// ���Ͷ���
	private void sendSMS(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		// �����ŷ��ͺ�Ĵ���
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "�����ѷ���",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "���ִ���", Toast.LENGTH_SHORT)
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

		// �������Ѵ������
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

	// ��ȡ���ж���
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
						// �洢��preferences
						Editor edit = preferences.edit();
						edit.putString("mobileinput", mobileinput);
						edit.putString("mobileme", jmobile);
						edit.commit();
						//
						String msg = "��������ȡ�óɹ�";
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
		if (NetUtil.checkNet(this)) { // ���������ɹ�
			Intent it = new Intent("com.tuifi.quanzi.logic.MainService");
			this.startService(it);
			// �Զ���¼

		} else { // ���������쳣�Ի���
			MainService.AlertNetError(this);

		}
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case -100://
			Toast.makeText(this, "��¼ʧ��", 1000).show();
			break;
		case REFRESH_LOGIN:
			//pd.cancel();// �رս�����
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
