package com.tuifi.dahuo.app;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.DetailActivity;
import com.tuifi.dahuo.R;
import com.tuifi.dahuo.adapater.ShopAdapater;
import com.tuifi.dahuo.controller.MsgController;
import com.tuifi.dahuo.model.Msg;
import com.tuifi.dahuo.tools.formActivity;

public  class TabActivity_list extends formActivity{
	private static final String LOG = "TabActivity_list";
	private String tag;	
	public SharedPreferences preferences;	
	ListView plist;	
	TextView tv_add;
	ImageView example_left, example_right;
	private listTask mAuthTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.tabactivity_list);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		ApplicationMap.allActivity.add(this);
		
		tv_add=(TextView) findViewById(R.id.tv_add);		
		tv_add.setText(((ApplicationMap)getApplication()).mAdd);
		((ApplicationMap)getApplication()).mTv = tv_add;
		
		plist = (ListView) findViewById(R.id.postlist);
		attemptReadList();
		
		LinearLayout container=(LinearLayout)((ActivityGroup)getParent()).getWindow().findViewById(R.id.tab_top);
		example_right = (ImageView) container.findViewById(R.id.example_right);
		example_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptReadList();
			}
		});
		//tv.setText("This is Example 1 Activity");
	}
	public void attemptReadList() {
		if (mAuthTask != null) {
			return;
		}

		boolean cancel = false;
		View focusView = null;

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			/*
			 * mListStatusMessageView.setText(R.string.progress_searching);
			 * showProgress(true);
			 */
			mAuthTask = new listTask();
			mAuthTask.execute((Void) null);
		}
	}

	private void refreshListView(String value) {
		ShopAdapater ctadp = new ShopAdapater(TabActivity_list.this);
		if (MsgController.MsgList == null)
			return;
		if (plist == null)
			return;
		if (value == null || value.trim().length() == 0) {
			ctadp.setadaptList(MsgController.MsgList);
			plist.setAdapter(ctadp);
			return;
		}
		List<Msg> tmpList = new ArrayList<Msg>();
		if (MsgController.MsgList == null)
			return;

		for (Msg msg : MsgController.MsgList) {
			// 筛选符合value关键字的list
			if (msg.getremark() != null)
				if (msg.getremark().indexOf(value) >= 0) {
					tmpList.add(msg);
					continue;
				}

		}
		// if (tmpList.size() == 0) return;
		ctadp.setadaptList(tmpList);
		plist.setAdapter(ctadp);
		plist.invalidateViews();
	}

	private void loadListOffer() {

		// plist = (ListView) findViewById(R.id.list_plist);
		if (MsgController.MsgList != null) {
			ShopAdapater adapater = new ShopAdapater(TabActivity_list.this);
			// adapater.removeAll();
			plist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> lv, View view, int pos,
						long id) {
					// User ui = userList.get(pos);
					ShopAdapater ada = (ShopAdapater) plist.getAdapter();
					Msg ui = ada.adaptList.get(pos);
					// Object obj = view.getTag();
					if (ui != null) {
						// String id = obj.toString();
						try {
							// 启动reg
							Intent intent = new Intent(TabActivity_list.this,
									DetailActivity.class);

							Bundle b = new Bundle();
							b.putSerializable("id", ui.getId());
							intent.putExtras(b);
							startActivity(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});

			try {
				if (adapater != null) {
					adapater.setadaptList(MsgController.MsgList);
					plist.setAdapter(adapater);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// hide the keyboard
		InputMethodManager imm = (InputMethodManager) TabActivity_list.this.getSystemService(
						android.content.Context.INPUT_METHOD_SERVICE);
		try {
			imm.hideSoftInputFromWindow(plist.getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class listTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			boolean re = false;
			try {
				MsgController.MsgList = MsgController.getmsglistFromJson(
						TabActivity_list.this, null, null);
				if (MsgController.MsgList != null)
					return true;
				// Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(LOG, e.toString());
				return false;
			}
			// TODO: register the new account here.
			return re;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			// showProgress(false);

			if (success) {
				loadListOffer();
				// finish();
			} else {

			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			// showProgress(false);
		}
	}

}
