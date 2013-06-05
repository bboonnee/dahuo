package com.tuifi.quanzi;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.util.HttpUtil;

/**
 */
public class LaunchService extends Service {
	public static String LOG = "LaunchService";
	int notification_id = 20110822;
	SharedPreferences preferences;
	int refreshtime = 100000;
	String myuid = "";
	String mobileme;
	String smsSender, smsBody;
	static final String ACTION_sms_received = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		preferences = getSharedPreferences("quanziinfo", MODE_PRIVATE);
		mobileme = preferences.getString("mobileme", "");
		refreshtime = preferences.getInt("refreshtime", 60000);
		Log.i(LOG, "refreshtime=" + refreshtime);
		myuid = preferences.getString("myuid", "");
		if (myuid.equals("")) {
			myuid = UserController.getMyuid(this, mobileme);
		}
		MainService.msuid = myuid;
		// ����1��ִ��һ�����
		if (!myuid.equals(""))
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					int notifynum = getNewNotify();
					if (notifynum > 0) {
						makeNoifty(notifynum);
					}
					String actName = getActivityName();
					if(actName.equals("actName.ChatActivity"))
					{
						
					}
					Log.i(LOG, ".....Timer running " + myuid+" actName"+actName);
				}
			}, 0, refreshtime);

		// �����Ŵ���
		IntentFilter mfilter = new IntentFilter();
		mfilter.setPriority(800);
		mfilter.addAction(ACTION_sms_received);
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// mobileme = handleEvent(context, intent);
			}
		}, mfilter);
	}
	private String getActivityName()
	{
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);   
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  		
		Log.d("", "pkg:"+cn.getPackageName());   
		Log.d("", "cls:"+cn.getClassName());   
		return cn.getShortClassName();

	}
	// �����õĶ��� ������ܵ��������Ķ��ţ�������mobileһ�£�����֤�ɹ�������ʧ��
	// ���ر���mobile
	private String handleEvent(Context context, Intent intent) {
		// ����ǽ��յ�����
		if (intent.getAction().equals(ACTION_sms_received)) {
			// ȡ���㲥�����д��뽫����ϵͳ�ղ������ţ�
			// abortBroadcast();
			// ������SMS������������
			Bundle bundle = intent.getExtras();
			// �ж��Ƿ�������
			if (bundle != null) {
				// ͨ��pdus���Ի�ý��յ������ж�����Ϣ
				Object[] pdus = (Object[]) bundle.get("pdus");
				// �������Ŷ���array,�������յ��Ķ��󳤶�������array�Ĵ�С
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				// �������Ķ��źϲ��Զ�����Ϣ��StringBuilder����
				for (SmsMessage message : messages) {
					smsSender = message.getDisplayOriginatingAddress();
					smsBody = message.getDisplayMessageBody();
					try {
						JSONObject jsonObj = new JSONObject(smsBody);
						String jmobile = jsonObj.getString("mobile");
						// �洢��preferences
						Editor edit = preferences.edit();
						edit.putString("mobileinput", jmobile);
						edit.putString("mobileme", smsSender);
						edit.commit();

						return smsSender;

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return "";
	}

	private void makeNoifty(int notifynum) {
		Intent intent = new Intent(LaunchService.this, MainTabActivity.class);
		// ����һ��Bundle����
		Bundle data = new Bundle();
		data.putInt("notifyid", notification_id);
		data.putInt("RaidoTabSelected", 2);
		intent.putExtras(data);

		PendingIntent pi = PendingIntent.getActivity(LaunchService.this, 0,
				intent, 0);

		String notifymsg = "����" + notifynum + "���µ�Ȧ����Ϣ,�������";
		// ����һ��Notification
		Notification notify = new Notification();
		// ΪNotification����ͼ�꣬��ͼ����ʾ��״̬��
		notify.icon = R.drawable.mba_icon;
		// ΪNotification�����ı����ݣ����ı�����ʾ��״̬��
		notify.tickerText = notifymsg;
		// ΪNotification���÷���ʱ��
		notify.when = System.currentTimeMillis();
		// ΪNotification��������
		notify.defaults = Notification.DEFAULT_SOUND;
		// ΪNotification����Ĭ��������Ĭ���񶯡�Ĭ�������
		notify.defaults = Notification.DEFAULT_ALL;

		// �����¼���Ϣ
		notify.setLatestEventInfo(LaunchService.this, notifymsg, "����鿴", pi);
		// ��ȡϵͳ��NotificationManager����
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// ����֪ͨ
		try {
			notificationManager.notify(notification_id, notify);
		} catch (Exception e) {
			e.printStackTrace();
			e.toString();
		}
	}

	// ���巢������ķ���
	private JSONObject queryNewNotify() throws Exception {
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("do", "getNotifyNumber");
		map.put("uid", myuid);
		// ���巢�������URL
		// String url = HttpUtil.BASE_URL + "login.jsp";
		String url = HttpUtil.BASE_URL;
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map, 0));
	}

	private int getNewNotify() {

		JSONObject jsonObj;
		try {
			jsonObj = queryNewNotify();

			// ���userId ����0
			int number = jsonObj.getInt("is_read");
			if (number > 0) {
				return number;
			}
		} catch (Exception e) {
			//
			// Toast.makeText(getApplicationContext(), "��������Ӧ�쳣�����Ժ����ԣ�",
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return 0;
	}

}