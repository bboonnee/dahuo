package com.tuifi.quanzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tuifi.quanzi.controller.MsgController;
import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.model.MsgInfo;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.util.FrameActivity;

/*
 * yibo 2011-11-15
 * 显示msg的列表
 * 按照圈子的顺序显示
 * 
 */
public class MsgListActivity extends FrameActivity implements IWeiboActivity {

	private static final String LOG = "MsgListActivity";
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
		bnnewmsg.setVisibility(View.GONE);
		// bnrefresh.setVisibility(View.GONE);
		plist = (ListView) findViewById(R.id.msglistview);

		bnnewmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent mintent = new Intent(MsgListActivity.this,
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

	// ////显示msg list
	public class MessageHolder {
		public ImageView listImage;
		public TextView listName;
		public TextView listTime;
		public TextView listMsg;
	}

	// 列表Adapater
	public class contactAdapater extends BaseAdapter {

		public List<QuanziInfo> adaptList = new ArrayList<QuanziInfo>();

		public List<QuanziInfo> getadaptList() {
			return adaptList;
		}

		public void setadaptList(List<QuanziInfo> adaptList) {
			Log.i(LOG, "setadaptList");
			this.adaptList = adaptList;
		}

		public int getCount() {
			Log.i(LOG, "getCount" + adaptList.size());
			return adaptList.size();
		}

		public Object getItem(int position) {
			Log.i(LOG, "getItem" + position);
			return adaptList.get(position);
		}

		public long getItemId(int position) {
			Log.i(LOG, "getItemId" + position);
			return position;
		}

		public void removeAll() {
			Log.i(LOG, "removeAll");
			adaptList.clear();
			notifyDataSetChanged();
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			Log.i(LOG, "getView");
			convertView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.msglistitem, null);
			MessageHolder oh = new MessageHolder();
			oh.listImage = (ImageView) convertView
					.findViewById(R.id.imagemsglist);
			// iv.setImageDrawable(user.getUserIcon());
			oh.listMsg = (TextView) convertView
					.findViewById(R.id.msgitemcontent);
			oh.listName = (TextView) convertView
					.findViewById(R.id.msgitemsender);
			oh.listTime = (TextView) convertView.findViewById(R.id.msgitemtime);

			QuanziInfo oi = adaptList.get(position);

			if (oi != null) {
				convertView.setTag(oi.getID());
				try {
					if(oi.getIcon()!=null)
					oh.listImage.setImageDrawable(oi.getIcon());
					List<MsgInfo> list = MsgController.loadquanzimsg(oi.getID());
					oh.listName.setText("圈子："+oi.getname());
					oh.listTime.setText("消息："+list.size()+"条");					
					String last = "";					
					if (list.size()>0) 
						{
						String username = UserController.getNameFromUid(list.get(0).getsenduid());
						String content = list.get(0).getcontent();
							last = username+":"+content;
						}
					oh.listMsg.setText(last);
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
		if (myquanziList != null) {
			contactAdapater adapater = new contactAdapater();
			// adapater.removeAll();
			plist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> lv, View view, int pos,
						long id) {

					contactAdapater ada = (contactAdapater) plist.getAdapter();
					QuanziInfo ui = ada.adaptList.get(pos);
					//
					if (ui != null) {
						//
						try {
							// Intent intent = new Intent(QuanziActivity.this,
							// PersonInfo.class);
							Intent intent = new Intent(MsgListActivity.this,
									ChatActivity.class);
							Bundle b = new Bundle();
							b.putSerializable("item", ui.getID());
							intent.putExtras(b);
							startActivity(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});

			try {
				if (adapater != null) {
					adapater.setadaptList(myquanziList);
					plist.setAdapter(adapater);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void refreshTask() {
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
			Log.i(LOG, "refreshTask ERROR:" + e.toString());
		}
	}

	@Override
	public void init() {

		refreshTask();
		loadListOffer();

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
		if (param == null)
			return;

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
			/*
			 * MsgController mc = new MsgController(); mymsgList =
			 * mc.loadList(this, myuid, myquanziList,0);
			 */
			Log.d(LOG, LOG + "-----refresh LOAD_QUANZI_LIST");
			break;

		default:
			break;
		}
	}

}