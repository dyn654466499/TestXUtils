package com.example.beans;

import android.graphics.drawable.Drawable;

/**
 * 使用bean操作traffic information
 * @author 邓耀宁
 *
 */
public class TrafficStateInfo {
    
	//使用流量的app对应的包名
	private String bundleID;
	
	//数据量的字节数
	private long rx;
	
	//数据量的字节数
	private long tx;
	
	//wifi的ssid
	private String wifi_ssid;
	
	private Drawable app_icon;
	
	private String app_name;

	public Drawable getApp_icon() {
		return app_icon;
	}
	public void setApp_icon(Drawable app_icon) {
		this.app_icon = app_icon;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	/**
	 * ----------------------------    getter和setter          ------------------------------
	 */
	
	public String getWifi_ssid() {
		return wifi_ssid;
	}
	public void setWifi_ssid(String wifi_ssid) {
		this.wifi_ssid = wifi_ssid;
	}
	
	public long getRx() {
		return rx;
	}
	public void setRx(long rx) {
		this.rx = rx;
	}
	public long getTx() {
		return tx;
	}
	public void setTx(long tx) {
		this.tx = tx;
	}
	public String getBundleID() {
		return bundleID;
	}
	public void setBundleID(String bundleID) {
		this.bundleID = bundleID;
	}
	
}
