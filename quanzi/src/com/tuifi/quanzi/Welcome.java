package com.tuifi.quanzi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tuifi.quanzi.controller.UserController;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.util.Config;
import com.tuifi.quanzi.util.FrameActivity;
import com.tuifi.quanzi.util.MyActivity;
import com.tuifi.quanzi.util.NetworkTool;

public class Welcome extends FrameActivity {
	private static String LOG = "Welcome";
	String pass, mobile;
	int uid;
	static int flag = 0;
	protected boolean _active = true;
	protected int _splashTime = 1000;
	String imei = "";
	public SharedPreferences preferences;

	private static final String TAG = "Update";
	public ProgressDialog pBar;
	private Handler handler = new Handler();

	private int newVerCode = 0;
	private String newVerName = "";
	int flagUpdate = 0;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ȡ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ȡ��״̬��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		try {
			this.setContentView(R.layout.welcome);
			LinearLayout iv = (LinearLayout) this
					.findViewById(R.id.welcomelayout);
			AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
			aa.setDuration(1000);
			iv.startAnimation(aa);
			aa.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					String autologin = preferences.getString("autologin", "");
					if ((mobileme.equals("")) || autologin.equals("")) {
						// ����Loginin Activity
						Intent intent = new Intent(Welcome.this, Loginin.class);
						startActivity(intent);
						finish();

					} else {
						// ����Main Activity
						try {
							Intent intent = new Intent(Welcome.this,MainTabActivity.class);							
							startActivity(intent);
							// ������Activity
							finish();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					initform();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG, LOG + "==========ERROR " + e.toString());
		}

	}


	private void initform() {
		vercode = Config.getVerCode(this);
		// ��ȡ������¼
		// ��ȡ������mobile
		preferences = getSharedPreferences("quanziinfo", MODE_PRIVATE);

		// ����ʹ�� �洢��preferences
/*		 Editor edit = preferences.edit();
		 edit.putString("autologin", "true");
		 edit.putString("mobileme", "13911181917");
		 edit.commit();*/
		// ��������ڣ����ȡ�����δ��¼�����¼������Ѿ���¼�������������
/*	try {

			myuserList = UserController.loadList(this,myuid,0);
			UserController.userList = myuserList;
			Log.i(LOG, "finish reading myuserList");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		mobileme = preferences.getString("mobileme", "");
		mobileinput = preferences.getString("mobileinput", "");
	    myuid = preferences.getString("myuid", "");
		if (myuid.equals("")) myuid = UserController.readUidFromMobile(mobileme);		
		MainService.msuid = myuid;
		MainService.msmobile= mobileme;


		// ���͵�¼��Ϣ
		getphonenum();
		if(imei!=null)
		if (!imei.equals(""))
			try {
				Map<String, String> map = new HashMap<String, String>();
				map.put("do", "loginRecord");
				map.put("mobile", imei);
				JSONObject jb = queryObject(map, 1);
				String t = jb.getString("result");
				if (t.equals("overtime")) {
					Toast.makeText(this, "���ӳ�ʱ ���������磬�Ժ�ˢ�£�",
							Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (Exception e) {
				//
				e.printStackTrace();
			}

		//
		// ���汾��Ϣ
		/*
		 * if (getServerVerCode()) {
		 * 
		 * if (newVerCode > vercode) { flagUpdate = 1; doNewVersionUpdate();
		 * 
		 * } else { // notNewVersionShow(); } }
		 */
	}

	// ��ȡ��������IMEI����Ϣ
	private void getphonenum() {
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {
			// String deviceid = tm.getDeviceId();
			// String tel = tm.getLine1Number();
			imei = tm.getSimSerialNumber();
			// String imsi = tm.getSubscriberId();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG, "getphonenum " + e.toString());
		}
	}

	private boolean getServerVerCode() {
		try {
			String url = Config.UPDATE_SERVER + Config.UPDATE_VERJSON;
			String verjson = NetworkTool.getContent(url);
			JSONArray array = new JSONArray(verjson);
			if (array.length() > 0) {
				JSONObject obj = array.getJSONObject(0);
				try {
					newVerCode = Integer.parseInt(obj.getString("verCode"));
					newVerName = obj.getString("verName");
				} catch (Exception e) {
					newVerCode = -1;
					newVerName = "";
					return false;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}
		return true;
	}

	private void notNewVersionShow() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("��ǰ�汾:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(",\n�������°�,�������!");
		Dialog dialog = new AlertDialog.Builder(Welcome.this).setTitle("�������")
				.setMessage(sb.toString())// ��������
				.setPositiveButton("ȷ��",// ����ȷ����ť
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						}).create();// ����
		// ��ʾ�Ի���
		dialog.show();
	}

	private void doNewVersionUpdate() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("��ǰ�汾:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(", �����°汾:");
		sb.append(newVerName);
		sb.append(" Code:");
		sb.append(newVerCode);
		sb.append(", �Ƿ����?");
		Dialog dialog = new AlertDialog.Builder(Welcome.this)
				.setTitle("�������")
				.setMessage(sb.toString())
				// ��������
				.setPositiveButton("����",// ����ȷ����ť
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pBar = new ProgressDialog(Welcome.this);
								pBar.setTitle("��������");
								pBar.setMessage("���Ժ�...");
								pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								downFile(Config.UPDATE_SERVER
										+ Config.UPDATE_APKNAME);

							}

						})
				.setNegativeButton("�ݲ�����",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// ���"ȡ��"��ť֮���˳�����
								flagUpdate = 0;
								// finish();
							}
						}).create();// ����
		// ��ʾ�Ի���
		dialog.show();
	}

	void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {

						File file = new File(
								Environment.getExternalStorageDirectory(),
								Config.UPDATE_SAVENAME);
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
							}
						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					flagUpdate = 0;
				}
			}

		}.start();

	}

	void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();

			}
		});

	}

	void update() {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), Config.UPDATE_SAVENAME)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
}
