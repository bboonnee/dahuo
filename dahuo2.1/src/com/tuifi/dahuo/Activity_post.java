package com.tuifi.dahuo;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.CityActivity;
import com.tuifi.dahuo.R;
import com.tuifi.dahuo.controller.MsgController;
import com.tuifi.dahuo.model.Msg;

public class Activity_post extends Activity {
	private static final String LOG = "Activity_post";
	// Fragment对应的标签，当Fragment依附于Activity时得到
	private String tag;
	public SharedPreferences preferences;	
	EditText startcity, tocity, EtCargoName, EtWeight;
	static EditText Etdeadline;
	// 声明一个独一无二的标识，来作为要显示DatePicker的Dialog的ID：
	static final int DATE_DIALOG_ID = 0;
	// 用来保存年月日：
	public static int mYear;
	public static int mMonth;
	public static int mDay;
	private PostTask mAuthTask = null;
	Activity ac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		ApplicationMap.allActivity.add(this);
		
		TableLayout post_layout=(TableLayout) findViewById(R.id.post_layout);
		post_layout.setBackgroundResource(R.color.white);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		Etdeadline = (EditText) findViewById(R.id.Etdeadline);
		EtCargoName = (EditText) findViewById(R.id.EtCargoName);
		EtWeight = (EditText) findViewById(R.id.EtWeight);
		startcity = (EditText) findViewById(R.id.Etstartcity);
		tocity = (EditText) findViewById(R.id.Ettocity);
		
			
		startcity.setText(((ApplicationMap)getApplication()).mAdd);
		((ApplicationMap)getApplication()).mEt = startcity;
		
		startcity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Activity_post.this,
						CityActivity.class);
				String s = startcity.getText().toString();
				intent.putExtra("add", s);
				intent.putExtra("type", "postStartAdd");

				startActivity(intent);

			}
		});
		tocity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Activity_post.this,
						CityActivity.class);	
				String s = tocity.getText().toString();
				intent.putExtra("add", s);
				intent.putExtra("type", "postEndAdd");
				startActivity(intent);

			}
		});
		Etdeadline.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/*
				 * Intent intent = new Intent(); intent.setClass(getActivity(),
				 * DatePickerActivity.class);
				 * //DatePickerDemo.this.startActivity(intent);//对比
				 * getActivity().startActivityForResult(intent, 1000);//数字随意
				 */

				// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(DATE_DIALOG_ID);
			}
		});
		findViewById(R.id.btnpost).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						if (checkValidInput()) {

							if (mAuthTask != null) {
								return;
							}
							mAuthTask = new PostTask();
							mAuthTask.execute((Void) null);

						}

					}
				});
		findViewById(R.id.btn_back).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});
		// 获得当前的日期：
		final Calendar currentDate = Calendar.getInstance();
		mYear = currentDate.get(Calendar.YEAR);
		mMonth = currentDate.get(Calendar.MONTH);
		mDay = currentDate.get(Calendar.DAY_OF_MONTH);
		// 设置文本的内容：
		Etdeadline.setText(new StringBuilder().append(mYear).append("年")
				.append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
				.append(mDay).append("日"));

	}

	// 需要定义弹出的DatePicker对话框的事件监听器：
	private DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			// 设置文本的内容：
			Etdeadline.setText(new StringBuilder().append(mYear).append("年")
					.append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(mDay).append("日"));
		}
	};

	/**
	 * 当Activity调用showDialog函数时会触发该函数的调用：
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);

		}
		return null;
	}

	public boolean checkValidInput() {
		if (EtCargoName.getText().toString().trim().equals("")) {
			EtCargoName.setError(getString(R.string.error_field_required));
			EtCargoName.requestFocus();
			return false;
		}
		if (EtWeight.getText().toString().trim().equals("")) {
			EtWeight.setError(getString(R.string.error_field_required));
			EtWeight.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class PostTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			Msg msg = new Msg();
			msg.setstartadd(startcity.getText().toString());
			msg.setendadd(tocity.getText().toString());
			msg.setremark(EtCargoName.getText().toString());
			msg.setweight(EtWeight.getText().toString());
			String deadline = Etdeadline.getText().toString();
			msg.setdeadline(deadline);

			MsgController.savemsgFromJson(Activity_post.this, msg);

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			if (success) {
				Toast toast = Toast.makeText(Activity_post.this, "信息发布成功",
						Toast.LENGTH_LONG);
				toast.show();

			} else {
				Toast toast = Toast.makeText(Activity_post.this, "信息发布失败",
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;

		}
	}

	@Override
	public void onPause() {
		super.onPause();
		// 把数据保存到类似于Session之类的存储集合里面

		Editor edit = preferences.edit();
		//edit.putString("startregion", startcity.getText().toString());
		edit.putString("toregion", tocity.getText().toString());
		edit.commit();
	}

	// 重启：onStart()->onResume()
	@Override
	public void onResume() {
		super.onResume();

		// 从共享数据存储对象中获取所需的数据
		//String start = preferences.getString("startregion", null);
		String to = preferences.getString("toregion", null);
		//startcity.setText(start);
		tocity.setText(to);
		if(ApplicationMap.postStartAdd!=null)
		{
			String add=startcity.getText().toString();
				try{
					add =ApplicationMap.postStartAdd.get(0).REGION_NAME;
					add +=ApplicationMap.postStartAdd.get(1).REGION_NAME;
					add +=ApplicationMap.postStartAdd.get(2).REGION_NAME;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				 startcity.setText(add);						
		}
		if(ApplicationMap.postEndAdd!=null)
		{
			String add=startcity.getText().toString();
				try{
					add =ApplicationMap.postEndAdd.get(0).REGION_NAME;
					add +=ApplicationMap.postEndAdd.get(1).REGION_NAME;
					add +=ApplicationMap.postEndAdd.get(2).REGION_NAME;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				 tocity.setText(add);						
		}
	}

	// 处理返回的结果：
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		mYear = Integer.parseInt(data.getStringExtra("year"));
		mMonth = Integer.parseInt(data.getStringExtra("month"));
		mDay = Integer.parseInt(data.getStringExtra("day"));
		// 设置文本的内容：
		Etdeadline.setText(new StringBuilder().append(mYear).append("年")
				.append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
				.append(mDay).append("日"));
	}

}
