package com.example.testsqlite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.util.EncodingUtils;

import com.example.adapters.TrafficAdapter;
import com.example.beans.TrafficInfo;
import com.example.utils.CommonUtils;
import com.example.utils.DBUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {
	private TrafficAdapter adapter;
	private Context mContext;
	private LinkedList<TrafficInfo> trafficInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		mContext = this;
		// 1.获取一个包管理器。
		PackageManager pm = getPackageManager();
		// 2.遍历手机操作系统 获取所有的应用程序的uid
		final List<ApplicationInfo> appliactaionInfos = pm
				.getInstalledApplications(0);
		trafficInfo = new LinkedList<TrafficInfo>();

		/**
		 * 由于初始化数据操作耗时，故create一个子线程封装数据，并显示loading界面，以免UI线程卡顿。
		 */
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				for (ApplicationInfo applicationInfo : appliactaionInfos) {
					int uid = applicationInfo.uid; // 获得软件uid
					// proc/uid_stat/10086
					// 方法返回值 -1 代表的是应用程序没有产生流量 或者操作系统不支持流量统计
					long tx = TrafficStats.getUidTxBytes(uid);// 发送的 上传的流量byte
					long rx = TrafficStats.getUidRxBytes(uid);// 下载的流量 byte

					TrafficInfo info = new TrafficInfo();
					info.setPhoneNum("15277104415");
					info.setNetType("4G");
					info.setOperators("中国移动");
					info.setData(rx + tx);
					info.setTime(System.currentTimeMillis());
					info.setBundleID(applicationInfo.packageName);
					trafficInfo.add(info);
				}
				// TrafficStats.getMobileTxBytes();//获取手机3g/2g网络上传的总流量
				// TrafficStats.getMobileRxBytes();//手机2g/3g下载的总流量
				// TrafficStats.getTotalTxBytes();//手机全部网络接口 包括wifi，3g、2g上传的总流量
				// TrafficStats.getTotalRxBytes();//手机全部网络接口 包括wifi，3g、2g下载的总流量
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				setContentView(R.layout.activity_main);
				ListView listView = (ListView) findViewById(R.id.listView_data);
				adapter = new TrafficAdapter(mContext,
						new LinkedList<TrafficInfo>());
				listView.setAdapter(adapter);
			}

		}.execute();
	}

	/**
	 * 在xml中，button设置了onclick
	 * 
	 * @param view
	 */
	public void DBOperate(final View view) {
		switch (view.getId()) {
		case R.id.button_delete:
			DBUtils.getInstance(mContext).deleteTrafficInfo(
					DBUtils.TABLE_TRAFFIC_INFO, "phoneNum=?",
					new String[] { "15277104415" });
			CommonUtils.showTips(mContext, "执行删除操作");
			break;

		case R.id.button_select:
			new AsyncTask<Void, Void, List<TrafficInfo>>() {
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					view.setEnabled(false);
				}

				@Override
				protected List<TrafficInfo> doInBackground(Void... params) {
					// TODO Auto-generated method stub
					List<TrafficInfo> infos = DBUtils.getInstance(mContext)
							.selectTrafficInfo(DBUtils.TABLE_TRAFFIC_INFO,
									null, null, null, null, null, null);
					return infos;
				}

				@Override
				protected void onPostExecute(List<TrafficInfo> result) {
					// TODO Auto-generated method stub
					adapter.setTrafficInfo(result);
					adapter.notifyDataSetChanged();
					view.setEnabled(true);
					super.onPostExecute(result);
				}

			}.execute();
			CommonUtils.showTips(mContext, "执行查询操作");
			break;

		case R.id.button_insert:
			DBUtils.getInstance(mContext).insertTrafficInfo(trafficInfo);
			CommonUtils.showTips(mContext, "执行插入操作");
			break;

		case R.id.button_update:
			ContentValues values = new ContentValues();
			values.put("netType", "4G");
			values.put("wifi_ssid", "无");
			values.put("operators", "中国联通");
			DBUtils.getInstance(mContext).updateTrafficInfo(
					DBUtils.TABLE_TRAFFIC_INFO, values, null, null);
			CommonUtils.showTips(mContext, "执行更新操作");
			break;

		case R.id.button_app_show:
			new AsyncTask<Void, Void, List<TrafficInfo>>() {
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					view.setEnabled(false);
				}

				@Override
				protected List<TrafficInfo> doInBackground(Void... params) {
					// TODO Auto-generated method stub
					// GregorianCalendar cal = new GregorianCalendar();
					// cal.setTime(new Date());
					// //可以根据需要设置时区
					// cal.setTimeZone(TimeZone.getDefault());
					// cal.set(Calendar.HOUR_OF_DAY, 0);
					// cal.set(Calendar.MINUTE, 0);
					// cal.set(Calendar.SECOND, 0);
					// //毫秒可根据系统需要清除或不清除
					// cal.set(Calendar.MILLISECOND, 0);
					// long startTime = cal.getTimeInMillis();
					// long endTime = startTime + 24 * 3600 * 1000-1;
					//
					// List<TrafficInfo> infos = DBUtils.getInstance(mContext)
					// .selectTrafficInfo(DBUtils.TABLE_TRAFFIC_INFO,
					// null, "bundleID=? and time>=? and time<=?",
					// new String[] {
					// "com.tencent.mm",String.valueOf(startTime),
					// String.valueOf(endTime)}, null,
					// null, null);

					List<TrafficInfo> infos = DBUtils.getInstance(mContext)
							.selectTrafficInfoByCurrentDay(
									DBUtils.TABLE_TRAFFIC_INFO, "time",
									"bundleID=?", new String[] {"com.tencent.mm"});
					return infos;
				}

				@Override
				protected void onPostExecute(List<TrafficInfo> result) {
					// TODO Auto-generated method stub
					adapter.setTrafficInfo(result);
					adapter.notifyDataSetChanged();
					view.setEnabled(true);
					super.onPostExecute(result);
				}

			}.execute();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
