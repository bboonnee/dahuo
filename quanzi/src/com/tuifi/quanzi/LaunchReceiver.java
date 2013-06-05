/**
 * 
 */
package com.tuifi.quanzi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @version 1.0
 */
public class LaunchReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
			Intent tIntent = new Intent(context, LaunchService.class);
			context.startService(tIntent);	
	}
}