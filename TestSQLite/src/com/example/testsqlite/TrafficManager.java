package com.example.testsqlite;

import android.content.Context;

public class TrafficManager {

	private static Context applicationContext;
	
	public static void init(Context appContext){
		applicationContext = appContext;
	}

	public static Context getApplicationContext() {
		return applicationContext;
	}
	
}
