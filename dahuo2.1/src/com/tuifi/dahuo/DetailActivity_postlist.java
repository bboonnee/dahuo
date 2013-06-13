package com.tuifi.dahuo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tuifi.dahuo.adapater.ShopAdapater;
import com.tuifi.dahuo.controller.MsgController;
import com.tuifi.dahuo.model.Msg;

public class DetailActivity_postlist extends Activity {
	private static final String LOG = "DetailActivity_postlist";
	ListView plist;
	private listTask mAuthTask = null;
	TextView city;
	String current_city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_postlist);
		ApplicationMap.allActivity.add(this);
		plist = (ListView) findViewById(R.id.detail_postlist);
		attemptReadList();
		findViewById(R.id.btn_back).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			current_city = extras.getString("add");			
			//根据获取的类型读取数据						
		}
		city = (TextView) findViewById(R.id.post_city);
		if(current_city!=null)
		{
			city.setText(current_city);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_activity_shoplist, menu);
		return true;
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

	private void loadListOffer() {

		// plist = (ListView) findViewById(R.id.list_plist);
		if (MsgController.MsgList != null) {
			ShopAdapater adapater = new ShopAdapater(this);
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
							Intent intent = new Intent(
									DetailActivity_postlist.this,
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
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
		try {
			imm.hideSoftInputFromWindow(plist.getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class listTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			boolean re = false;
			try {
				MsgController.MsgList = MsgController.getInfoshoplistFromJson(
						DetailActivity_postlist.this, current_city);
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
