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
import android.widget.ListView;
import android.widget.TextView;

import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.model.MsgInfo;
import com.tuifi.quanzi.util.FrameActivity;

public class MessageActivity extends FrameActivity implements IWeiboActivity {

	private static String LOG = "MessageActivity";
	private static final int MSG_KEY = 0x1234;
	ListView msglistview;

	public static final int CLEAR_NOTIFY = 1;
	public static final int REFRESH_MSG = 2;
	int notification_id = 20110822;

	private int nowpage = 1;
	public View process;// 加载条

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		MainService.allActivity.add(this);
		init();
		initActivity();		
		Log.d(LOG, "MessageActivity------onCreate");
	}

	private void initActivity() {

		initTopbar();
		title.setText("消息列表");
		bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		// bnrefresh.setVisibility(View.GONE);
		msglistview = (ListView) findViewById(R.id.msglistview);
		bnrefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadMsglist();
			}
		});
		bnnewmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mintent = new Intent(MessageActivity.this,
						MessageNew.class);
				startActivity(mintent);
			}
		});
		process = this.findViewById(R.id.progress);
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
			Log.i(LOG, "=======getadaptList" + adaptList.size());
			return adaptList;
		}

		public void setadaptList(List<MsgInfo> adaptList) {
			Log.i(LOG, "=======setadaptList" + adaptList.size());
			this.adaptList = adaptList;
		}

		public int getCount() {
			Log.i(LOG, "=======getCount" + adaptList.size());
			return adaptList.size();
		}

		public Object getItem(int position) {
			Log.i(LOG, "=======getItem"+ position);
			return adaptList.get(position);
		}

		public long getItemId(int position) {
			Log.i(LOG, "=======getItemId"+ position);
			return position;
		}

		public void removeAll() {
			Log.i(LOG, "=======removeAll");
			adaptList.clear();
			notifyDataSetChanged();
		}

/*		// 增加更多的数据
		public void addMoreData(List<MsgInfo> more) {
			this.adaptList.addAll(more);// 把新数据增加到原有集合
			this.notifyDataSetChanged();
		}
*/
		public View getView(int position, View convertView, ViewGroup parent) {

			Log.i(LOG, "=======getView start");
			// asyncImageLoader = new AsyncImageLoader();
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
					Log.e(LOG, e.toString());
				}
			}
			Log.i(LOG, "=======getView");
			return convertView;
		}
	}

	private void loadMsglist() {

		//msglistview.removeAllViewsInLayout();
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			Task task = new Task(Task.LOAD_MSG_LIST, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----Task.LOAD_MSG_LIST");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG, LOG + e.toString());
		}

		// readMsgListFromJson();
		// 网络读取部位空，设值
		/*
		 * if (msgList != null) { contactAdapater adapater = new
		 * contactAdapater(msgList); try { if (adapater != null) {
		 * adapater.setadaptList(msgList); msglistview.setAdapter(adapater); } }
		 * catch (Exception e) { e.printStackTrace(); } }
		 */

	}

	@Override
	public void init() {
		// 刷新
		//msglistview.removeAllViewsInLayout();
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			Task task = new Task(Task.LOAD_MSG_LIST, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----init Task.LOAD_MSG_LIST");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG, LOG + e.toString());
		}

		//
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
		// TODO Auto-generated method stub
		switch (((Integer) (param[0])).intValue()) {
		case CLEAR_NOTIFY://
			MainService.notifyid = 0;
			Log.d(LOG, "refresh CLEAR_NOTIFY");
			break;
		case REFRESH_MSG://
			/*if (nowpage == 1) {
				process.setVisibility(View.GONE);// 隐藏进度条
				List<MsgInfo> ms = (List<MsgInfo>) param[1];
				contactAdapater ma = new contactAdapater();
				ma.adaptList = ms;
				if (ms != null) {
					msglistview.setAdapter(ma);					
					Log.d(LOG, "--------REFRESH_MSG---param[1] = " + ms.size());
				} else {
					Log.d(LOG, "--------REFRESH_MSG---param[1] is null");
				}
			} 
			else {
				((contactAdapater) msglistview.getAdapter())
						.addMoreData((List<MsgInfo>) param[1]);
			}
			((contactAdapater) msglistview.getAdapter()).notifyDataSetChanged();*/
			List<MsgInfo> rs = (List<MsgInfo>) param[1];
			if (rs != null)
				mymsgList = rs;
			loadListOffer();
			Log.d(LOG, "refresh REFRESH_MSG");
			break;
		}
	}
	private void loadListOffer() {

		msglistview = (ListView) findViewById(R.id.msglistview);
		if (mymsgList != null) {
			contactAdapater adapater = new contactAdapater();
			// adapater.removeAll();
			msglistview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> lv, View view, int pos,
						long id) {
					/*contactAdapater ada = (contactAdapater) msglistview.getAdapter();
					MsgInfo mi = ada.adaptList.get(pos);
					if (mi != null) {
						try {
							Intent intent = new Intent(MessageActivity.this,PersonInfoNew.class);
							Bundle b = new Bundle();
							b.putSerializable("item", mi);
							intent.putExtras(b);
							startActivity(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}*/
				}
			});

			try {
				if (adapater != null) {
					adapater.setadaptList(mymsgList);
					msglistview.setAdapter(adapater);
					Log.i(LOG,"msglistview.setAdapter(adapater)");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
