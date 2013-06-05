package com.tuifi.quanzi.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.tuifi.quanzi.AffairsActivity;
import com.tuifi.quanzi.ContactActivity;
import com.tuifi.quanzi.HomeActivity;
import com.tuifi.quanzi.MsgListActivity;
import com.tuifi.quanzi.R;
import com.tuifi.quanzi.controller.AffairsController;
import com.tuifi.quanzi.controller.HuodongController;
import com.tuifi.quanzi.controller.ModelController;
import com.tuifi.quanzi.controller.MsgController;
import com.tuifi.quanzi.controller.NotifyController;
import com.tuifi.quanzi.controller.QuanziController;
import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.controller.UserInfoController;
import com.tuifi.quanzi.model.QuanziInfo;

public class MainService extends Service implements Runnable {
	private static String LOG = "MainService";
	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();// ��������Activity
	public static int lastActivityId;// ����ǰ��Activity���
	// �����û�ͷ��
	public static HashMap<Integer, BitmapDrawable> alluserIcon = new HashMap<Integer, BitmapDrawable>();
	// ����Ȧ����Ƭ
	public static HashMap<Integer, BitmapDrawable> allQuanziIcon = new HashMap<Integer, BitmapDrawable>();

	public static ModelController mc = new ModelController();
	public static QuanziController qmc = new QuanziController();
	public static HuodongController hdmc = new HuodongController();
	public static UserController umc = new UserController();
	public static UserInfoController uimc = new UserInfoController();
	public static MsgController msgmc = new MsgController();
	public static AffairsController affmc = new AffairsController();
	public static String msuid = "";
	public static String msmobile = "";
	public static List<QuanziInfo> msqzList;
	public static int notifyid = 0;
	public static int runtime = 0;

