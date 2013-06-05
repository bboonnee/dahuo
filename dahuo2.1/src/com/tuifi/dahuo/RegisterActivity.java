package com.tuifi.dahuo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tuifi.dahuo.app.MainActivity;
import com.tuifi.dahuo.controller.LoginController;
import com.tuifi.dahuo.model.User;
import com.tuifi.dahuo.tools.MD5Utils;
import com.tuifi.dahuo.tools.constActivity;

/**
 * 2 需要判断是否已经注册 2 需要返回登录界面
 * 
 */

public class RegisterActivity extends constActivity {
	private static final String LOG = "LoginActivity";
	private UserRegTask mAuthTask = null;

	EditText reg_mobile, reg_password, reg_name;
	public static final int REFRESH_LOGIN = 1;
	public SharedPreferences preferences;

	private View mRegFormView;
	private View mRegStatusView;
	private TextView mRegStatusMessageView;
	private static final String[] m={"我是车主，找货源","我是货主，找货车"}; 
	private Spinner SpinnerUsertype; 
	private ArrayAdapter<String> adapter; 

	// public ProgressDialog pd;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		// init button and edittext
		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		reg_mobile = (EditText) findViewById(R.id.reg_mobile);
		reg_password = (EditText) findViewById(R.id.reg_password);
		reg_name = (EditText) findViewById(R.id.reg_name);

		mRegFormView = findViewById(R.id.reg2_form);
		mRegStatusView = findViewById(R.id.reg_status);
		mRegStatusMessageView = (TextView) findViewById(R.id.reg_status_message);

		SpinnerUsertype =(Spinner) findViewById(R.id.SpinnerUsertype);  
		//将可选内容与ArrayAdapter连接起来  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);  
          
        //设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
          
        //将adapter 添加到spinner中  
        SpinnerUsertype.setAdapter(adapter);  
          
        //添加事件Spinner事件监听    
        SpinnerUsertype.setOnItemSelectedListener(new SpinnerSelectedListener());  
          
        //设置默认值  
        SpinnerUsertype.setVisibility(View.VISIBLE);  
		//
		findViewById(R.id.action_register).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptNext();
					}
				});
		findViewById(R.id.action_backlogin).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// 启动reg
						Intent intent = new Intent(RegisterActivity.this,
								LoginActivity.class);
						startActivity(intent);
						finish();
					}
				});
	}
	//使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}  
  
       
    }  
	private void regButtonClick() {
		mRegStatusMessageView.setText(R.string.reg_progress_signing_in);
		showProgress(true);
		mAuthTask = new UserRegTask();
		mAuthTask.execute((Void) null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 把数据保存到类似于Session之类的存储集合里面
		Editor edit = preferences.edit();
		edit.putString("reg_mobile", reg_mobile.getText().toString());
		edit.commit();
	}

	// 重启：onStart()->onResume()
	@Override
	protected void onResume() {
		super.onResume();

		// 从共享数据存储对象中获取所需的数据
		String mobile = preferences.getString("reg_mobile", null);
		if (mobile != null) {
			reg_mobile.setText(mobile);
		}

	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptNext() {
		// Reset errors.
		reg_mobile.setError(null);
		reg_password.setError(null);
		reg_name.setError(null);

		// Store values at the time of the login attempt.
		String mMobile = reg_mobile.getText().toString();
		String mPassword = reg_password.getText().toString();
		String mName = reg_name.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(mMobile)) {
			reg_mobile.setError(getString(R.string.error_field_required));
			focusView = reg_mobile;
			cancel = true;
		} else if (mMobile.length() != 11) {
			reg_mobile.setError(getString(R.string.error_invalid_mobile));
			focusView = reg_mobile;
			cancel = true;
		}
		// Check for a valid name.
		if (TextUtils.isEmpty(mName)) {
			reg_name.setError(getString(R.string.error_field_required));
			focusView = reg_name;
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			reg_password.setError(getString(R.string.error_field_required));
			focusView = reg_password;
			cancel = true;
		} else if (mPassword.length() < 5) {
			reg_password.setError(getString(R.string.error_invalid_password));
			focusView = reg_password;
			cancel = true;
		}
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			regButtonClick();

		}
	}

	private void showProgress(final boolean show) {

		// The ViewPropertyAnimator APIs are not available, so simply show
		// and hide the relevant UI components.
		mRegStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
		mRegFormView.setVisibility(show ? View.GONE : View.VISIBLE);

	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserRegTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			String mobile = reg_mobile.getText().toString().trim();
			String password = reg_password.getText().toString().trim();
			String name = reg_name.getText().toString().trim();
			String type = String.valueOf(SpinnerUsertype.getSelectedItemPosition());
			try {
				User u = LoginController.register(mobile, password, name,type);
				String md5 = MD5Utils.MD5(password);
				if (u.password.equals(md5)) {
					ApplicationMap.currentUser = u;
					return true;
				} else if (u.result.equals("-1")) {
					reg_mobile
							.setError(getString(R.string.error_incorrect_userextis));
					reg_mobile.requestFocus();
					return false;
				} else {
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(LOG, e.toString());
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;

			// 登录成功
			if (success) {
				// 启动主页
				savePreference();
				Intent intent = new Intent(RegisterActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				reg_password.setError(getString(R.string.error_incorrect_reg));
			}
			showProgress(false);
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	public void savePreference() {
		if (ApplicationMap.currentUser != null) {
			Editor edit = preferences.edit();
			edit.putBoolean("autologin", true);
			edit.putString("uid", ApplicationMap.currentUser.id);
			edit.putString("uname", ApplicationMap.currentUser.name);
			edit.putString("mobile", ApplicationMap.currentUser.mobile);
			edit.putString("type", ApplicationMap.currentUser.type);
			edit.commit();
		}

	}
}
