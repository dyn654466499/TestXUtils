package com.example.testsqlite;

import com.example.services.TrafficStatService;

import android.app.Application;
import android.content.Intent;

public class TrafficApplication extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TrafficManager.init(this);
		//startService(new Intent(this,TrafficStatService.class));
	}

	
	
}
