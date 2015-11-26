package com.example.testsqlite;

import android.app.Application;

public class TrafficApplication extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TrafficManager.init(this);
	}

}
