package com.tuifi.quanzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tuifi.quanzi.controller.MsgController;
import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.model.MsgInfo;
import com.tuifi.quanzi.util.FrameActivity;

public class MsgActivity extends FrameActivity implements IWeiboActivity {

	private static final String LOG = "MsgActivity";
	private static final int MSG_KEY = 0x1234;
	ListView plist;		
	MsgController control = new MsgController();
	public static final int CLEAR_NOTIFY = 1;
	public static final int REFRESH_MSG = 2;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		MainService.allActivity.add(this);
		init();
		initActivity();
	}

	private void initActivity() {
		initTopbar();
		title.setText("消息列表");
		bnback.setVisibility(View.GONE);
		// bnnewmsg.setVisibility(View.GONE);
		// bnrefresh.setVisibility(View.GONE);
		plist = (ListView) findViewById(R.id.msglistview);		
		plist.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub

				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d(LOG, LOG + "------------plist onTouch ACTION_DOWN");
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

					imm.hideSoftInputFromWindow(plist.getWindowToken(), 0);

				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Log.d(LOG, LOG + "------------plist onTouch ACTION_UP");

				} else if (arg1.getAction() == MotionEvent.ACTION_MASK) {
					Log.d(LOG, LOG + "------------plist onTouch ACTION_MASK");

				} else if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
					Log.d(LOG, LOG + "------------plist onTouch ACTION_MOVE");

				} else if (arg1.getAction() == MotionEvent.ACTION_OUTSIDE) {
					Log.d(LOG, LOG + "------------plist onTouch ACTION_OUTSIDE");

				} else if (arg1.getAction() == MotionEvent.ACTION_POINTER_1_DOWN) {
					Log.d(LOG, LOG
							+ "------------plist onTouch ACTION_POINTER_1_DOWN");

				}

				return false;
			}

		});
		bnnewmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent mintent = new Intent(MsgActivity.this,
						MessageNew.class);
				startActivity(mintent);
			}
		});		
		bnrefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				init();
			}
		});
	}

	// ////
	public class MessageHolder {

		public TextView msgitemsender;
		public TextView msgitemtime;
		public TextView msgitemcontent;

	}

	// 微博列表Adapater
	public class contactAdapater extends BaseAdapter {

		public List<MsgInfo> adaptList = new ArrayList<MsgInfo>();

		public List<MsgInfo> getadaptList() {
			return adaptList;
		}

		public void setadaptList(List<MsgInfo> adaptList) {
			Log.i(LOG,"setadaptList");
			this.adaptList = adaptList;
		}

		public int getCount() {
			Log.i(LOG,"getCount"+ adaptList.size());
			return adaptList.size();
		}

		public Object getItem(int position) {
			Log.i(LOG,"getItem"+ position);
			return adaptList.get(position);
		}

		public long getItemId(int position) {
			Log.i(LOG,"getItemId"+ position);
			return position;
		}

		public void removeAll() {
			Log.i(LOG,"removeAll");
			adaptList.clear();
			notifyDataSetChanged();
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			Log.i(LOG,"getView");			
			convertView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.msgitem, null);
			MessageHolder oh = new MessageHolder();
			oh.msgitemcontent = (TextView) convertView
					.findViewById(R.id.msgitemcontent);
			oh.msgitemsender = (TextView) convertView
					.findViewById(R.id.msgitemsender);
			oh.msgitemtime = (TextView) convertView
					.findViewById(R.id.msgitemtime);

			MsgInfo oi = adaptList.get(position);

			if (oi != null) {
				convertView.setTag(oi.getid());
				try {
					oh.msgitemcontent.setText(oi.getcontent());
					oh.msgitemsender.setText(oi.getsenduid());
					oh.msgitemtime.setText(oi.getctime());		
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return convertView;
		}
	}

	private void loadListOffer() {

		plist = (ListView) findViewById(R.id.msglistview);
		plist.removeAllViewsInLayout();
		if (mymsgList != null) {
			contactAdapater adapater = new contactAdapater();
			// adapater.removeAll();
			plist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> lv, View view, int pos,
						long id) {
					// User ui = mymsgList.get(pos);
					contactAdapater ada = (contactAdapater) plist.getAdapter();
					MsgInfo ui = ada.adaptList.get(pos);
					// Object obj = view.getTag();
					if (ui != null) {
						// String id = obj.toString();
						try {

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});

			try {
				if (adapater != null) {
					adapater.setadaptList(mymsgList);
					plist.setAdapter(adapater);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void refreshTask()
	{
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			param.put("myuid", myuid);
			Task task = new Task(Task.LOAD_MSG_LIST, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----Task.LOAD_MSG_LIST myuid =" + myuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void init() {
		// 
		refreshTask();
		if (mymsgList == null) {
			refreshTask();
		} else {
			loadListOffer();
		}
		int notification_id = 20110822;
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			param.put("notification_id", notification_id);
			Task task = new Task(Task.TASK_CLEAR_NOTIFY, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----Task.TASK_CLEAR_NOTIFY");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG, LOG + "---TASK_CLEAR_NOTIFY------" + e.toString());
		}
	}

	@Override
	public void refresh(Object... param) {

		if(param==null) return;
			
		int pa = ((Integer) (param[0])).intValue();
		switch (pa) {
		case CLEAR_NOTIFY://
			MainService.notifyid = 0;
			Log.d(LOG, "refresh CLEAR_NOTIFY");
			break;
		case REFRESH_MSG:// 更新微博列表
			List<MsgInfo> rs = (List<MsgInfo>) param[1];
			if (rs != null)
				mymsgList = rs;
			loadListOffer();
			// mymsgList = uc.loadList(this, Integer.toString(myuid), 0);
			Log.d(LOG, LOG + "-----refresh LOAD_QUANZI_LIST");
			break;

		default:
			break;
		}
	}

}