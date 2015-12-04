package com.example.broadcasts;

import com.example.services.TrafficStatService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TrafficReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("com.example.broadcasts.TrafficReceiver")) {
			Intent i = new Intent();
			i.setClass(context, TrafficStatService.class);
			// 启动service
			// 多次调用startService并不会启动多个service 而是会多次调用onStart
			context.startService(i);
		}
	}

}
