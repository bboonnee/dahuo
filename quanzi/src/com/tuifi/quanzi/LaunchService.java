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
		// 定义1秒执行一行输出
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

		// 监测短信传输
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
	// 处理获得的短信 如果接受到自身发来的短信，并且与mobile一致，则验证成功，否则失败
	// 返回本机mobile
	private String handleEvent(Context context, Intent intent) {
		// 如果是接收到短信
		if (intent.getAction().equals(ACTION_sms_received)) {
			// 取消广播（这行代码将会让系统收不到短信）
			// abortBroadcast();
			// 接收由SMS传过来的数据
			Bundle bundle = intent.getExtras();
			// 判断是否有数据
			if (bundle != null) {
				// 通过pdus可以获得接收到的所有短信消息
				Object[] pdus = (Object[]) bundle.get("pdus");
				// 构建短信对象array,并依据收到的对象长度来创建array的大小
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				// 将送来的短信合并自定义信息于StringBuilder当中
				for (SmsMessage message : messages) {
					smsSender = message.getDisplayOriginatingAddress();
					smsBody = message.getDisplayMessageBody();
					try {
						JSONObject jsonObj = new JSONObject(smsBody);
						String jmobile = jsonObj.getString("mobile");
						// 存储到preferences
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
		// 创建一个Bundle对象
		Bundle data = new Bundle();
		data.putInt("notifyid", notification_id);
		data.putInt("RaidoTabSelected", 2);
		intent.putExtras(data);

		PendingIntent pi = PendingIntent.getActivity(LaunchService.this, 0,
				intent, 0);

		String notifymsg = "您有" + notifynum + "条新的圈子信息,点击看看";
		// 创建一个Notification
		Notification notify = new Notification();
		// 为Notification设置图标，该图标显示在状态栏
		notify.icon = R.drawable.mba_icon;
		// 为Notification设置文本内容，该文本会显示在状态栏
		notify.tickerText = notifymsg;
		// 为Notification设置发送时间
		notify.when = System.currentTimeMillis();
		// 为Notification设置声音
		notify.defaults = Notification.DEFAULT_SOUND;
		// 为Notification设置默认声音、默认振动、默认闪光灯
		notify.defaults = Notification.DEFAULT_ALL;

		// 设置事件信息
		notify.setLatestEventInfo(LaunchService.this, notifymsg, "点击查看", pi);
		// 获取系统的NotificationManager服务
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 发送通知
		try {
			notificationManager.notify(notification_id, notify);
		} catch (Exception e) {
			e.printStackTrace();
			e.toString();
		}
	}

	// 定义发送请求的方法
	private JSONObject queryNewNotify() throws Exception {
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("do", "getNotifyNumber");
		map.put("uid", myuid);
		// 定义发送请求的URL
		// String url = HttpUtil.BASE_URL + "login.jsp";
		String url = HttpUtil.BASE_URL;
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map, 0));
	}

	private int getNewNotify() {

		JSONObject jsonObj;
		try {
			jsonObj = queryNewNotify();

			// 如果userId 大于0
			int number = jsonObj.getInt("is_read");
			if (number > 0) {
				return number;
			}
		} catch (Exception e) {
			//
			// Toast.makeText(getApplicationContext(), "服务器响应异常，请稍后再试！",
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return 0;
	}

}