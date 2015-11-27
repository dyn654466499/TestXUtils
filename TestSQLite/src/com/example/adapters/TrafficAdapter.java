package com.example.adapters;

import java.util.List;

import com.example.beans.TrafficInfo;
import com.example.testsqlite.R;
import com.example.testsqlite.R.id;
import com.example.testsqlite.R.layout;
import com.example.utils.CommonUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TrafficAdapter extends BaseAdapter{
    private Context context;
    private List<TrafficInfo> trafficInfo;
	
	public TrafficAdapter(Context context, List<TrafficInfo> trafficInfo) {
		super();
		this.context = context;
		this.trafficInfo = trafficInfo;
	}
	
	public void setTrafficInfo(List<TrafficInfo> trafficInfo) {
		this.trafficInfo = trafficInfo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trafficInfo.size();
	}

	@Override
	public TrafficInfo getItem(int arg0) {
		// TODO Auto-generated method stub
		return trafficInfo.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, viewGroup, false);
			holder.textView_phoneNum = (TextView)convertView.findViewById(R.id.textView_phoneNum);
//			holder.textView_imei = (TextView)convertView.findViewById(R.id.textView_imei);
//			holder.textView_time = (TextView)convertView.findViewById(R.id.textView_time);
//			holder.textView_data = (TextView)convertView.findViewById(R.id.textView_data);
//			holder.textView_bundleID = (TextView)convertView.findViewById(R.id.textView_bundleID);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		holder.textView_phoneNum.setText(trafficInfo.get(position).getPhoneNum()+"\n"
				+trafficInfo.get(position).getImei()+"\n"
				+trafficInfo.get(position).getNetType()+"\n"
				+trafficInfo.get(position).getWifi_ssid()+"\n"
				+trafficInfo.get(position).getOperators()+"\n"
				+CommonUtils.getFormatTime(trafficInfo.get(position).getTime())+"\n"
				+CommonUtils.getFormatTrafficSize(trafficInfo.get(position).getData())+"\n"
				+trafficInfo.get(position).getBundleID()
				);
//		holder.textView_imei.setText(trafficInfo.get(position).getImei());
//		holder.textView_time.setText(String.valueOf(trafficInfo.get(position).getTime()));
//		holder.textView_data.setText(String.valueOf(trafficInfo.get(position).getData()));
//		holder.textView_bundleID.setText(trafficInfo.get(position).getBundleID());
		return convertView;
	}

	static class ViewHolder{
		//private TextView textView_id;
		private TextView textView_phoneNum;
//		private TextView textView_imei;
//		private TextView textView_time;
//		private TextView textView_data;
//		private TextView textView_bundleID;
		
	}
}
