package com.example.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beans.TrafficInfo;
import com.example.beans.TrafficStateInfo;
import com.example.testsqlite.R;
import com.example.utils.CommonUtils;

public class TrafficStateAdapter extends BaseAdapter{
    private Context context;
    private List<TrafficStateInfo> trafficInfo;
	
	public TrafficStateAdapter(Context context, List<TrafficStateInfo> trafficInfo) {
		super();
		this.context = context;
		this.trafficInfo = trafficInfo;
	}
	
	public void setTrafficInfo(List<TrafficStateInfo> trafficInfo) {
		this.trafficInfo = trafficInfo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trafficInfo.size();
	}

	@Override
	public TrafficStateInfo getItem(int arg0) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_trafficstate_item, viewGroup, false);
			holder.imageView_app_icon = (ImageView)convertView.findViewById(R.id.imageView_app_icon);
			holder.textView_trafficTX = (TextView)convertView.findViewById(R.id.textView_trafficTX);
			holder.textView_trafficRX = (TextView)convertView.findViewById(R.id.textView_trafficRX);
			holder.textView_app_name = (TextView)convertView.findViewById(R.id.textView_app_name);
			holder.textView_trafficStat = (TextView)convertView.findViewById(R.id.textView_trafficStat);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView_app_icon.setImageDrawable(trafficInfo.get(position).getApp_icon());
		holder.textView_app_name.setText(trafficInfo.get(position).getApp_name());
		
		long tx = trafficInfo.get(position).getTx();
		holder.textView_trafficTX.setText(CommonUtils.getFormatTrafficSize(tx));
		long rx = trafficInfo.get(position).getRx();
		holder.textView_trafficRX.setText(CommonUtils.getFormatTrafficSize(rx));
		holder.textView_trafficStat.setText(CommonUtils.getFormatTrafficSize(tx+rx));
		return convertView;
	}

	static class ViewHolder{
		private ImageView imageView_app_icon;
		private TextView textView_trafficTX;
		private TextView textView_trafficRX;
		private TextView textView_app_name;
		private TextView textView_trafficStat;
	}
}