	// �Ӽ�����ͨ��name��ȡActivity����
	public static Activity getActivityByName(String name) {
		for (Activity ac : allActivity) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				return ac;
			}
		}
		return null;
	}

	public static ArrayList<Task> allTask = new ArrayList<Task>();

	// ���
	public static void newTask(Task task) {
		allTask.add(task);
	}

	public boolean isrun = true;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	// ����UI
	private Handler hand = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			//
			case Task.TASK_CLEAR_NOTIFY:
				IWeiboActivity ia1 = (IWeiboActivity) MainService
						.getActivityByName("MsgListActivity");
				ia1.refresh(new Integer(MsgListActivity.CLEAR_NOTIFY), msg.obj);
				Log.d(LOG, "handleMessage------TASK_CLEAR_NOTIFY");
				break;

			case Task.LOAD_QUANZI_LIST:
				IWeiboActivity ia2 = (IWeiboActivity) MainService
						.getActivityByName("HomeActivity");
				ia2.refresh(new Integer(HomeActivity.LOAD_QUANZI_LIST), msg.obj);
				Log.d(LOG, "handleMessage------LOAD_QUANZI_LIST");
				break;
			// ��ȡ�û��б�
			case Task.LOAD_USER_LIST:
				IWeiboActivity ia3 = (IWeiboActivity) MainService
						.getActivityByName("ContactActivity");
				if (msg.obj != null)
					ia3.refresh(new Integer(ContactActivity.LOAD_USER_LIST),
							msg.obj);
				else
					Log.i(LOG,
							"handleMessage------LOAD_USER_LIST msg.obj is null");
				Log.d(LOG, "handleMessage------LOAD_USER_LIST");
				break;
			// ˢ���û��б�
			case Task.REFRESH_USER_LIST:
				IWeiboActivity iaul = (IWeiboActivity) MainService
						.getActivityByName("ContactActivity");
				if (msg.obj != null)
					iaul.refresh(new Integer(ContactActivity.LOAD_USER_LIST),
							msg.obj);
				else
					Log.i(LOG,
							"handleMessage------LOAD_USER_LIST msg.obj is null");
				Log.d(LOG, "handleMessage------LOAD_USER_LIST");
				break;
			case Task.REFRESH_USER_INFO_LIST:
				IWeiboActivity ia4 = (IWeiboActivity) MainService
						.getActivityByName("ContactActivity");
				ia4.refresh(new Integer(ContactActivity.REFRESH_USER_INFO_LIST), msg.obj);
				Log.d(LOG, "handleMessage------REFRESH_USER_INFO_LIST");
				break;
			case Task.LOAD_HUODONG_LIST:
				IWeiboActivity ia5 = (IWeiboActivity) MainService
						.getActivityByName("HomeActivity");
				ia5.refresh(new Integer(HomeActivity.LOAD_QUANZI_LIST), msg.obj);
				Log.d(LOG, "handleMessage------LOAD_QUANZI_LIST");
				break;
			case Task.LOAD_MSG_LIST:
				IWeiboActivity ia6 = (IWeiboActivity) MainService
						.getActivityByName("MsgListActivity");
				ia6.refresh(new Integer(MsgListActivity.REFRESH_MSG), msg.obj);
				Log.d(LOG, "handleMessage------REFRESH_MSG");
				break;
			case Task.LOAD_AFFAIRS:
				IWeiboActivity ia7 = (IWeiboActivity) MainService
						.getActivityByName("AffairsActivity");
				ia7.refresh(new Integer(AffairsActivity.REFRESH_AFF), msg.obj);
				Log.d(LOG, "handleMessage------LOAD_AFFAIRS");
				break;				
			// ˢ���û��б�
			case Task.REFRESH_USER_ICON:
				IWeiboActivity iaulicon = (IWeiboActivity) MainService
						.getActivityByName("ContactActivity");
				if (msg.obj != null)
					iaulicon.refresh(new Integer(
							ContactActivity.REFRESH_USER_ICON), msg.obj);
				else
					Log.i(LOG,
							"handleMessage------REFRESH_USER_ICON msg.obj is null");
				Log.d(LOG, "handleMessage------REFRESH_USER_ICON");
				break;
			}

		}

	};

	// ִ������
	public void doTask(Task task) {
		Message mess = hand.obtainMessage();
		mess.what = task.getTaskID();// ����ˢ��UI�ı仯
		try {
			Log.d(LOG, "doTask-----task.getTaskID()=" + task.getTaskID());
			switch (task.getTaskID()) {
			// ���notify��Ϣ
			case Task.TASK_CLEAR_NOTIFY:
				int notification_id = (Integer) task.getTaskParam().get(
						"notification_id");
				NotifyController nc = new NotifyController();
				boolean rs = nc.clearNotifyNumber(msuid);
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.cancel(notification_id);
				mess.obj = rs;
				break;
			// ��ȡȦ���б�
			case Task.LOAD_QUANZI_LIST:
				QuanziController.qzList = qmc.loadqzList(this, msuid, 0);
				mess.obj = qmc.qzList;
				Log.d(LOG, LOG + "-----doTask LOAD_QUANZI_LIST" + msuid);
				break;
			// ��ȡ�û��б�
			case Task.LOAD_USER_LIST:
				UserController.userList = UserController.loadList(this, msuid,
						0);
				mess.obj = UserController.userList;
				if (mess.obj == null)
					Log.d(LOG, LOG + "-----doTask LOAD_USER_LIST "
							+ UserController.userList.size());
				break;
			// ˢ���б�
			case Task.REFRESH_USER_LIST:
				UserController.userList = UserController.loadList(this, msuid,
						1);
				mess.obj = UserController.userList;
				if (mess.obj == null)
					Log.d(LOG, LOG + "-----doTask LOAD_USER_LIST "
							+ UserController.userList.size());
				break;
			// ˢ��user icon
			case Task.REFRESH_USER_ICON:
				UserController.userList = UserController
						.downUserIcon(this,UserController.userList);
				mess.obj = UserController.userList;
				if (mess.obj == null)
					Log.d(LOG, LOG + "-----doTask REFRESH_USER_ICON "
							+ UserController.userList.size());
				break;
			// ��ȡȦ���б�
			case Task.REFRESH_USER_INFO_LIST:
				UserInfoController.infoList = uimc.loadUiList(this, "", 1);
				mess.obj = qmc.qzList;
				break;
			// ��ȡȦ���б�
			case Task.LOAD_HUODONG_LIST:
				HuodongController.huodongList = hdmc.readFromJson(this, msuid);
				mess.obj = qmc.qzList;
				break;
			// ��ȡ
			case Task.LOAD_MSG_LIST:
				MsgController.MsgList = msgmc.loadList(this, msuid, msqzList, 0);
				mess.obj = MsgController.MsgList;
				Log.d(LOG, LOG + "-----doTask LOAD_MSG_LIST "
						+ MsgController.MsgList.size());
				break;	
				// ��ȡ
				case Task.LOAD_MSG_CHAT:
					MsgController.MsgList = msgmc.loadList(this, msuid, msqzList, 0);
					mess.obj = MsgController.MsgList;
					Log.d(LOG, LOG + "-----doTask LOAD_MSG_LIST "
							+ MsgController.MsgList.size());
					break;					
			// ��ȡ
			case Task.LOAD_AFFAIRS:
				AffairsController.AffairsList = affmc.loadList(this, 0);
				mess.obj = AffairsController.AffairsList;
				Log.d(LOG, LOG + "-----doTask LOAD_AFFAIRS "
						+ AffairsController.AffairsList.size());
				break;
			}			
		} catch (Exception e) {
			mess.what = -100;
		}
		hand.sendMessage(mess);// ���͸���UI����Ϣ�����߳�
		allTask.remove(task);// ִ��������
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isrun) {
			Task lastTask = null;
			synchronized (allTask) {
				if (allTask.size() > 0) {// ȡ����
					lastTask = allTask.get(0);
					// ִ������
					doTask(lastTask);
				}
			}
			//120��ˢ��chat
			if (runtime % 120 == 0)
			{
				String actName = getActivityName();
				//chat refresh ������ڵĻ��chat����ˢ��
				if(actName.equals("actName.ChatActivity"))
				{
					try {
						// ��ȡChatActivity LOAD_QUANZI_MSG
						HashMap param = new HashMap();
						Task task = new Task(Task.LOAD_MSG_CHAT, param);
						MainService.newTask(task);
						Log.d(LOG, LOG + "-----init Task.LOAD_QUANZI_MSG");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(LOG, LOG + e.toString());
					}		
					Log.i(LOG, ".....Timer running " +" actName"+actName);
				}
				Log.d(LOG, LOG + "............." + runtime);
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(LOG, e.toString());
			}
			runtime++;
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		isrun = true;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isrun = false;
	}

	// alertUser ��ʾ�û�����״̬����
	public static void AlertNetError(final Context con) {
		AlertDialog.Builder ab = new AlertDialog.Builder(con);
		ab.setTitle(com.tuifi.quanzi.R.string.NoRouteToHostException);
		ab.setMessage(com.tuifi.quanzi.R.string.NoSignalException);
		ab.setNegativeButton(com.tuifi.quanzi.R.string.apn_is_wrong1_exit,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						exitApp(con);
					}

				});
		ab.setPositiveButton(com.tuifi.quanzi.R.string.apn_is_wrong1_setnet,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						con.startActivity(new Intent(
								android.provider.Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		ab.create().show();
	}

	public static void exitApp(Context con) {
		for (Activity ac : allActivity) {
			ac.finish();
		}
		//
		Intent it = new Intent("com.tuifi.quanzi.logic.MainService");
		con.stopService(it);
	}

	public static void promptExit(final Context con) {
		// �����Ի���
		LayoutInflater li = LayoutInflater.from(con);
		View exitV = li.inflate(R.layout.exitdialog, null);
		AlertDialog.Builder ab = new AlertDialog.Builder(con);
		ab.setView(exitV);// �趨�Ի�����ʾ��View����
		ab.setPositiveButton(com.tuifi.quanzi.R.string.exit,
				new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						exitApp(con);
					}
				});
		ab.setNegativeButton(com.tuifi.quanzi.R.string.cancel, null);
		// ��ʾ�Ի���
		ab.show();
	}
	private String getActivityName()
	{
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);   
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  		
		Log.d("", "pkg:"+cn.getPackageName());   
		Log.d("", "cls:"+cn.getClassName());   
		return cn.getShortClassName();

	}
}