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
	// �汾��Ϣ2011.9.18
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
		// �õ���Ļ���ڴ�С
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		m_dispWidth = dm.widthPixels;
		m_dispHeight = dm.heightPixels;

		/*
		 * //�����ؼ�λ�úʹ�С�� private EditText et_longitude;
		 * AbsoluteLayout.LayoutParams lp;
		 * 
		 * lp = new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT, 0, 0);
		 * 
		 * lp.x = x; lp.y = y; lp.height = m_etHeight; lp.width = m_etWidth;
		 * et_longitude.setLayoutParams(lp);
		 * 
		 * //���ÿؼ���ʾ�����С����list ���������ַ�����
		 */
	}

	//
	public void getLocation() {

		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // ;
		if (locManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {

		} else {
			Toast.makeText(this, "�뿪��GPS", Toast.LENGTH_SHORT).show();
		}
		// ���ҵ�������Ϣ
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // �߾���
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // �͹���

		String provider = locManager.getBestProvider(criteria, true); // ��ȡGPS��Ϣ
		mylocation = locManager.getLastKnownLocation(provider); // ͨ��GPS��ȡλ��

		if (mylocation == null)
			mylocation = locManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		// ����ÿ3���ȡһ��GPS�Ķ�λ��Ϣ
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,
				8, new LocationListener() {
					@Override
					public void onLocationChanged(Location location) {
						// ��GPS��λ��Ϣ�����ı�ʱ������λ��
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
						// ��GPS LocationProvider����ʱ������λ��
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
						// ��GPS��λ��Ϣ�����ı�ʱ������λ��
						mylocation = location;
						saveLocation();
					}

					@Override
					public void onProviderDisabled(String provider) {
						// updateMapView(null);
					}

					@Override
					public void onProviderEnabled(String provider) {
						// ��GPS LocationProvider����ʱ������λ��
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

	// ���߾���ά�Ȼ�õ�ַ
	private String getAddFromLocation(Location loc) {

		String latLongString;
		String addressString = "û���ҵ���ַ\n";
		if (loc != null) {
			double lng = loc.getLongitude();
			double lat = loc.getLatitude();
			// ȡ�þ��Ⱥ�γ��
			Double geoLat = loc.getLatitude() * 1E6;
			Double geoLng = loc.getLongitude() * 1E6;
			// ����ת��Ϊint��
			GeoPoint point = new GeoPoint(geoLat.intValue(), geoLng.intValue());
			latLongString = "���ȣ�" + lat + "\nγ�ȣ�" + lng;
			double latitude = loc.getLatitude();
			double longitude = loc.getLongitude();
			// ���ݵ�������ȷ������
			Geocoder gc = new Geocoder(this, Locale.getDefault());
			try {
				// ȡ�õ�ַ��ص�һЩ��Ϣ�����ȡ�γ��
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
			latLongString = "û���ҵ�����.\n";
		}
		// ��ʾ
		return ("�㵱ǰ����������:\n" + latLongString + "\n" + addressString);
	}

	// ͨ��Service���������ж��Ƿ�����ĳ������
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
	// ��ȡ���������ķ��������
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
		// ��Ҫ�жϵķ������֣�����launcher2�����һ�����ַ���
		final String ClassName = "com.tuifi.quanzi.LaunchService";

		boolean b = ServiceIsStart(mServiceList, ClassName);

		if (!b) {
			Intent tIntent = new Intent(this, LaunchService.class);
			// ����ָ��Service
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
