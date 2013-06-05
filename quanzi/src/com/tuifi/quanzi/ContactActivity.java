package com.tuifi.quanzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.model.User;
import com.tuifi.quanzi.model.UserInfo;
import com.tuifi.quanzi.util.AsyncImageLoader;
import com.tuifi.quanzi.util.AsyncImageLoader.ImageCallback;
import com.tuifi.quanzi.util.FrameActivity;

public class ContactActivity extends FrameActivity implements IWeiboActivity {

	private static final String LOG = "ContactActivity";
	private static final int MSG_KEY = 0x1234;
	Button bnlist, bnpic, bnprofile, bnexit;
	ListView plist;
	AsyncImageLoader asyncImageLoader;
	MultiAutoCompleteTextView multiAutoCompleteTextView1;
	UserController uc = new UserController();
	private Handler handler;
	public static final int LOAD_USER_LIST = 1;
	public static final int REFRESH_USER_LIST = 2;
	public static final int REFRESH_USER_ICON = 3;
	public static final int REFRESH_USER_INFO_LIST = 4;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listcontact);
		MainService.allActivity.add(this);
		init();
		initActivity();
	}

	private void initActivity() {
		initTopbar();
		title.setText("联系人");
		bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);

		//
		multiAutoCompleteTextView1 = (MultiAutoCompleteTextView) findViewById(R.id.multitv);
		multiAutoCompleteTextView1.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable editer) {
				Log.d(LOG, "afterTextChanged");
			}

			public void beforeTextChanged(CharSequence value, int arg0,
					int arg1, int arg2) {
				Log.d(LOG, "beforeTextChanged");
			}

			public void onTextChanged(CharSequence value, int arg0, int arg1,
					int arg2) {
				Log.d(LOG, "onTextChanged");
				Log.w(LOG, "input.text=" + value.toString());
				Message msg = new Message();
				msg.what = MSG_KEY;
				Bundle data = new Bundle();
				data.putString("value", value.toString());
				msg.setData(data);
				handler.sendMessage(msg);
			}
		});
		plist = (ListView) findViewById(R.id.plist);
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

		plist = (ListView) findViewById(R.id.plist);
		bnrefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshUserlist();
			}
		});
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_KEY:
					refreshListView(msg.getData().get("value").toString());
				}
			}
		};
	}

	private void refreshListView(String value) {
		contactAdapater ctadp = new contactAdapater();
		if (value == null || value.trim().length() == 0) {
			ctadp.setadaptList(myuserList);
			plist.setAdapter(ctadp);
			return;
		}
		List<User> tmpList = new ArrayList<User>();
		for (User user : myuserList) {
			if (user.getuname().indexOf(value) >= 0) {
				tmpList.add(user);
				continue;
			}
			/*
			 * if (user.getuename().indexOf(value) >= 0) { tmpList.add(user);
			 * continue; } if (user.getcompany().indexOf(value) >= 0) {
			 * tmpList.add(user); continue; }
			 */
			if (user.getMobile().indexOf(value) >= 0) {
				tmpList.add(user);
				continue;
			}
			if (user.getemail().indexOf(value) >= 0) {
				tmpList.add(user);
				continue;
			}
			/*
			 * if (user.gettitle().indexOf(value) >= 0) { tmpList.add(user);
			 * continue; } if (user.getLocation().indexOf(value) >= 0) {
			 * tmpList.add(user); continue; } if (user.gethobby().indexOf(value)
			 * >= 0) { tmpList.add(user); continue; }
			 */

		}
		// if (tmpList.size() == 0) return;
		ctadp.setadaptList(tmpList);
		plist.setAdapter(ctadp);
		plist.invalidateViews();
	}

	// ////
	public class ContactHolder {
		public ImageView chusericon;
		public TextView chname;
		public TextView chclass;
		public TextView chmobile;
		public TextView chlocatoin;

	}

	// 微博列表Adapater
	public class contactAdapater extends BaseAdapter {

		public List<User> adaptList = new ArrayList<User>();

		public List<User> getadaptList() {
			return adaptList;
		}

		public void setadaptList(List<User> adaptList) {
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
			asyncImageLoader = new AsyncImageLoader();
			convertView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.contact, null);
			ContactHolder oh = new ContactHolder();
			oh.chclass = (TextView) convertView.findViewById(R.id.tvclass);
			oh.chlocatoin = (TextView) convertView
					.findViewById(R.id.tvlocation);
			oh.chmobile = (TextView) convertView.findViewById(R.id.tvmobile);
			oh.chname = (TextView) convertView.findViewById(R.id.tvname);
			oh.chusericon = (ImageView) convertView.findViewById(R.id.usericon);

			User oi = adaptList.get(position);

			if (oi != null) {
				convertView.setTag(oi.getuid());
				try {
					// oh.chclass.setText(oi.get());
					oh.chlocatoin.setText(oi.getemail());
					oh.chmobile.setText(oi.getMobile());
					oh.chname.setText(oi.getuname());
					if(oi.getUserIcon()!=null)
					oh.chusericon.setImageDrawable(oi.getUserIcon());
					/*Drawable cachedImage = asyncImageLoader.loadDrawable(
							oi.getimageurl(), oh.chusericon,
							new ImageCallback() {
								public void imageLoaded(Drawable imageDrawable,
										ImageView imageView, String imageUrl) {
									imageView.setImageDrawable(imageDrawable);
								}
							});
					if (cachedImage == null) {
						oh.chusericon.setImageResource(R.drawable.usericon);
					} else {
						oh.chusericon.setImageDrawable(cachedImage);
					}*/

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return convertView;
		}
	}

	private void loadListOffer() {

		plist = (ListView) findViewById(R.id.plist);
		if (myuserList != null) {
			contactAdapater adapater = new contactAdapater();
			// adapater.removeAll();
			plist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> lv, View view, int pos,
						long id) {
					// User ui = myuserList.get(pos);
					contactAdapater ada = (contactAdapater) plist.getAdapter();
					User ui = ada.adaptList.get(pos);
					// Object obj = view.getTag();
					if (ui != null) {
						// String id = obj.toString();
						try {
							// Intent intent = new Intent(QuanziActivity.this,
							// PersonInfo.class);
							Intent intent = new Intent(ContactActivity.this,
									PersonInfoNew.class);
							Bundle b = new Bundle();
							b.putSerializable("item", ui.getuid());
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
					adapater.setadaptList(myuserList);
					plist.setAdapter(adapater);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if (myuserList == null) {
			try {
				// 获取系统的NotificationManager服务
				HashMap param = new HashMap();
				param.put("myuid", myuid);
				Task task = new Task(Task.LOAD_USER_LIST, param);
				MainService.newTask(task);
				Log.d(LOG, LOG + "-----Task.LOAD_USER_LIST myuid =" + myuid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			loadListOffer();
		}
		if (myinfoList == null) {
			try {
				// 获取系统的NotificationManager服务
				HashMap param = new HashMap();
				param.put("myuid", myuid);
				Task task = new Task(Task.REFRESH_USER_INFO_LIST, param);
				MainService.newTask(task);
				Log.d(LOG, LOG + "-----Task.REFRESH_USER_INFO_LIST myuid =" + myuid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}

	public void refreshUserlist() {
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			param.put("myuid", myuid);
			Task task = new Task(Task.REFRESH_USER_LIST, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----Task.REFRESH_USER_LIST myuid =" + myuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			param.put("myuid", myuid);
			Task task = new Task(Task.REFRESH_USER_ICON, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----Task.REFRESH_USER_ICON myuid =" + myuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void refresh(Object... param) {

		if (param == null)
			return;

		int pa = ((Integer) (param[0])).intValue();
		switch (pa) {
		case LOAD_USER_LIST:// 更新微博列表
			List<User> rs = (List<User>) param[1];
			if (rs != null)
				myuserList = rs;
			loadListOffer();
			// myuserList = uc.loadList(this, Integer.toString(myuid), 0);
			Log.d(LOG, LOG + "-----refresh LOAD_QUANZI_LIST");
			break;
		case REFRESH_USER_LIST:// 更新微博列表
			List<User> rs2 = (List<User>) param[1];
			if (rs2 != null)
				myuserList = rs2;
			loadListOffer();
			// myuserList = uc.loadList(this, Integer.toString(myuid), 0);
			Log.d(LOG, LOG + "-----refresh LOAD_QUANZI_LIST");
			break;
		case REFRESH_USER_ICON:// 更新微博列表
			List<User> rs3 = (List<User>) param[1];
			if (rs3 != null)
				myuserList = rs3;
			loadListOffer();
			// myuserList = uc.loadList(this, Integer.toString(myuid), 0);
			Log.d(LOG, LOG + "-----refresh REFRESH_USER_ICON");
			break;	
		case REFRESH_USER_INFO_LIST:// 更新微博列表
			List<UserInfo> rs4 = (List<UserInfo>) param[1];
			if (rs4 != null)
				myinfoList = rs4;
			//loadListOffer();
			// myuserList = uc.loadList(this, Integer.toString(myuid), 0);
			Log.d(LOG, LOG + "-----refresh REFRESH_USER_INFO_LIST");
			break;		
			
		default:
			break;
		}
	}

}