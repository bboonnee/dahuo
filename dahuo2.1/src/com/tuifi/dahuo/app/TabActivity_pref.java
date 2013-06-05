package com.tuifi.dahuo.app;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tuifi.dahuo.ApplicationMap;
import com.tuifi.dahuo.R;

public class TabActivity_pref extends PreferenceActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		ApplicationMap.allActivity.add(this);
	}
}
