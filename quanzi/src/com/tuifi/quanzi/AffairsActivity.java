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
import com.tuifi.quanzi.model.AffairsInfo;
import com.tuifi.quanzi.util.FrameActivity;

public class AffairsActivity extends FrameActivity implements IWeiboActivity {

	private static String LOG = "AffairsActivity";
	private static final int MSG_KEY = 0x1234;
	ListView msglistview;

	public static final int REFRESH_AFF = 2;

	public View process;// 加载条

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		MainService.allActivity.add(this);
		init();
		initActivity();		
		Log.d(LOG, "AffairsActivity------onCreate");
	}

	private void initActivity() {

		initTopbar();
		title.setText("活动通知");
		bnback.setVisibility(View.GONE);
	    bnnewmsg.setVisibility(View.GONE);
		// bnrefresh.setVisibility(View.GONE);
		msglistview = (ListView) findViewById(R.id.msglistview);
		bnrefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				init();
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
		
		public List<AffairsInfo> adaptList = new ArrayList<AffairsInfo>();

		public List<AffairsInfo> getadaptList() {
			Log.i(LOG, "=======getadaptList" + adaptList.size());
			return adaptList;
		}

		public void setadaptList(List<AffairsInfo> adaptList) {
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
		public void addMoreData(List<AffairsInfo> more) {
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

			AffairsInfo oi = adaptList.get(position);

			if (oi != null) {
				convertView.setTag(oi.getid());
				try {
					oh.msgitemcontent.setText(oi.getName());
					oh.msgitemsender.setText(oi.getdeadtime());
					oh.msgitemtime.setText("");
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(LOG, e.toString());
				}
			}
			Log.i(LOG, "=======getView");
			return convertView;
		}
	}

	@Override
	public void init() {
		// 刷新
		//msglistview.removeAllViewsInLayout();
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			Task task = new Task(Task.LOAD_AFFAIRS, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----init Task.LOAD_AFFAIRS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG, LOG + e.toString());
		}		
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) (param[0])).intValue()) {
		case REFRESH_AFF://
				process.setVisibility(View.GONE);// 隐藏进度条
				List<AffairsInfo> ms = (List<AffairsInfo>) param[1];
				contactAdapater ma = new contactAdapater();
				ma.adaptList = ms;
				if (ms != null) {
					msglistview.setAdapter(ma);					
					Log.d(LOG, "--------REFRESH_MSG---param[1] = " + ms.size());
				} else {
					Log.d(LOG, "--------REFRESH_MSG---param[1] is null");
				}

			((contactAdapater) msglistview.getAdapter()).notifyDataSetChanged();
			List<AffairsInfo> rs = (List<AffairsInfo>) param[1];
			if (rs != null)
				myaffList = rs;
			loadListOffer();
			Log.d(LOG, "refresh REFRESH_MSG");
			break;
		}
	}
	private void loadListOffer() {
		msglistview = (ListView) findViewById(R.id.msglistview);
		if (myaffList != null) {
			contactAdapater adapater = new contactAdapater();
			// adapater.removeAll();
			msglistview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> lv, View view, int pos,
						long id) {
					contactAdapater ada = (contactAdapater) msglistview.getAdapter();
					AffairsInfo mi = ada.adaptList.get(pos);
					if (mi != null) {
						try {
							Intent intent = new Intent(AffairsActivity.this,AffItemActivity.class);
							Bundle b = new Bundle();
							b.putSerializable("item", mi.getaid());
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
					adapater.setadaptList(myaffList);
					msglistview.setAdapter(adapater);
					Log.i(LOG,"msglistview.setAdapter(adapater)");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
