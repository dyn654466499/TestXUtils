package com.example.beans;

import com.example.testsqlite.TrafficManager;

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
	protected String bundleID="";
	
	//插入时间
	protected long time=0;
	
	//手机号
	protected String phoneNum = tm.getLine1Number();
	
	//手机IMEI
	protected String imei = tm.getDeviceId();
	
	//网络类型
	protected String netType="";
	
	//数据量的字节数
	protected long data=0;
	
	//wifi的ssid
	protected String wifi_ssid="";
	
	//运营商标识
	protected String operators="";
	
	/**
	 * ----------------------------    getter和setter          ------------------------------
	 */
	
	public String getWifi_ssid() {
		return wifi_ssid;
	}
	public void setWifi_ssid(String wifi_ssid) {
		this.wifi_ssid = wifi_ssid;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	
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
