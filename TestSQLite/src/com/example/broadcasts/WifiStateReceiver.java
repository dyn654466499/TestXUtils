package com.example.broadcasts;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.beans.TrafficDetailInfo;
import com.example.testsqlite.TrafficListActivity;
import com.example.utils.DBUtils;

/**
 * 监听wifi的状态，记得在AndroidManifest加上权限
 * 
 * @author 邓耀宁
 * 
 */
public class WifiStateReceiver extends BroadcastReceiver {
	private static final String TAG = WifiStateReceiver.class.getName();
	private String wifi_ssid="";
	private boolean hasWifi = false;
	
	@SuppressLint("InlinedApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction()
				.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			Log.e(TrafficListActivity.class.getName(), "WIFI_STATE_CHANGED_ACTION");
			int wifistate = intent.getIntExtra(
					WifiManager.EXTRA_WIFI_STATE,
					WifiManager.WIFI_STATE_DISABLED);
			/**
			 * 仅仅判断wifi是否打开，并不判断是否连接！
			 */
			if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
				Log.e(TrafficListActivity.class.getName(), "wifi stop");

			} else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {

				Log.e(TrafficListActivity.class.getName(), "wifi start");
				hasWifi = true;
			}
		}

		if (intent.getAction().equals(
				WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
			Log.e(TrafficListActivity.class.getName(), "NETWORK_STATE_CHANGED_ACTION");
			NetworkInfo info = intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (hasWifi) {
				if (null != info && "WIFI".equals(info.getTypeName())) {
					State state = info.getState();
					if (state == State.CONNECTED) {
						Log.e(TAG, "wifi isConnected");
						WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
						WifiInfo wifiInfo = wifiManager.getConnectionInfo();
						wifi_ssid = wifiInfo.getSSID();
						Log.e(TAG, "wifi ssid = " + wifi_ssid);

						/**
						 * 记录当前uid应用的流量。
						 */
						//recordWifiState(context,wifi_ssid);
					} else if (state == State.DISCONNECTED) {
						Log.e(TAG, "wifi disConnected");
						/**
						 * 如果关闭,结余本次wifi过程中 uid应用的 流量
						 */
						//recordWifiState(context,wifi_ssid);
						hasWifi = false;
					}
				}
			}
		}
	}
	
	private void recordWifiState(final Context mContext,final String ssid){
		new AsyncTask<Void, Void, List<TrafficDetailInfo>>(){
 			@Override
 			protected void onPreExecute() {
 				// TODO Auto-generated method stub
 				super.onPreExecute();
 			}

 			@Override
 			protected List<TrafficDetailInfo> doInBackground(Void... params) {
 				// TODO Auto-generated method stub
 				PackageManager pm = mContext.getPackageManager();
 				LinkedList<TrafficDetailInfo> trafficInfos = new LinkedList<TrafficDetailInfo>();
 				List<ApplicationInfo> appliactaionInfos = pm.getInstalledApplications(0);
 				 for(ApplicationInfo applicationInfo : appliactaionInfos){
 					    /**
 					     * 判断应用是否有网络访问权限，如果有，该应用才属于流量统计范围之内。
 					     */
 					 boolean hasInternetPermission = (PackageManager.PERMISSION_GRANTED ==   
 				                pm.checkPermission("android.permission.INTERNET", applicationInfo.packageName));
 					 if(hasInternetPermission){
 			        	int uid = applicationInfo.uid;    // 获得软件uid
 			        	//proc/uid_stat/10086
 			        	//方法返回值 -1 代表的是应用程序没有产生流量 或者操作系统不支持流量统计
 			        	long tx = TrafficStats.getUidTxBytes(uid);//发送的 上传的流量byte
 			        	long rx = TrafficStats.getUidRxBytes(uid);//下载的流量 byte
 			        	TrafficDetailInfo info = new TrafficDetailInfo();
 			        	info.setPhoneNum("15277104415");
 			        	info.setData(rx+tx);
 			        	info.setNetType("wifi");
 			        	info.setWifi_ssid(ssid);
 			        	info.setTime(System.currentTimeMillis());
 			        	info.setBundleID(applicationInfo.packageName);
 			        	trafficInfos.add(info);
 			        }
 				 }
 				return trafficInfos;
 			}

 			@Override
 			protected void onPostExecute(List<TrafficDetailInfo> result) {
 				// TODO Auto-generated method stub
 				super.onPostExecute(result);
 				DBUtils.getInstance(mContext).insertTrafficInfo(result);
 			}
 			
 		}.execute();
	}
}
