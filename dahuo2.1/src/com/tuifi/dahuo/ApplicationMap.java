package com.tuifi.dahuo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.tuifi.dahuo.model.Region;
import com.tuifi.dahuo.model.User;

public class ApplicationMap extends Application {

	private static ApplicationMap mInstance = null;
	public boolean m_bKeyRight = true;
	public BMapManager mBMapManager = null;

	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();

	public LocationClient mLocationClient = null;
	private String mData;
	public MyLocationListenner myListener = new MyLocationListenner();
	public TextView mTv;
	public EditText mEt;
	public NotifyLister mNotifyer = null;
	public Vibrator mVibrator01;
	public static String TAG = "LocTestDemo";
	public String mAdd ;
	public String mProvince ;
	public String mCity ;
	public String mXian ;
	public static ArrayList<Region> postStartAdd ;
	public static ArrayList<Region> postEndAdd;
	public static ArrayList<Region> SearchStartAdd;
	public static ArrayList<Region> SearchEndAdd;
	public static ArrayList<Region> currentRegion;
	public static List<Region> regionList;

	public static User currentUser;
	public static final String strKey = "DD88D3830D7CBC40BA62FF440DC46036AEA7B8B6";

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initEngineManager(this);
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
	}

	@Override
	// 建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}
		super.onTerminate();
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(
					ApplicationMap.getInstance().getApplicationContext(),
					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	public static ApplicationMap getInstance() {
		return mInstance;
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						ApplicationMap.getInstance().getApplicationContext(),
						"您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						ApplicationMap.getInstance().getApplicationContext(),
						"输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(
						ApplicationMap.getInstance().getApplicationContext(),
						"请在 DemoApplication.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				ApplicationMap.getInstance().m_bKeyRight = false;
			}
		}
	}

	public void logMsg(String str) {
		try {
			mData = str;
			if (mTv != null)
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void logMsgEditText(String str) {
		try {
			mAdd = str;
			if (mEt != null)
				mEt.setText(mAdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n省：");
				sb.append(location.getProvince());
				mProvince = location.getProvince();
				sb.append("\n市：");
				sb.append(location.getCity());
				mCity = location.getCity();
				sb.append("\n区/县：");
				sb.append(location.getDistrict());
				mXian = location.getDistrict();
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				mAdd = location.getAddrStr();
			}
			sb.append("\nsdk version : ");
			sb.append(mLocationClient.getVersion());
			sb.append("\nisCellChangeFlag : ");
			sb.append(location.isCellChangeFlag());
			logMsg(mAdd);
			logMsgEditText(mAdd);
			mLocationClient.stop();
			Log.i(TAG, sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
			mVibrator01.vibrate(1000);
		}
	}

	public void exitApp(Context con) {
		for (Activity ac : allActivity) {
			ac.finish();
		}
	}
	// 设置相关参数
		public static void setLocationOption(LocationClient mClient) {
			if(mClient!=null)
			{
				LocationClientOption option = new LocationClientOption();
				option.setOpenGps(true); // 打开gps
				option.setCoorType("bd09ll"); // 设置坐标类型
				option.setServiceName("com.baidu.location.service_v2.9");
				option.setPoiExtraInfo(true);
				option.setAddrType("all");
				option.setScanSpan(500); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
				// option.setScanSpan(3000);
				option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
				// option.setPriority(LocationClientOption.GpsFirst); //不设置，默认是gps优先
				option.setPoiNumber(10);
				option.disableCache(true);
				mClient.setLocOption(option);
			}
			
		}
}