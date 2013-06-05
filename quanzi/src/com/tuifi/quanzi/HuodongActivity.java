package com.tuifi.quanzi;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.model.HuodongInfo;
import com.tuifi.quanzi.util.FrameActivity;
import com.tuifi.quanzi.util.MyActivity;

public class HuodongActivity extends FrameActivity implements IWeiboActivity{

	ListView msglistview;
	BaseAdapter adapter = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		initActivity();		
		MainService.allActivity.add(this);
		loadMsglist();
		
	}
	private void initActivity()
	{
		initTopbar();		
		//
		bnback.setVisibility(View.GONE);
		//bnnewmsg.setVisibility(View.GONE);	
		//bnrefresh.setVisibility(View.GONE);
		title.setText("活动列表");	
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
				Intent mintent = new Intent(HuodongActivity.this, HuodongNew.class);
				startActivity(mintent);
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

			public List<HuodongInfo> adaptList = new ArrayList<HuodongInfo>();

			public List<HuodongInfo> getadaptList() {
				return adaptList;
			}

			public void setadaptList(List<HuodongInfo> adaptList) {
				this.adaptList = adaptList;
			}

			public int getCount() {
				return adaptList.size();
			}

			public Object getItem(int position) {
				return adaptList.get(position);
			}

			public long getItemId(int position) {
				return position;
			}

			public void removeAll() {
				adaptList.clear();
				notifyDataSetChanged();
			}

			public View getView(int position, View convertView, ViewGroup parent) {

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

				HuodongInfo oi = adaptList.get(position);

				if (oi != null) {
					convertView.setTag(oi.getID());
					try {
						oh.msgitemcontent.setText(oi.getdetail());
						oh.msgitemsender.setText(oi.getcuid());
						oh.msgitemtime.setText(oi.getftime());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return convertView;
			}
		}

		private void loadMsglist() {

			msglistview.removeAllViewsInLayout();
			//readHuodongFromJson();
			// 网络读取部位空，设值
			if (myhuodongList != null) {
				contactAdapater adapater = new contactAdapater();
				try {
					if (adapater != null) {
						adapater.setadaptList(myhuodongList);
						msglistview.setAdapter(adapater);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		@Override
		public void init() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void refresh(Object... param) {
			// TODO Auto-generated method stub
			
		}

}