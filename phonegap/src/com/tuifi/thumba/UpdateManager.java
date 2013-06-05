package com.tuifi.thumba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 
 * @author wwj
 * @date 2012/11/17 ʵ��������µĹ�����
 */
public class UpdateManager extends Activity{
	private static String LOG = "UpdateManager";

	// ������...
	private static final int DOWNLOAD = 1;
	// �������
	private static final int DOWNLOAD_FINISH = 2;
	// ���������XML��Ϣ
	HashMap<String, String> mHashMap;
	// ���ر���·��
	private String mSavePath;
	// ��¼����������
	private int progress;
	// �Ƿ�ȡ������
	private boolean cancelUpdate = false;
	// �����Ķ���
	private Context mContext;
	// ������
	private ProgressBar mProgressBar;
	// ���½������ĶԻ���
	private Dialog mDownloadDialog;

	public static int versionCode,serviceCode;
	//public static final String UPDATE_URL = "http://192.168.1.5/down/version.json";

	public static String versionName,serviceName;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// �����С�����
			case DOWNLOAD:
				// ���½�����
				System.out.println(progress);
				mProgressBar.setProgress(progress);
				break;
			// �������
			case DOWNLOAD_FINISH:
				// ��װ�ļ�
				installApk();
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		super();
		this.mContext = context;
	}

	/**
	 * ����������
	 * type =0 ����ʾ��ʾ 1 ��ʾ��ʾ
	 */
	public void checkUpdate(boolean showToast) {
		if (isUpdateJson()) {
			// ��ʾ��ʾ�Ի���
			showNoticeDialog();
		} else {
			if(showToast)
			Toast.makeText(mContext, "��ǰ�Ѿ������°汾",Toast.LENGTH_SHORT).show();
		}
	}

	private void showNoticeDialog() {
		// TODO Auto-generated method stub
		// ����Ի���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("�廪MBA����");
		builder.setMessage("�����ǰ�汾"+versionName+versionCode+"���°汾"+serviceName+serviceCode+"�������ظ���");
		// ����
		builder.setPositiveButton("����",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						// ��ʾ���ضԻ���
						showDownloadDialog();
					}
				});
		// �Ժ����
		builder.setNegativeButton("�Ժ����",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();						
						// ������Activity
						finish();
					}
				});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog() {
		// ����������ضԻ���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("�������ظ���");
		// �����ضԻ������ӽ�����
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.softupdate, null);
		mProgressBar = (ProgressBar) view.findViewById(R.id.update_progress);
		builder.setView(view);
		builder.setNegativeButton("ȡ��",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						// ����ȡ��״̬
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// �����ļ�
		downloadApk();
	}

	/**
	 * ����APK�ļ�
	 */
	private void downloadApk() {
		// TODO Auto-generated method stub
		// �������߳��������
		new DownloadApkThread().start();
	}

	/**
	 * �������Ƿ��и��°汾
	 * 
	 * @return
	 */
	public boolean isUpdate() {
		// ��ȡ��ǰ����汾
		 versionCode = getVersionCode(mContext);
		 versionName = getVerName(mContext);
		// ��version.xml�ŵ������ϣ�Ȼ���ȡ�ļ���Ϣ
		InputStream inStream = ParseXmlService.class.getClassLoader()
				.getResourceAsStream("version.xml");
		// ����XML�ļ��� ����XML�ļ��Ƚ�С�����ʹ��DOM��ʽ���н���
		ParseXmlService service = new ParseXmlService();
		try {
			mHashMap = service.parseXml(inStream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (null != mHashMap) {
			 serviceCode = Integer.valueOf(mHashMap.get("version"));
			 serviceName = String.valueOf(mHashMap.get("VersionName"));
			// �汾�ж�
			if (serviceCode > versionCode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ����������汾�Ƿ��и��°汾
	 * 
	 * @return
	 */
	public boolean isUpdateJson() {
		// ��ȡ��ǰ����汾
		isUpdate();
		 versionCode = getVersionCode(mContext);
		 versionName = getVerName(mContext);
		 serviceCode = -1;
		try {
			serviceCode = getRemoteJSON(HttpUtil.UPDATE_URL);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG,"ClientProtocolException"+ e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG,"IOException"+ e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(LOG,"JSONException"+ e.toString());
		}
		// �汾�ж�
		if (serviceCode > versionCode) {
			return true;
		}

		return false;
	}

	private int getRemoteJSON(String url) throws ClientProtocolException,
			IOException, JSONException {
		// String url = String.format("http://%s/%s", host, VER_JSON);
		StringBuilder sb = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpResponse response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"), 8192);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
		}

		JSONObject object = (JSONObject) new JSONTokener(sb.toString())
				.nextValue();
		/*
		 * this.apkFullName = object.getString("ApkFullName"); this.versionName
		 * = object.getString("VersionName");
		 */
		serviceCode = Integer.valueOf(object.getInt("VersionCode"));
		serviceName = object.getString("VersionName");

		return serviceCode;
	}

	/**
	 * ��ȡ����汾��
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		// TODO Auto-generated method stub
		int versionCode = 0;

		// ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					"com.tuifi.thumba", 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionCode;
	}
	public static String getVerName(Context context) {  
        String verName = "";  
        try {  
            verName = context.getPackageManager().getPackageInfo(  
                    "com.tuifi.thumba", 0).versionName;  
        } catch (NameNotFoundException e) {  
        	e.printStackTrace();  
        }  
        return verName;     
}  
	/**
	 * �����ļ��߳�
	 * 
	 * @author Administrator
	 * 
	 */
	private class DownloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ��ȡSDCard��·��
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(mHashMap.get("url"));
					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// ��ȡ�ļ���С
					int length = conn.getContentLength();
					// ����������
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// ����ļ������ڣ��½�Ŀ¼
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					do {
						int numread = is.read(buf);
						count += numread;
						// �����������λ��
						progress = (int) (((float) count / length) * 100);
						// ���½���
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// �������
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// ���ȡ����ֹͣ����
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ȡ�����ضԻ�����ʾ
			mDownloadDialog.dismiss();
		}
	}

	/**
	 * ��װAPK�ļ�
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, mHashMap.get("name"));
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
	

}
