package com.example.testsqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AppTrafficDayActivity extends Activity {
    private static final String TAG =AppTrafficDayActivity.class.getName(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apptrafficday_list);
		String packageName = getIntent().getStringExtra("packageName");
		String[] temp = new String[]{packageName};
		Log.e(TAG,"preferences != null");
//		List<TrafficInfo> infos = DBUtils.getInstance(this).selectTrafficInfoByToday(DBUtils.TABLE_TRAFFIC_INFO, "time", "bundleID=?",temp, null, null, null);
//		if(infos!=null&&infos.size()>0)
//		 Log.e(TAG, "packageName = "+temp[0]+",开始时间="+CommonUtils.getFormatTime(infos.get(0).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(0).getData())+
//		    ",结束时间="+CommonUtils.getFormatTime(infos.get(infos.size()-1).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(infos.size()-1).getData()));
//	
		}

}
