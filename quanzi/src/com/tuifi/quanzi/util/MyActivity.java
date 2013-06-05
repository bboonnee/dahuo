package com.tuifi.quanzi.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.tuifi.quanzi.HomeActivity;
import com.tuifi.quanzi.LaunchService;
import com.tuifi.quanzi.R;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.model.AffairsInfo;
import com.tuifi.quanzi.model.HuodongInfo;
import com.tuifi.quanzi.model.MsgInfo;
import com.tuifi.quanzi.model.NotifyInfo;
import com.tuifi.quanzi.model.QuanziInfo;
import com.tuifi.quanzi.model.User;
import com.tuifi.quanzi.model.UserInfo;

public class MyActivity extends JsonActivity {
	private static String LOG = "MyActivity";
	public static Location mylocation;
	public static double mylongitude, mylatitude;
	public static final int PROGRESS_DIALOG = 1;
	public static final int QUIT_DIALOG = 2;
	public static final int ACCEPT_AUCTION = 3;
	public static final int STATE_FINISH = 1;
	public static final int STATE_ERROR = -1;
	public static String myuid=""; // user id
	public String mobileme, mobileinput, autologin;
	public SharedPreferences preferences;
	// 版本信息2011.9.18
	public int vercode;
	// add 9.19
	public ImageButton bnhome, bnhuodong, bnmsg, bncontact, bnotheract;
	public ImageView bnrefresh, bnback, bnnewmsg;
	public TextView title;
	public LinearLayout bnhomelayout, bnhuodonglayout, bnmsglayout,
			bncontactlayout, bnotherlayout;

	// get the window resolution
	private static DisplayMetrics dm;
	private static int m_dispWidth;
	private static int m_dispHeight;

	public static List<User> myuserList;
	public static List<NotifyInfo> mynotifyList;
	public static List<MsgInfo> mymsgList;
	public static List<AffairsInfo> myaffList;
	public static List<QuanziInfo> myquanziList;
	public static List<HuodongInfo> myhuodongList;
	public static List<UserInfo> myinfoList;
		
	//
	public void initFrom() {
		initBottombar();
		initTopbar();
	}

	public void initBottombar() {

		bnhomelayout = (LinearLayout) findViewById(R.id.bnhomelayout);
		bnhuodonglayout = (LinearLayout) findViewById(R.id.bnhuodonglayout);
		bnmsglayout = (LinearLayout) findViewById(R.id.bnmsglayout);
		bncontactlayout = (LinearLayout) findViewById(R.id.bncontactlayout);
		bnotherlayout = (LinearLayout) findViewById(R.id.bnotherlayout);

		bnhome = (ImageButton) findViewById(R.id.bnhome);
		bnhuodong = (ImageButton) findViewById(R.id.bnhuodong);
		bnmsg = (ImageButton) findViewById(R.id.bnmsg);
		bncontact = (ImageButton) findViewById(R.id.bncontact);
		bnotheract = (ImageButton) findViewById(R.id.bnotheract);
		bnhome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
/*				Intent mintent = new Intent(MyActivity.this, MainActivity.class);
				startActivity(mintent);
				finish();*/
			}
		});
		// to set its default *.png
		bnhome.setOnTouchListener(new ImageButton.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					arg0.setBackgroundResource(R.drawable.connect38p);
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					arg0.setBackgroundResource(R.drawable.connect38);
				}

				return false;
			}

		});
		bnhuodong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

/*				Intent mintent = new Intent(MyActivity.this, Huodong.class);
				startActivity(mintent);
				finish();*/

			}
		});
		// to set its default *.png
		bnhuodong.setOnTouchListener(new ImageButton.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					arg0.setBackgroundResource(R.drawable.huodong38p);
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					arg0.setBackgroundResource(R.drawable.huodong38);
				}

				return false;
			}

		});
		bnmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
