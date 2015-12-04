package com.example.beans;

public class AppTrafficDayInfo extends TrafficInfo {
	
	private long gprs = 0;
	private long wifi = 0;
	
	public long getGprs() {
		return gprs;
	}
	public void setGprs(long gprs) {
		this.gprs = gprs;
	}
	public long getWifi() {
		return wifi;
	}
	public void setWifi(long wifi) {
		this.wifi = wifi;
	}
}
