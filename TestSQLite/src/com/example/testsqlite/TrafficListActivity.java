package com.example.testsqlite;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.adapters.TrafficStateAdapter;
import com.example.beans.TrafficDetailInfo;
import com.example.beans.TrafficInfo;
import com.example.services.TrafficStatService;
import com.example.utils.CommonUtils;
import com.example.utils.DBUtils;

public class TrafficListActivity extends Activity {
	private static final String TAG = TrafficListActivity.class.getName();
	LinkedList<TrafficDetailInfo> trafficInfos;
	private Context mContext;
	//获取一个包管理器。
	private PackageManager pm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading);
		mContext = this;
		pm = getPackageManager();
        //遍历手机操作系统 获取所有的应用程序的uid
        final List<ApplicationInfo> appliactaionInfos = pm.getInstalledApplications(0);
        trafficInfos = new LinkedList<TrafficDetailInfo>();
		/**
         * 由于初始化数据操作耗时，故create一个子线程封装数据，并显示loading界面，以免UI线程卡顿。
         */
        new AsyncTask<Void, Void, Void>(){
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
//				for (ApplicationInfo applicationInfo : appliactaionInfos) {
//
//					/**
//					 * 判断应用是否有网络访问权限，如果有，该应用才属于流量统计范围之内。
//					 */
//					boolean hasInternetPermission = (PackageManager.PERMISSION_GRANTED == pm
//							.checkPermission("android.permission.INTERNET",
//									applicationInfo.packageName));
//					if (hasInternetPermission) {
//						int uid = applicationInfo.uid; // 获得软件uid
//						// proc/uid_stat/10086
//						// 方法返回值 -1 代表的是应用程序没有产生流量 或者操作系统不支持流量统计
//						long tx = TrafficStats.getUidTxBytes(uid);// 发送的
//																	// 上传的流量byte
//						long rx = TrafficStats.getUidRxBytes(uid);// 下载的流量 byte
//						TrafficDetailInfo info = new TrafficDetailInfo();
//						info.setRx(rx);
//						info.setTx(tx);
//						info.setWifi_ssid("test");
//						info.setBundleID(applicationInfo.packageName);
//						info.setApp_icon(applicationInfo.loadIcon(pm));
//						info.setApp_name(applicationInfo.loadLabel(pm)
//								.toString());
//						trafficInfos.add(info);
//					}
//
//				}
				
				SharedPreferences preferences = CommonUtils
						.getPackageNamePreferences(mContext);
				if (preferences == null) {
					Log.e(TAG,"preferences == null");
					preferences = mContext.getSharedPreferences(
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
							int uid = applicationInfo.uid; // 获得软件uid
							// 方法返回值 -1 代表的是应用程序没有产生流量 或者操作系统不支持流量统计
							long tx = TrafficStats.getUidTxBytes(uid);// 发送的
																		// 上传的流量byte
							long rx = TrafficStats.getUidRxBytes(uid);// 下载的流量 byte
							TrafficDetailInfo info = new TrafficDetailInfo();
							info.setRx(rx);
							info.setTx(tx);
							info.setData(rx+tx);
							info.setWifi_ssid("test");
							info.setBundleID(applicationInfo.packageName);
							info.setApp_icon(applicationInfo.loadIcon(pm));
							info.setApp_name(applicationInfo.loadLabel(pm)
									.toString());
							trafficInfos.add(info);
						}
						editor.commit();
					}
				} else {
					Map<String, Integer> map = (Map<String, Integer>) preferences
							.getAll();
					Iterator<Entry<String, Integer>> iterator = map.entrySet()
							.iterator();
					while (iterator.hasNext()) {
						Entry<String, Integer> entry = iterator.next();
						long tx = TrafficStats.getUidTxBytes(entry.getValue());// 上传的流量byte
						long rx = TrafficStats.getUidRxBytes(entry.getValue());// 下载的流量 byte
						TrafficDetailInfo info = new TrafficDetailInfo();
						info.setRx(rx);
						info.setTx(tx);
						info.setData(rx+tx);
						info.setWifi_ssid("test");
						info.setBundleID(entry.getKey());
						try {
							info.setApp_icon(pm.getApplicationIcon(entry.getKey()));
							info.setApp_name(pm.getApplicationLabel(pm.getApplicationInfo(entry.getKey(), PackageManager.GET_META_DATA)).toString());
						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						trafficInfos.add(info);
					}
				}
				//DBUtils.getInstance(mContext).insertTrafficInfo(trafficInfos);
				// TrafficStats.getMobileRxBytes();//获取手机通过 2G/3G 接收的字节流量总数
				// TrafficStats.getMobileRxPackets();//获取手机通过 2G/3G 接收的数据包总数
				// TrafficStats.getMobileTxBytes();//获取手机通过 2G/3G 发出的字节流量总数
				// TrafficStats.getMobileTxPackets();//获取手机通过 2G/3G 发出的数据包总数
				// TrafficStats.getTotalRxBytes();//获取手机通过所有网络方式接收的字节流量总数(包括
				// wifi)
				// TrafficStats.getTotalRxPackets();//获取手机通过所有网络方式接收的数据包总数(包括
				// wifi)
				// TrafficStats.getTotalTxBytes();//获取手机通过所有网络方式发送的字节流量总数(包括
				// wifi)
				// TrafficStats.getTotalTxPackets();//获取手机通过所有网络方式发送的数据包总数(包括
				// wifi)
				// TrafficStats.getUidRxBytes(uid);//获取手机指定 UID
				// 对应的应程序用通过所有网络方式接收的字节流量总数(包括 wifi)
				// TrafficStats.getUidTxBytes(uid);//获取手机指定 UID
				// 对应的应用程序通过所有网络方式发送的字节流量总数(包括 wifi)
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				setContentView(R.layout.activity_traffic_list);
				ListView listView_showTrafficState = (ListView)findViewById(R.id.listView_showTrafficState);
				TrafficStateAdapter adapter = new TrafficStateAdapter(mContext,trafficInfos);
				listView_showTrafficState.setAdapter(adapter);
				listView_showTrafficState.setOnItemClickListener(new OnItemClickListener() {


					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mContext,AppTrafficDayActivity.class);
					    intent.putExtra("packageName", trafficInfos.get(position).getBundleID());
						mContext.startActivity(intent);
						
					}
				});
				DBUtils.getInstance(mContext).insertTrafficInfo(trafficInfos);
			}
			
		}.execute();
		
		Log.e(TAG, "day startTime = "+CommonUtils.getFormatTime(CommonUtils.getTodayStartTime()));
		Log.e(TAG, "day endTime = "+CommonUtils.getFormatTime(CommonUtils.getTodayEndTime()));
		Log.e(TAG, "month startTime = "+CommonUtils.getFormatTime(CommonUtils.getCurrentMonthStartTime()));
		Log.e(TAG, "month endTime = "+CommonUtils.getFormatTime(CommonUtils.getCurrentMonthEndTime()));
        
		//CommonUtils.getSIMCardInfo(this);
//		Intent intent = new Intent(this, TrafficStatService.class);
//		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);
//		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000,pendingIntent);
		//alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5 * 1000,pendingIntent);
		
		String[] temp = new String[]{"com.tencent.mm"};
		List<TrafficInfo> infos = DBUtils.getInstance(this).selectTrafficInfoByToday(DBUtils.TABLE_TRAFFIC_INFO, "time", "bundleID=?",temp, null, null, null);
		if(infos!=null&&infos.size()>1)
		 Log.e(TAG, "packageName = "+temp[0]+",开始时间="+CommonUtils.getFormatTime(infos.get(0).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(0).getData())+
		    ",结束时间="+CommonUtils.getFormatTime(infos.get(infos.size()-1).getTime())+"|使用流量="+CommonUtils.getFormatTrafficSize(infos.get(infos.size()-1).getData()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.traffic_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
	
	
}
