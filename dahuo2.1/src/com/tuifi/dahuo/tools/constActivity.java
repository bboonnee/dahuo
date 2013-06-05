package com.tuifi.dahuo.tools;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.tuifi.dahuo.R;
import com.tuifi.dahuo.model.Region;
import com.tuifi.dahuo.model.User;

public class constActivity extends Activity {
	private static final String LOG = "constActivity";
	public static List<User> userList;	
	public static List<Region> cityList;
	public static List<Region> xianList;
	public static List<Region> provinceList;	
	public SharedPreferences preferences;

	
	public static ArrayList<String> classNoList = new ArrayList<String>();
	public static ArrayList<String> keywordList = new ArrayList<String>();
	public static ArrayList<String> yearList = new ArrayList<String>();
	public static int networkStats = 1;
	public static int versionCode, serviceCode;

	public boolean checkNetwork() {
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet(); // true or
																	// false
		if (!isInternetPresent) {
			networkStats = -9;
			return false;
		}
		isInternetPresent = cd.isConnByHttp(); // true or false
		if (!isInternetPresent) {
			networkStats = -1;
			return false;
		}
		return true;
	}

	public boolean checkNetworkMeg(Context context) {
		switch (networkStats) {
		case -1:
			Toast toast = Toast.makeText(context,
					R.string.error_network_server, Toast.LENGTH_LONG);
			toast.show();
			return false;

		case -9:
			toast = Toast.makeText(context, R.string.error_network,
					Toast.LENGTH_LONG);
			toast.show();
			return false;
		}
		return true;
	}

	public void readCityJsonfile() {
		String fileName = "region.json";
		String res = "";
		try {

			InputStream in = getResources().getAssets().open(fileName);

			int length = in.available();

			byte[] buffer = new byte[length];

			in.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
}
