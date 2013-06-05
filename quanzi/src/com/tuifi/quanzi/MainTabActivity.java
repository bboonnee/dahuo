package com.tuifi.quanzi;

import java.util.HashMap;
import java.util.List;

import android.app.ActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.util.MyActivity;

public class MainTabActivity extends TabActivity implements IWeiboActivity {
	private static String LOG = "MainTabActivity";
	public TabHost mth;
	private String mainuid = MainService.msuid;
	private String mainmobile = MainService.msmobile;
	public static final String TAB_HOME = "TabHome";
	public static final String TAB_MSG = "TabMSG";
	public static final String TAB_HUODONG = "TabHuodong";
	public static final String TAB_CONTACT = "TabContact";
	public static final String TAB_MORE = "TabMore";

	public static final int QUIT_DIALOG = 2;
	public RadioGroup mainbtGroup;
	private RadioButton[] mRadioButtons;
	public static int RaidoTabSelected = 1;

	@Override
	public void init() {
		try {

			MyActivity.myuserList = UserController.loadList(this, mainuid, 0);
			UserController.userList = MyActivity.myuserList;

			if (mainuid.equals("")) {
				mainuid = UserController.getMyuid(this, mainmobile);
				MainService.msuid = mainuid;
			}

			Log.i(LOG, "finish reading myuserList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 如果没有启动查询服务，则启动服务
		servicestart();
/*		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			param.put("myuid", mainuid);
			Task task = new Task(Task.LOAD_QUANZI_LIST, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----Task.LOAD_QUANZI_LIST");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	public void servicestart() {
		
		Intent it=new Intent("com.tuifi.quanzi.logic.MainService");
		this.startService(it);
		
		ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager
				.getRunningServices(30);
		// 我要判断的服务名字，我在launcher2里加了一个音乐服务
		final String ClassName = "com.tuifi.quanzi.LaunchService";
		boolean b = ServiceIsStart(mServiceList, ClassName);
		if (!b) {
			Intent tIntent = new Intent(this, LaunchService.class);
			// 启动指定Service
			startService(tIntent);
		}		

	}
	// 通过Service的类名来判断是否启动某个服务
	private boolean ServiceIsStart(
			List<ActivityManager.RunningServiceInfo> mServiceList,
			String className) {

		for (int i = 0; i < mServiceList.size(); i++) {
			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.maintabs);
		mth = this.getTabHost();
		init();
		// 创建2个子页
		TabSpec ts1 = mth.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
		ts1.setContent(new Intent(this, HomeActivity.class));
		mth.addTab(ts1);
		TabSpec ts2 = mth.newTabSpec(TAB_HUODONG).setIndicator(TAB_HUODONG);
		ts2.setContent(new Intent(this, AffairsActivity.class));
		mth.addTab(ts2);
		TabSpec ts3 = mth.newTabSpec(TAB_MSG).setIndicator(TAB_MSG);
		ts3.setContent(new Intent(this, MsgListActivity.class));
		mth.addTab(ts3);
		TabSpec ts4 = mth.newTabSpec(TAB_CONTACT).setIndicator(TAB_CONTACT);
		ts4.setContent(new Intent(this, ContactActivity.class));
		mth.addTab(ts4);
		TabSpec ts5 = mth.newTabSpec(TAB_MORE).setIndicator(TAB_MORE);
		ts5.setContent(new Intent(this, MoreActivity.class));
		mth.addTab(ts5);
		//
		mRadioButtons = new RadioButton[5];
		//mRadioButtons[0] = (RadioButton) findViewById(R.id.radio_button0);
		mRadioButtons[1] = (RadioButton) findViewById(R.id.radio_button1);
		mRadioButtons[2] = (RadioButton) findViewById(R.id.radio_button2);
		mRadioButtons[3] = (RadioButton) findViewById(R.id.radio_button3);
		//mRadioButtons[4] = (RadioButton) findViewById(R.id.radio_button4);
		// 得到RadioGroup对象
		this.mainbtGroup = (RadioGroup) this.findViewById(R.id.main_radio);
		mainbtGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				/*case R.id.radio_button0:// 圈子
					mth.setCurrentTabByTag(TAB_HOME);
					break;*/
				case R.id.radio_button1:// 活动
					mth.setCurrentTabByTag(TAB_HUODONG);
					break;
				case R.id.radio_button2:// 信息
					mth.setCurrentTabByTag(TAB_MSG);
					break;
				case R.id.radio_button3:// 联系人
					mth.setCurrentTabByTag(TAB_CONTACT);
					break;
				/*case R.id.radio_button4:// 更多
					mth.setCurrentTabByTag(TAB_MORE);
					break;*/
				}
			}
		});
		// 默认主页
		switchMode(RaidoTabSelected);
		getNotify();

	}

	private void getNotify() {
		try {
			// uid = preferences.getInt("uid", 0);
			// 获取启动该Result的Intent
			Intent intent = getIntent();
			// 获取该intent所携带的数据
			Bundle data = intent.getExtras();
			// 从Bundle数据包中取出数据
			if (data != null) {
				RaidoTabSelected = data.getInt("RaidoTabSelected");
				int notifyid = data.getInt("notifyid");
				if (notifyid > 0)
					MainService.notifyid = notifyid;
				else
					MainService.notifyid = 0;
				switchMode(0);
				switchMode(RaidoTabSelected);
				Log.i(LOG, "have the intent data from Lauch Service" + notifyid);
			} else {
				RaidoTabSelected = 0;
				switchMode(RaidoTabSelected);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

	private void switchMode(int paramInt) {

		mRadioButtons[paramInt].toggle();
		mRadioButtons[paramInt].setSelected(true);
	}

}
