package com.example.testsqlite;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.example.adapters.TrafficStateAdapter;
import com.example.beans.TrafficStateInfo;

public class TrafficListActivity extends Activity {
	LinkedList<TrafficStateInfo> trafficInfos;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading);
		mContext = this;
        //1.获取一个包管理器。
        final PackageManager pm = getPackageManager();
        //2.遍历手机操作系统 获取所有的应用程序的uid
        final List<ApplicationInfo> appliactaionInfos = pm.getInstalledApplications(0);
        trafficInfos = new LinkedList<TrafficStateInfo>();
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
			        	TrafficStateInfo info = new TrafficStateInfo();
			        	info.setRx(rx);
			        	info.setTx(tx);
			        	info.setWifi_ssid("test");
			        	info.setBundleID(applicationInfo.packageName);
			        	info.setApp_icon(applicationInfo.loadIcon(pm));
			        	info.setApp_name(applicationInfo.loadLabel(pm).toString());
			        	trafficInfos.add(info);
			        }
				 }
//			    TrafficStats.getMobileRxBytes();//获取手机通过 2G/3G 接收的字节流量总数
//			    TrafficStats.getMobileRxPackets();//获取手机通过 2G/3G 接收的数据包总数
//			    TrafficStats.getMobileTxBytes();//获取手机通过 2G/3G 发出的字节流量总数
//			    TrafficStats.getMobileTxPackets();//获取手机通过 2G/3G 发出的数据包总数
//			    TrafficStats.getTotalRxBytes();//获取手机通过所有网络方式接收的字节流量总数(包括 wifi)
//			    TrafficStats.getTotalRxPackets();//获取手机通过所有网络方式接收的数据包总数(包括 wifi)
//			    TrafficStats.getTotalTxBytes();//获取手机通过所有网络方式发送的字节流量总数(包括 wifi)
//			    TrafficStats.getTotalTxPackets();//获取手机通过所有网络方式发送的数据包总数(包括 wifi)
//			    TrafficStats.getUidRxBytes(uid);//获取手机指定 UID 对应的应程序用通过所有网络方式接收的字节流量总数(包括 wifi)
//			    TrafficStats.getUidTxBytes(uid);//获取手机指定 UID 对应的应用程序通过所有网络方式发送的字节流量总数(包括 wifi)
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
			}
			
		}.execute();
	}

	/**
	 * 监听wifi的状态，记得在AndroidManifest加上权限
	 * @author 邓耀宁
	 *
	 */
	public class WifiStateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
		    if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
		        int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
		        if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
		            //如果关闭,结余本次wifi过程中 uid应用的 流量
		        } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
		            //记录当前uid应用的流量.
		        }
		    }
		}
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
}
