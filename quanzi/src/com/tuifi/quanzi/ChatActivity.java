package com.tuifi.quanzi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tuifi.quanzi.AffairsActivity.contactAdapater;
import com.tuifi.quanzi.controller.MsgController;
import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.model.AffairsInfo;
import com.tuifi.quanzi.model.ChatMsgEntity;
import com.tuifi.quanzi.model.ChatMsgViewAdapter;
import com.tuifi.quanzi.model.MsgInfo;
import com.tuifi.quanzi.util.FrameActivity;

public class ChatActivity extends FrameActivity implements IWeiboActivity {
	private static String LOG = "ChatActivity";

	private static final String TAG = ChatActivity.class.getSimpleName();;

	private ListView talkView;

	private Button messageButton;

	private EditText messageText;
	
	public static final int REFRESH_CHAT = 1;

	// private ChatMsgViewAdapter myAdapter;
	public static String currentqzid;

	private ArrayList<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();
	private List<MsgInfo> currentlist = new ArrayList<MsgInfo>();

	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate >>>>>>");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		// 获取启动该Result的Intent
		Intent intent = getIntent();
		// 获取该intent所携带的数据
		Bundle data = intent.getExtras();
		// 从Bundle数据包中取出数据
		try {
			currentqzid = (String) data.getSerializable("item");
			currentlist = MsgController.loadquanzimsg(currentqzid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		initActivity();
	}
	private void initActivity() {
		initTopbar();
		// 本activity的设置
		bnnewmsg.setEnabled(false);
		title.setText("聊天");
		// bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		bnrefresh.setVisibility(View.GONE);		
		talkView = (ListView) findViewById(R.id.list);
		messageButton = (Button) findViewById(R.id.MessageButton);
		messageText = (EditText) findViewById(R.id.MessageText);
		OnClickListener messageButtonListener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.v(TAG, "onclick >>>>>>>>");
				String name = getName();
				String date = getDate();
				String msgText = getText();
				int RId = R.layout.list_say_he_item;

				ChatMsgEntity newMessage = new ChatMsgEntity(name, date,
						msgText, RId);
				list.add(newMessage);
				// list.add(d0);
				talkView.setAdapter(new ChatMsgViewAdapter(ChatActivity.this,
						list));
				messageText.setText("");
				// myAdapter.notifyDataSetChanged();
				int sendresult = sendNewMsgByJson(msgText);
				if (sendresult>0)
				{
					String msg =  "发送成功";
					Toast.makeText(getApplicationContext(),msg,
							Toast.LENGTH_LONG).show();
					//finish();
				}
			}
		};
		messageButton.setOnClickListener(messageButtonListener);
		initMsgList();
		
	}
	private void initMsgList() {
		list.clear();
		for (MsgInfo mi : currentlist) {
			String uid = mi.getsenduid();
			String name = UserController.getNameFromUid(uid);
			String date = mi.getctime();
			String msgText = mi.getcontent();
			int RId = R.layout.list_say_me_item;
			if (myuid.equals(uid)) {
				RId = R.layout.list_say_he_item;
			}
			ChatMsgEntity newMessage = new ChatMsgEntity(name, date, msgText,
					RId);
			list.add(newMessage);
		}
		// list.add(d0);
		int  l = list.size();
		if ((list != null)&&(list.size()>0)) {
			talkView.setAdapter(new ChatMsgViewAdapter(ChatActivity.this, list));
			messageText.setText("");
		}
	}
	// shuold be redefine in the future
	private String getName() {
		return getResources().getString(R.string.myDisplayName);
	}

	// shuold be redefine in the future
	private String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = year + "-" + month + "-" + day;
		return date;
	}

	// shuold be redefine in the future
	private String getText() {
		return messageText.getText().toString();
	}

	public void onDestroy() {
		Log.v(TAG, "onDestroy>>>>>>");
		// list = null;
		super.onDestroy();
	}

	@Override
	public void init() {
		// 刷新
		//msglistview.removeAllViewsInLayout();
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			Task task = new Task(Task.LOAD_MSG_CHAT, param);
			MainService.newTask(task);
			Log.d(LOG, LOG + "-----init Task.LOAD_MSG_CHAT");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG, LOG + e.toString());
		}		

	}

	@Override
	public void refresh(Object... param) {
				switch (((Integer) (param[0])).intValue()) {
				case REFRESH_CHAT://						
					currentlist = (List<MsgInfo>) param[1];
					initMsgList();				
					break;
				}
	}
	private int sendNewMsgByJson(String msg) { // 根据手机号读取网络，判断是否存在
		JSONObject data;		
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("do", "savenewMessage");
			map.put("senduid",myuid);
			map.put("notifymsg", msg);
			map.put("qzid", currentqzid);			
			map.put("type", "1");	//1 quanzi 2 huodong  3 int				
			//String ctime = Long.toString(System.currentTimeMillis());
			String ctime = Long.toString(new Date().getTime());
			map.put("ctime", ctime);
			
			data = queryObject(map, 0);
			String t = data.toString();
			if (t.equals("overtime")) {
				Toast.makeText(getApplicationContext(), "数据读取超时，请检查网络",
						Toast.LENGTH_LONG).show();
				return -2;
			} else if (t.equals("connerr")) {
				Toast.makeText(getApplicationContext(), "服务器无法连接，请检查网络稍后再试",
						Toast.LENGTH_LONG).show();
				return -3;
			} else {		
				int result = data.getInt("result");
				if (t.equals("")) {
					// 号码存在 则返回成功
					return 0;
				} else {
					return result;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG, "sendNewMsgByJson " + e.toString());
		}
		Toast.makeText(getApplicationContext(), "发送失败，请检查网络。",
				Toast.LENGTH_LONG).show();
		return 0;

	}
}
