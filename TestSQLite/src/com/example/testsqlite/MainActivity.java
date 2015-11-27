package com.example.testsqlite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import com.example.adapters.TrafficAdapter;
import com.example.beans.TrafficInfo;
import com.example.utils.DBUtils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
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
        setContentView(R.layout.activity_main);
        mContext = this;
        //1.获取一个包管理器。
        PackageManager pm = getPackageManager();
        //2.遍历手机操作系统 获取所有的应用程序的uid
        final List<ApplicationInfo> appliactaionInfos = pm.getInstalledApplications(0);
        trafficInfo = new LinkedList<TrafficInfo>();
        /**
         * create一个子线程封装数据，以免UI线程卡顿
         */
        new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				  for(ApplicationInfo applicationInfo : appliactaionInfos){
			        	int uid = applicationInfo.uid;    // 获得软件uid
			        	//proc/uid_stat/10086
			        	//方法返回值 -1 代表的是应用程序没有产生流量 或者操作系统不支持流量统计
			        	long tx = TrafficStats.getUidTxBytes(uid);//发送的 上传的流量byte
			        	long rx = TrafficStats.getUidRxBytes(uid);//下载的流量 byte
			        	
			        	TrafficInfo info = new TrafficInfo();
			        	info.setPhoneNum("15277104415");
			        	info.setNetType("wifi");
			        	info.setWifi_ssid("test");
			        	info.setOperators("中国移动");
			        	info.setData(rx);
			        	info.setTime(System.currentTimeMillis());
			        	info.setBundleID(applicationInfo.packageName);
			        	trafficInfo.add(info);
			        }
			}
        	
        }.start();
      
//        TrafficStats.getMobileTxBytes();//获取手机3g/2g网络上传的总流量
//        TrafficStats.getMobileRxBytes();//手机2g/3g下载的总流量
//
//        TrafficStats.getTotalTxBytes();//手机全部网络接口 包括wifi，3g、2g上传的总流量
//        TrafficStats.getTotalRxBytes();//手机全部网络接口 包括wifi，3g、2g下载的总流量
        
        ListView listView = (ListView)findViewById(R.id.listView_data);
        adapter = new TrafficAdapter(this,new LinkedList<TrafficInfo>());
        listView.setAdapter(adapter);
    }
    
    /**
     * 在xml中，button设置了onclick
     * @param view
     */
    public void DBOperate(final View view){
    	switch (view.getId()) {
		case R.id.button_delete:
			DBUtils.getInstance(mContext).deleteTrafficInfoData(DBUtils.table_trafficInfo, "phoneNum=?", new String[]{"15277104415"});
			break;
			
		case R.id.button_select:
			new AsyncTask<Void, Void, List<TrafficInfo>>(){
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					view.setEnabled(false);
				}

				@Override
				protected List<TrafficInfo> doInBackground(Void... params) {
					// TODO Auto-generated method stub
					List<TrafficInfo> infos = DBUtils.getInstance(mContext).selectTrafficInfoData(DBUtils.table_trafficInfo,null,null,null,null,null,null);
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
			
		case R.id.button_insert:
			DBUtils.getInstance(mContext).insertTrafficInfoData(trafficInfo);
			break;
			
		case R.id.button_update:
			
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
