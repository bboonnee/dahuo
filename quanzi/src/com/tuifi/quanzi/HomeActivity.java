package com.tuifi.quanzi;

import java.util.HashMap;
import java.util.List;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.logic.Task;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.util.FrameActivity;
import com.tuifi.quanzi.util.MyActivity;

public class HomeActivity extends FrameActivity implements IWeiboActivity {
	private static String LOG = "HomeActivity";
	public static final int LOAD_QUANZI_LIST = 1;
	public static final int LOAD_QUANZI_ICON = 2;
	public View process;// 加载条
	Gallery gallery;
	TextView tvdes;
	int[] imageIds = new int[] { R.drawable.mba_l };
	String[] descriptions = new String[] { "圈子：清华大学MBA2011级p2班  \n 宗旨：分享班级学习及生活内容　＼ｎ"
			+ "人数：　圈子成员72人 \n 　加入方式：固定" };

	
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		MainService.allActivity.add(this);
		init();
		initActivity();
		
	}

	private void initActivity() {

		initTopbar();
		gallery = (Gallery) findViewById(R.id.gallery);
		tvdes = (TextView) findViewById(R.id.tvdes);
		// 本activity的设置
		//
		bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		//bnrefresh.setVisibility(View.GONE);
		title.setText("圈子列表");
		process = this.findViewById(R.id.homeprogress);
		bnrefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				init();
			}
		});
		// 创建一个BaseAdapter对象，该对象负责提供Gallery所显示的图片
		BaseAdapter adapter = new BaseAdapter() {
			@Override
			public int getCount() {
				if (myquanziList != null)
					return myquanziList.size();
				else
					return 0;
			}

			@Override
			public Object getItem(int position) {
				return position;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			// 该方法的返回的View就是代表了每个列表项
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// 创建一个ImageView
				ImageView imageView = new ImageView(HomeActivity.this);
				imageView.setImageResource(imageIds[0]);
				// 设置ImageView的缩放类型
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new Gallery.LayoutParams(240, 300));
				TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
				imageView.setBackgroundResource(typedArray.getResourceId(
						R.styleable.Gallery_android_galleryItemBackground, 0));
				return imageView;
			}
		};
		gallery.setAdapter(adapter);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			// 当Gallery选中项发生改变时触发该方法
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (myquanziList != null) {
					QuanziInfo oi = myquanziList.get(position % myquanziList.size());
					tvdes.setText(oi.getdetail());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			// 获取系统的NotificationManager服务
			HashMap param = new HashMap();
			param.put("myuid", myuid);
			Task task = new Task(Task.LOAD_QUANZI_LIST, param);
			MainService.newTask(task);
/*			process = this.findViewById(R.id.homeprogress);
			process.setVisibility(View.VISIBLE);// 隐藏进度条
*/			Log.d(LOG, LOG + "-----Task.LOAD_QUANZI_LIST myuid ="+myuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) (param[0])).intValue()) {
		case LOAD_QUANZI_LIST:// 更新微博列表
/*			process = this.findViewById(R.id.homeprogress);
			process.setVisibility(View.GONE);// 隐藏进度条
*/			List<QuanziInfo> rs = (List<QuanziInfo>) param[1];
			if (rs!=null)  
				{
				myquanziList = rs;
				MainService.msuid = myuid;
				MainService.msqzList = myquanziList;
				
				}
			
			((BaseAdapter) gallery.getAdapter()).notifyDataSetChanged();
			Log.d(LOG, LOG + "-----refresh LOAD_QUANZI_LIST");
			break;
		case LOAD_QUANZI_ICON:// 更新用户的头像
			// ((MyAdapter)allStatus.getAdapter()).notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

}