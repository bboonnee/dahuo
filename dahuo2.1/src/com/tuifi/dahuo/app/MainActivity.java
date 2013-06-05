package com.tuifi.dahuo.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.R;
import com.tuifi.dahuo.TabHostActivity;
import com.tuifi.dahuo.zaker.MainZakerActivity;

/**
 * <p>
 * 整个流程就像使用ListView自定BaseAdapter一样
 * </p>
 * 
 * <p>
 * 如果要自定义TabHostActivity的Theme，并且不想要头部阴影 一定要添加这个android:windowContentOverlay =
 * null
 * </p>
 * 
 * <p>
 * 如果想在别的项目里面使用TabHostActivity 可以项目的属性里面找到Android，然后在Library部分添加这个项目(Api) <a
 * href
 * ="http://www.cnblogs.com/qianxudetianxia/archive/2011/05/01/2030232.html">
 * 如何添加</a>
 * </p>
 * */
public class MainActivity extends TabHostActivity {

	List<TabItem> mItems;
	private LayoutInflater mLayoutInflater;
	TextView topCenter;
	ImageView example_left, example_right;
	private LocationClient mLocClient;	

	/**
	 * 在初始化TabWidget前调用 和TabWidget有关的必须在这里初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ApplicationMap.allActivity.add(this);
		setCurrentTab(0);
		topCenter = (TextView) findViewById(R.id.example_center);
		topCenter.setText("首页");

		mLocClient = ((ApplicationMap) getApplication()).mLocationClient;
		example_left = (ImageView) findViewById(R.id.example_left);
		example_right = (ImageView) findViewById(R.id.example_right);
		/*example_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationMap.setLocationOption(mLocClient);
				mLocClient.start();
				
				//发起定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
				if (mLocClient != null && mLocClient.isStarted())
					mLocClient.requestLocation();
				else 
					Log.d("LocSDK3", "locClient is null or not started");					
						//
				getTabHost().getCurrentTab();
			}
		});*/
		initTopView();
	}
	@Override
	protected void prepare() {
		TabItem home = new TabItem("本地", // title
				R.drawable.icon_home, // icon
				R.drawable.example_tab_item_bg, // background
				new Intent(this, TabActivity_list.class)); // intent
		TabItem msg = new TabItem("搜索", R.drawable.icon_meassage,
				R.drawable.example_tab_item_bg, new Intent(this,
						TabActivity_search.class));

		TabItem info = new TabItem("发布", R.drawable.icon_selfinfo,
				R.drawable.example_tab_item_bg, new Intent(this,
						TabActivity_post.class));

		
		TabItem square = new TabItem("地图", R.drawable.icon_square,
				R.drawable.example_tab_item_bg, new Intent(this,
						TabActivity_map.class));

		TabItem more = new TabItem("更多", R.drawable.icon_more,
				R.drawable.example_tab_item_bg, new Intent(this,
						TabActivity_pref.class));

		mItems = new ArrayList<TabItem>();
		mItems.add(home);
		mItems.add(msg);
		mItems.add(info);
		
		mItems.add(square);
		mItems.add(more);

		// 设置分割线
		TabWidget tabWidget = getTabWidget();
		tabWidget.setDividerDrawable(R.drawable.tab_divider);

		mLayoutInflater = getLayoutInflater();
	}

	

	private void initTopView() {
		getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				topCenter.setText("本地");
				example_left.setVisibility(View.INVISIBLE);
				example_right.setVisibility(View.VISIBLE);
				getTabHost().setCurrentTab(0);
			}
		});
		getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				topCenter.setText("发布");
				example_left.setVisibility(View.INVISIBLE);
				example_right.setVisibility(View.INVISIBLE);
				getTabHost().setCurrentTab(1);
			}
		});
		getTabWidget().getChildAt(2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				topCenter.setText("搜索");
				example_left.setVisibility(View.INVISIBLE);
				example_right.setVisibility(View.INVISIBLE);
				getTabHost().setCurrentTab(2);
			}
		});
		getTabWidget().getChildAt(3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				topCenter.setText("地图");
				example_left.setVisibility(View.INVISIBLE);
				example_right.setVisibility(View.INVISIBLE);
				getTabHost().setCurrentTab(3);
			}
		});
		getTabWidget().getChildAt(4).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				topCenter.setText("更多");
				example_left.setVisibility(View.INVISIBLE);
				example_right.setVisibility(View.INVISIBLE);
				getTabHost().setCurrentTab(4);
			}
		});
	}

	/** tab的title，icon，边距设定等等 */
	@Override
	protected void setTabItemTextView(TextView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
		String d = mItems.get(position).getTitle();
		textView.setText(mItems.get(position).getTitle());
		textView.setBackgroundResource(mItems.get(position).getBg());
		textView.setCompoundDrawablesWithIntrinsicBounds(0, mItems
				.get(position).getIcon(), 0, 0);

	}

	/** tab唯一的id */
	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getTitle(); // 我们使用title来作为id，你也可以自定
	}

	/** 点击tab时触发的事件 */
	@Override
	protected Intent getTabItemIntent(int position) {
		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}

	/** 自定义头部文件 */
	@Override
	protected View getTop() {
		return mLayoutInflater.inflate(R.layout.example_top, null);
	}
	@Override
	public void onDestroy() {
		mLocClient.stop();		
		super.onDestroy();
	}
	
	
}
