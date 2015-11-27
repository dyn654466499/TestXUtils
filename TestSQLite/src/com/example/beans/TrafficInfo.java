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
	private String bundleID;
	
	//插入时间
	private long time;
	
	//手机号
	private String phoneNum = tm.getLine1Number();
	
	//手机IMEI
	private String imei = tm.getDeviceId();
	
	//网络类型
	private String netType;
	
	//数据量的字节数
	private long data;
	
	//wifi的ssid
	private String wifi_ssid;
	
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
	//运营商标识
	private String operators;
	
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