/*				Intent mintent = new Intent(MyActivity.this, Message.class);
				startActivity(mintent);
				finish();*/
			}
		});
		// to set its default *.png
		bnmsg.setOnTouchListener(new ImageButton.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					arg0.setBackgroundResource(R.drawable.mail38p);
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					arg0.setBackgroundResource(R.drawable.mail38);
				}

				return false;
			}

		});
		bncontact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mintent = new Intent(MyActivity.this,
						HomeActivity.class);
				startActivity(mintent);
				finish();
			}
		});
		// to set its default *.png
		bncontact.setOnTouchListener(new ImageButton.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					arg0.setBackgroundResource(R.drawable.contact38p);
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					arg0.setBackgroundResource(R.drawable.contact38);
				}

				return false;
			}

		});
		bnotheract.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
/*				Intent mintent = new Intent(MyActivity.this, MoreList.class);
				startActivity(mintent);
				finish();*/
			}
		});
		// to set its default *.png
		bnotheract.setOnTouchListener(new ImageButton.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					arg0.setBackgroundResource(R.drawable.otherpoint38p);
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					arg0.setBackgroundResource(R.drawable.otherpoint38);
				}

				return false;
			}

		});
	}

	//
	public void initTopbar() {
		try {
			bnrefresh = (ImageView) findViewById(R.id.bnrefresh);
			bnback = (ImageView) findViewById(R.id.bnback);
			bnnewmsg = (ImageView) findViewById(R.id.bnnewmsg);
			title = (TextView) findViewById(R.id.title);

			// to set its default *.png
			bnrefresh.setOnTouchListener(new ImageButton.OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
						arg0.setBackgroundResource(R.drawable.refresh35p);
					} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
						arg0.setBackgroundResource(R.drawable.refresh35);
					}

					return false;
				}

			});
			bnback.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();

				}
			});
			// to set its default *.png
			bnback.setOnTouchListener(new ImageButton.OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
						arg0.setBackgroundResource(R.drawable.back35p);
					} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
						arg0.setBackgroundResource(R.drawable.back35);
					}

					return false;
				}

			});

			// to set its default *.png
			bnnewmsg.setOnTouchListener(new ImageButton.OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
						arg0.setBackgroundResource(R.drawable.newmsg35p);
					} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
						arg0.setBackgroundResource(R.drawable.newmsg35);
					}

					return false;
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// getWin
	public void getWindowResolution() {
		// 得到屏幕窗口大小
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		m_dispWidth = dm.widthPixels;
		m_dispHeight = dm.heightPixels;

		/*
		 * //调整控件位置和大小： private EditText et_longitude;
		 * AbsoluteLayout.LayoutParams lp;
		 * 
		 * lp = new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT, 0, 0);
		 * 
		 * lp.x = x; lp.y = y; lp.height = m_etHeight; lp.width = m_etWidth;
		 * et_longitude.setLayoutParams(lp);
		 * 
		 * //设置控件显示字体大小：（list 不可用这种方法）
		 */
	}

	//
	public void getLocation() {

		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // ;
		if (locManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {

		} else {
			Toast.makeText(this, "请开启GPS", Toast.LENGTH_SHORT).show();
		}
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		String provider = locManager.getBestProvider(criteria, true); // 获取GPS信息
		mylocation = locManager.getLastKnownLocation(provider); // 通过GPS获取位置

		if (mylocation == null)
			mylocation = locManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		// 设置每3秒获取一次GPS的定位信息
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,
				8, new LocationListener() {
					@Override
					public void onLocationChanged(Location location) {
						// 当GPS定位信息发生改变时，更新位置
						// updateView(location);
						mylocation = location;
						saveLocation();
					}

					@Override
					public void onProviderDisabled(String provider) {
						// updateView(null);

					}

					@Override
					public void onProviderEnabled(String provider) {
						// 当GPS LocationProvider可用时，更新位置
						//
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
					}
				});
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				20000, 8, new LocationListener() {
					@Override
					public void onLocationChanged(Location location) {
						// 当GPS定位信息发生改变时，更新位置
						mylocation = location;
						saveLocation();
					}

					@Override
					public void onProviderDisabled(String provider) {
						// updateMapView(null);
					}

					@Override
					public void onProviderEnabled(String provider) {
						// 当GPS LocationProvider可用时，更新位置
						// updateMapView(locManager.getLastKnownLocation(provider));
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
					}
				});

		getLastLocation();

	}

	public void saveLocation() {

		if ((mylongitude != 0) && (mylatitude != 0)) {
			//
		}

	}

	public void getLastLocation() {

		int needflag = 0;
		if (mylocation != null) {
			mylongitude = mylocation.getLongitude();
			mylatitude = mylocation.getLatitude();
			if ((mylongitude == 0) && (mylatitude == 0)) {
				needflag = 1;
			}
		}
		if (mylocation == null) {
			mylocation = new Location("a");
			needflag = 1;
		}

		if (needflag > 0)
			try {
				// String lati = preferences.getString("latitude", null);
				// String longti = preferences.getString("longitude", null);
				String lati = "";
				String longti = "";
				double la = Double.valueOf(lati);
				double lo = Double.valueOf(longti);
				mylocation.setLatitude(la);
				mylocation.setLongitude(lo);
				mylongitude = mylocation.getLongitude();
				mylatitude = mylocation.getLatitude();

			} catch (Exception e) {
				e.printStackTrace();
			}

	}

	public double distance(double x1, double y1, double x2, double y2) {
		double m;
		m = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return m;
	}

	// 更具经度维度获得地址
	private String getAddFromLocation(Location loc) {

		String latLongString;
		String addressString = "没有找到地址\n";
		if (loc != null) {
			double lng = loc.getLongitude();
			double lat = loc.getLatitude();
			// 取得经度和纬度
			Double geoLat = loc.getLatitude() * 1E6;
			Double geoLng = loc.getLongitude() * 1E6;
			// 将其转换为int型
			GeoPoint point = new GeoPoint(geoLat.intValue(), geoLng.intValue());
			latLongString = "经度：" + lat + "\n纬度：" + lng;
			double latitude = loc.getLatitude();
			double longitude = loc.getLongitude();
			// 根据地理环境来确定编码
			Geocoder gc = new Geocoder(this, Locale.getDefault());
			try {
				// 取得地址相关的一些信息、经度、纬度
				List<Address> addresses = gc.getFromLocation(latitude,
						longitude, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
						sb.append(address.getAddressLine(i)).append("\n");
					sb.append(address.getLocality()).append("\n");
					sb.append(address.getPostalCode()).append("\n");
					sb.append(address.getCountryName());
					addressString = sb.toString();
				}
			} catch (IOException e) {
			}
		} else {
			latLongString = "没有找到坐标.\n";
		}
		// 显示
		return ("你当前的坐标如下:\n" + latLongString + "\n" + addressString);
	}

	// 通过Service的类名来判断是否启动某个服务
	private boolean ServiceIsStart(
			List<ActivityManager.RunningServiceInfo> mServiceList,
			String className) {

		for (int i = 0; i < mServiceList.size(); i++) {
			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	// 获取所有启动的服务的类名
	private String getServiceClassName(
			List<ActivityManager.RunningServiceInfo> mServiceList) {
		String res = "";
		for (int i = 0; i < mServiceList.size(); i++) {
			res += mServiceList.get(i).service.getClassName() + " \n";
		}

		return res;
	}

	public void servicestart() {
		
		Intent it=new Intent("com.tuifi.quanzi.logic.MainService");
		this.startService(it);
		
		ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager
				.getRunningServices(30);
		// 我要判断的服务名字，我在launcher2里加了一个音乐服务
		final String ClassName = "com.tuifi.quanzi.LaunchService";

		boolean b = ServiceIsStart(mServiceList, ClassName);

		if (!b) {
			Intent tIntent = new Intent(this, LaunchService.class);
			// 启动指定Service
			startService(tIntent);

		}

	}

	public void fileScan(String file) {
		Uri data = Uri.parse("file://" + file);

		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
	}

	public void folderScan(String path) {
		File file = new File(path);

		if (file.isDirectory()) {
			File[] array = file.listFiles();

			for (int i = 0; i < array.length; i++) {
				File f = array[i];

				if (f.isFile()) {// FILE TYPE
					String name = f.getName();

					if (name.contains(".mp3")) {
						fileScan(f.getAbsolutePath());
					}
				} else {// FOLDER TYPE
					folderScan(f.getAbsolutePath());
				}
			}
		}
	}

}
