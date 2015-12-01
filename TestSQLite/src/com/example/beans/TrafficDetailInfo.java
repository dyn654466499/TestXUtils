package com.example.beans;

import android.graphics.drawable.Drawable;

/**
 * 使用bean操作traffic information
 * 
 * @author 邓耀宁
 * 
 */
public class TrafficDetailInfo extends TrafficInfo {

	// 数据量的字节数
	private long rx;

	// 数据量的字节数
	private long tx;

	private Drawable app_icon;

	private String app_name;

	/**
	 * ---------------------------- getter和setter ------------------------------
	 */
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
}
