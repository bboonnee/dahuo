package com.tuifi.quanzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.controller.UserInfoController;
import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.model.User;
import com.tuifi.quanzi.model.UserInfos;
import com.tuifi.quanzi.util.FrameActivity;

public class PersonInfoNew extends FrameActivity implements IWeiboActivity {

	private static final String LOG = "PersonInfoNew";

	public static final int REFRESH_USER_INFO = 1;
	User uitem;
	LinearLayout pilayout;
	TextView[] tvname;
	TextView[] tvvalue;
	LinearLayout[] ll;
	ImageView piuserpic;
	TextView piname, pimobile, piemail;
	String currentUid;
	ArrayList<UserInfos> currentlist = new ArrayList<UserInfos>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newpersioninfo);
		initActivity();
		// 获取启动该Result的Intent
		Intent intent = getIntent();
		// 获取该intent所携带的数据
		Bundle data = intent.getExtras();
		// 从Bundle数据包中取出数据
		try {
			currentUid = (String) data.getSerializable("item");
			uitem = UserController.getUserItemFromUid(currentUid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (uitem == null)
			uitem = myuserList.get(0);
		setValues();

	}

	private void initActivity() {
		initTopbar();
		initforms();
		// 本activity的设置
		try {
			title.setText("详细资料");
			bnrefresh.setVisibility(View.GONE);
			bnnewmsg.setVisibility(View.GONE);

			bnrefresh.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void initforms() {

		pilayout = (LinearLayout) findViewById(R.id.persioninfolayout);
		piuserpic = (ImageView) findViewById(R.id.piuserpic);
		piname = (TextView) findViewById(R.id.piname);
		pimobile = (TextView) findViewById(R.id.pimobile);
		piemail = (TextView) findViewById(R.id.piemail);
		pimobile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String inputStr = pimobile.getText().toString();
					Intent myIntentDial = new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + inputStr));
					startActivity(myIntentDial);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		piemail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * try{ String email = piemail.getText().toString(); Intent
				 * intent = new Intent(android.content.Intent.ACTION_SEND);
				 * intent.putExtra(android.content.Intent.EXTRA_EMAIL,email);
				 * intent.putExtra(android.content.Intent.EXTRA_TEXT, email);
				 * intent.setType("text/plain"); startActivity(intent); } catch
				 * (Exception e) { // TODO: handle exception
				 * e.printStackTrace(); }
				 */
			}
		});
	}

	void setValues() {
		//setInfo();
		
			readInfoFromJson();
		
		piname.setText(uitem.getuname());
		pimobile.setText(uitem.getMobile());
		piemail.setText(uitem.getemail());
		piuserpic.setImageDrawable(uitem.getUserIcon());
	}

	void setInfo() {
		currentlist = UserInfoController.getUserInfoFromUid(myuid);
		int size = currentlist.size();
		tvname = new TextView[size];
		tvvalue = new TextView[size];
		ll = new LinearLayout[size];
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < size; i++) {
			UserInfos ui = currentlist.get(i);
			if (ui != null) {
				ll[i] = new LinearLayout(this);
				ll[i].setOrientation(LinearLayout.HORIZONTAL);
				// ll[i].setBackgroundColor(Color.GRAY);
				// ll[i].setGravity(Gravity.LEFT);
				// LinearLayout.SHOW_DIVIDER_END=4
				// ll[i].setHorizontalGravity(5);
				tvname[i] = new TextView(this);
				tvname[i].setText(ui.getinfodata() + ": ");
				tvname[i].setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				tvname[i].setTextColor(Color.WHITE);

				tvvalue[i] = new TextView(this);
				tvvalue[i].setText(ui.getdatastr());
				tvvalue[i].setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				tvvalue[i].setPadding(5, 5, 5, 5);
				tvvalue[i].setTextColor(Color.WHITE);

				ll[i].addView(tvname[i]);
				ll[i].addView(tvvalue[i]);
				pilayout.addView(ll[i]);
			}
		}

	}

	public void readInfoFromJson() {
		// 每次loadlist的时候即出发了修改button的事件
		JSONArray data;
		try {
			// 使用Map封装请求参数
			Map<String, String> map = new HashMap<String, String>();
			String uid = uitem.getuid();
			map.put("do", "getinfolist");
			map.put("uid", uid);
			data = queryArray(map, 0);
			String t = data.getString(0);
			if (t.equals("overtime")) {
				Toast.makeText(this, "连接超时 ，请检查网络，稍后刷新！", Toast.LENGTH_SHORT)
						.show();
				return;
			} else {
				// 清空
				tvname = new TextView[data.length()];
				tvvalue = new TextView[data.length()];
				ll = new LinearLayout[data.length()];
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				for (int i = 0; i < data.length(); i++) {
					JSONObject d = data.getJSONObject(i);
					if (d != null) {
						ll[i] = new LinearLayout(this);
						ll[i].setOrientation(LinearLayout.HORIZONTAL);
						// ll[i].setBackgroundColor(Color.GRAY);
						// ll[i].setGravity(Gravity.LEFT);
						// LinearLayout.SHOW_DIVIDER_END=4
						// ll[i].setHorizontalGravity(5);
						tvname[i] = new TextView(this);
						tvname[i].setText(d.getString("info_data") + ": ");
						tvname[i].setLayoutParams(new ViewGroup.LayoutParams(
								ViewGroup.LayoutParams.WRAP_CONTENT,
								ViewGroup.LayoutParams.WRAP_CONTENT));
						tvname[i].setTextColor(Color.WHITE);

						tvvalue[i] = new TextView(this);
						tvvalue[i].setText(d.getString("datastr"));
						tvvalue[i].setLayoutParams(new ViewGroup.LayoutParams(
								ViewGroup.LayoutParams.WRAP_CONTENT,
								ViewGroup.LayoutParams.WRAP_CONTENT));
						tvvalue[i].setPadding(5, 5, 5, 5);
						tvvalue[i].setTextColor(Color.WHITE);

						ll[i].addView(tvname[i]);
						ll[i].addView(tvvalue[i]);
						pilayout.addView(ll[i]);
					}
				}
			}// end else

		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			return;
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
			setValues();
		}
	}

	@Override
	public void refresh(Object... param) {

		if (param == null)
			return;

		int pa = ((Integer) (param[0])).intValue();
		switch (pa) {
		case REFRESH_USER_INFO:// 更新微博列表
			List<User> rs = (List<User>) param[1];
			if (rs != null)
				myuserList = rs;
			setValues();
			// myuserList = uc.loadList(this, Integer.toString(myuid), 0);
			Log.d(LOG, LOG + "-----refresh LOAD_QUANZI_LIST");
			break;

		default:
			break;
		}
	}
}
