package com.tuifi.dahuo.zaker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tuifi.dahuo.Activity_favorlist;
import com.tuifi.dahuo.Activity_msglist;
import com.tuifi.dahuo.Activity_post;
import com.tuifi.dahuo.R;
import com.tuifi.dahuo.SearchActivity_car;
import com.tuifi.dahuo.SearchActivity_post;
import com.tuifi.dahuo.SearchActivity_shop;
import com.tuifi.dahuo.app.TabActivity_map;
import com.tuifi.dahuo.app.TabActivity_pref;

/**
 * 自定义适配器
 * 
 * @author wulianghuan
 * 
 */
public class MyPagerAdapter extends PagerAdapter {
	Vibrator vibrator;
	ArrayList<ImageInfo> data;
	Activity activity;
	LayoutParams params;

	public MyPagerAdapter(Activity activity, ArrayList<ImageInfo> data) {
		this.activity = activity;
		this.data = data;
		vibrator = (Vibrator) activity
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int index) {
		Log.v("test", index + "index");

		View view = activity.getLayoutInflater().inflate(R.layout.grid, null);
		GridView gridView = (GridView) view.findViewById(R.id.gridView1);
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(5);
		gridView.setHorizontalSpacing(5);
		gridView.setAdapter(new BaseAdapter() {

			@Override
			public int getCount() {
				return 8;
			}

			@Override
			public Object getItem(int position) {
				return position;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View item = LayoutInflater.from(activity).inflate(
						R.layout.grid_item, null);
				ImageView iv = (ImageView) item.findViewById(R.id.imageView1);
				RelativeLayout relativeLayout = (RelativeLayout) item
						.findViewById(R.id.relativeLayout);
				iv.setImageResource((data.get(index * 8 + position)).imageId);
				relativeLayout.setBackgroundResource((data.get(index * 8
						+ position)).bgId);
				relativeLayout.getBackground().setAlpha(225);
				TextView tv = (TextView) item.findViewById(R.id.msg);
				tv.setText((data.get(index * 8 + position)).imageMsg);
				return item;
			}
		});

		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				View view = arg1;
				arg1.setVisibility(View.INVISIBLE);

				params = new WindowManager.LayoutParams();
				// activity.getWindowManager().addView(view, params);
				vibrator.vibrate(2500);
				return true;
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Log.i("TAG", "-----" + position + "===" + id);
				Intent mainIntent = new Intent();
				switch (position) {
				// 搜索货源
				case 0:
					mainIntent.setClass(v.getContext(),
							SearchActivity_post.class);
					v.getContext().startActivity(mainIntent);
					break;
				// 搜索车辆
				case 1:
					mainIntent.setClass(v.getContext(),
							SearchActivity_car.class);
					v.getContext().startActivity(mainIntent);
					break;
				// 搜索信息部
				case 2:
					mainIntent.setClass(v.getContext(),
							SearchActivity_shop.class);
					v.getContext().startActivity(mainIntent);
					break;
				//发布信息
				case 3:
					mainIntent.setClass(v.getContext(), Activity_post.class);
					v.getContext().startActivity(mainIntent);
					break;
					//周边地图
				case 4:
					mainIntent.setClass(v.getContext(), TabActivity_map.class);
					v.getContext().startActivity(mainIntent);
					break;
					//个人中心
				case 5:
					mainIntent.setClass(v.getContext(), TabActivity_pref.class);
					v.getContext().startActivity(mainIntent);
					break;
					//收藏历史
				case 6:
					mainIntent.setClass(v.getContext(), Activity_favorlist.class);
					v.getContext().startActivity(mainIntent);
					break;
					//推送信息
				case 7:
					mainIntent.setClass(v.getContext(), Activity_msglist.class);
					v.getContext().startActivity(mainIntent);
					break;				

				}

			}
		});
		/*
		 * gridView.setOnTouchListener(new View.OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) {
		 * 
		 * return true; } });
		 */
		((ViewPager) container).addView(view);

		return view;
	}
}
