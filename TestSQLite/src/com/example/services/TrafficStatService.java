package com.example.services;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import com.example.beans.TrafficDetailInfo;
import com.example.beans.TrafficInfo;
import com.example.broadcasts.TrafficReceiver;
import com.example.testsqlite.TrafficListActivity;
import com.example.utils.CommonUtils;
import com.example.utils.DBUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.TrafficStats;
import android.os.IBinder;
import android.util.Log;

public class TrafficStatService extends Service{
	private static final String TAG = TrafficStatService.class.getName();
	private Timer timer; 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.e(TAG,"Traffic service start");
		
		SharedPreferences preferences = CommonUtils
				.getPackageNamePreferences(this);
		if (preferences == null) {
			Log.e(TAG,"preferences == null");
			PackageManager pm = getPackageManager();
	        //遍历手机操作系统 获取所有的应用程序的uid
	        final List<ApplicationInfo> appliactaionInfos = pm.getInstalledApplications(0);
			preferences = getSharedPreferences(
					"savePackageName", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			for (ApplicationInfo applicationInfo : appliactaionInfos) {
				/**
				 * 判断应用是否有网络访问权限，如果有，该应用才属于流量统计范围之内。
				 */
				boolean hasInternetPermission = (PackageManager.PERMISSION_GRANTED == pm
						.checkPermission("android.permission.INTERNET",
								applicationInfo.packageName));
				if (hasInternetPermission) {
					editor.putInt(applicationInfo.packageName,
							applicationInfo.uid);
				}
				editor.commit();
			}
		} else {
//			Log.e(TAG,"preferences != null");
//			Map<String, Integer> map = (Map<String, Integer>) preferences.getAll();
//			Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
//			/**
//			 * new一个数组保存packageName（ 比每次new String[]{entry.getKey()}更省内存）
//			 */
//			String[] temp = new String[1];
//			while (iterator.hasNext()) {
//				Entry<String, Integer> entry = iterator.next();
//				temp[0] = entry.getKey();
//				List<TrafficInfo> infos = DBUtils.getInstance(this).selectTrafficInfoByToday(DBUtils.TABLE_TRAFFIC_INFO, "time", "bundleID=?",temp, null, null, null);
//				if(infos!=null&&infos.size()>0)
//			    Log.e(TAG, "packageName = "+temp[0]+",开始时间="+CommonUtils.getFormatTime(infos.get(0).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(0).getData())+
//			    ",结束时间="+CommonUtils.getFormatTime(infos.get(infos.size()-1).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(infos.size()-1).getData()));
//				
//			}
			String[] temp = new String[]{"com.tencent.mm"};
			Log.e(TAG,"preferences != null");
			List<TrafficInfo> infos = DBUtils.getInstance(this).selectTrafficInfoByToday(DBUtils.TABLE_TRAFFIC_INFO, "time", "bundleID=?",temp, null, null, null);
			if(infos!=null&&infos.size()>0)
			 Log.e(TAG, "packageName = "+temp[0]+",开始时间="+CommonUtils.getFormatTime(infos.get(0).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(0).getData())+
			    ",结束时间="+CommonUtils.getFormatTime(infos.get(infos.size()-1).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(infos.size()-1).getData()));
				
		}
//		Intent intent_service = new Intent(this, TrafficStatService.class);
//		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent_service, 0);
//		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5 * 1000,pendingIntent);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		startService(new Intent(this,TrafficReceiver.class));
		if(timer!=null)timer.cancel();
	}
	
}
