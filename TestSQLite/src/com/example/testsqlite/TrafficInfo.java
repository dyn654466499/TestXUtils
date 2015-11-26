package com.example.testsqlite;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 使用bean操作traffic information
 * @author 邓耀宁
 *
 */
public class TrafficInfo {
    
	//private int id;
	private TelephonyManager tm = (TelephonyManager)TrafficManager.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
	
	//使用流量的app对应的包名
	private String bundleID;
	
	//
	private long time;
	
	//手机号
	private String phoneNum = tm.getLine1Number();
	
	//手机IMEI
	private String imei = tm.getDeviceId();
	
	//网络类型
	private String netType;
	
	//数据量的字节数
	private long data;
	
	public long getData() {
		return data;
	}
	public void setData(long data) {
		this.data = data;
	}
	public String getBundleID() {
		return bundleID;
	}
	public void setBundleID(String bundleID) {
		this.bundleID = bundleID;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getNetType() {
		return netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	
}
