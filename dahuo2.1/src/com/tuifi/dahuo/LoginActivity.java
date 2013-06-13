package com.tuifi.dahuo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tuifi.dahuo.app.MainActivity;
import com.tuifi.dahuo.controller.LoginController;
import com.tuifi.dahuo.model.User;
import com.tuifi.dahuo.tools.MD5Utils;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public  class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String LOG = "LoginActivity";

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mMobile;
	private String mPassword;

	// UI references.
	private EditText mMobileView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView, tv_error;
	private Dialog alertDialog;	
	public SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//((ApplicationMap) getApplication()).allActivity.add(this);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);		
		// Set up the login form.
		// mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mMobileView = (EditText) findViewById(R.id.mobile);
		mMobileView.setText(ApplicationMap.currentUser.mobile);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		tv_error = (TextView) findViewById(R.id.tv_error);
		tv_error.setTextColor(android.graphics.Color.RED);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		findViewById(R.id.action_register).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// 启动reg
						Intent intent = new Intent(LoginActivity.this,
								RegisterActivity.class);
						startActivity(intent);
						finish();
					}
				});
		alertDialog = new AlertDialog.Builder(LoginActivity.this)
				.setTitle("您的账户不存在")
				.setMessage("您需要注册新用户么？")
				.setIcon(R.drawable.logo)
				.setPositiveButton("注册", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showProgress(false);
						Intent intent = new Intent(LoginActivity.this,
								RegisterActivity.class);
						startActivity(intent);
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showProgress(false);
						// TODO Auto-generated method stub
					}
				}).create();
	}

	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mMobileView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mMobile = mMobileView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mMobile)) {
			mMobileView.setError(getString(R.string.error_field_required));
			focusView = mMobileView;
			cancel = true;
		} else if (mMobile.length() != 11) {
			mMobileView.setError(getString(R.string.error_invalid_mobile));
			focusView = mMobileView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	private void showProgress(final boolean show) {
		mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
		mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			Editor edit = preferences.edit();
			edit.putString("mobile", mMobile);
			edit.commit();

			User user = new User();
			try {
				// Simulate network access.
				user = LoginController.login(mMobile, mPassword);
				if (user.result.equals("false")) {
					/*
					 * Looper.prepare(); alertDialog.show(); Looper.loop();
					 */
					tv_error.setText("账号不存在，请点击注册按钮注册");
					return false;

				} else if (user.result.equals("overtime")) {
					tv_error.setText("服务器链接超时");
					return false;

				} else if (user.result.equals("connerr")) {
					tv_error.setText("服务器无法连接，请检查网络");
					return false;

				} else {
					String md5 = MD5Utils.MD5(mPassword);
					if (md5.equals(user.password)) {
						ApplicationMap.currentUser = user;
						return true;
					} else {
						mMobileView.setError("密码错误");
						tv_error.setText("密码错误");
						mPasswordView.requestFocus();
						return false;
					}

				}
				// Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(LOG, e.toString());
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				// 启动主页
				tv_error.setText("");
				savePreference(ApplicationMap.currentUser);
				/*Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);*/
				finish();
			} else {
				/*
				 * mPasswordView
				 * .setError(getString(R.string.error_incorrect_password));
				 * mPasswordView.requestFocus();
				 */
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	public void savePreference(User user) {
		if (user != null) {
			Editor edit = preferences.edit();
			edit.putBoolean("autologin", true);
			edit.putString("uid", user.id);
			edit.putString("uname", user.name);
			edit.putString("mobile", user.mobile);
			edit.putString("type", user.type);
			edit.commit();
		} else {
			Editor edit = preferences.edit();
			edit.putBoolean("autologin", false);
			edit.putString("uid", "");
			edit.putString("uname", "");
			edit.commit();
		}

	}
	
}
