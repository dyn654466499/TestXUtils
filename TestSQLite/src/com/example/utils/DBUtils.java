package com.example.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.example.beans.TrafficInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

/**
 * SQLite数据库操作类
 * 
 * @author 邓耀宁
 */
public class DBUtils extends SQLiteOpenHelper {

	/**
	 * 设计为单例，避免多线程并发错误。
	 */
	private static DBUtils dbHelper = null;
	private static final String DBName = "TrafficDoctorDB";
	public static final String TABLE_TRAFFIC_INFO = "TrafficDoctorInfo";
	public static final String TABLE_WIFI_TRAFFIC_INFO = "WifiTrafficInfo";

	public static DBUtils getInstance(Context context) {
		if (dbHelper == null) {
			dbHelper = new DBUtils(context);
		}
		return dbHelper;
	}

	public DBUtils(Context context) {
		super(context, DBName, null, 1);
		Log.i("SQLiteHelper", "SQLiteHelper>>>>>>>>start");
	}

	public DBUtils(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// String CREATE_TrafficInfo_SQL = "CREATE TABLE TrafficDoctorInfo("
		// + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
		// + "WIFI long default(0)," + "GPRS long default(0),"
		// + "time TIMESTAMP default (datetime('now', 'localtime')),"
		// + "packagename varchar(50))";

		String CREATE_TrafficInfo_SQL = "CREATE TABLE " + TABLE_TRAFFIC_INFO
				+ "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "phoneNum varchar(20)," + "imei varchar(20),"
				+ "netType varchar(10)," + "wifi_ssid varchar(30),"
				+ "operators varchar(20),"
				+ "time TIMESTAMP default (datetime('now', 'localtime')),"
				+ "data long," + "bundleID varchar(30))";

		String CREATE_Plans_SQL = "CREATE TABLE Plans("
				+ "company varchar(20)," + "planName varchar(50) primary key,"
				+ "planTraffic int," + "planPrice int)";
		// String DATE_TRIGGER ="";
//		String CREATE_WifiTrafficInfo_SQL = "CREATE TABLE " + TABLE_WIFI_TRAFFIC_INFO
//				+ "bundleID varchar(30) primary key,"
//				+ "wifi_ssid varchar(30),"
//				+ "time TIMESTAMP default (datetime('now', 'localtime'))," 
//				+ "data long)";
		
		db.execSQL(CREATE_TrafficInfo_SQL);
		//db.execSQL(CREATE_WifiTrafficInfo_SQL);

		Log.i("SQLiteHelper", "onCreate>>>>>>>>>>>start");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	// public void insertTrafficGPRS(long traffic, String packagename) {
	// SQLiteDatabase db = getWritableDatabase();
	// String sql = "insert into TrafficDoctorInfo(GPRS,packagename) values("
	// + traffic + ",'" + packagename + "')";
	// db.execSQL(sql);
	// Log.i("SQLiteHelper", "insertTrafficGPRS>>>>>>>>>>>");
	// db.close();
	// }
	//
	// public void insertTrafficWIFI(long traffic, String packagename) {
	// SQLiteDatabase db = getWritableDatabase();
	// String sql = "insert into TrafficDoctorInfo(WIFI,packagename) values("
	// + traffic + ",'" + packagename + "')";
	// db.execSQL(sql);
	// Log.i("SQLiteHelper", "insertTrafficWIFI>>>>>>>>>>>");
	// db.close();
	// }

	/**
	 * 设置相应的参数，可查询单条、多条或所有数据。
	 * 
	 * @param table
	 *            查询的表名
	 * @param columns
	 *            查询的列
	 * @param selection
	 *            查询条件（如"bundleID=?"）
	 * @param selectionArgs
	 *            赋予查询条件的值
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 *            排序
	 * @return
	 */
	public List<TrafficInfo> selectTrafficInfo(String table,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		
		List<TrafficInfo> infos = null;

		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		if (cursor != null) {
			infos = new LinkedList<TrafficInfo>();
			while (cursor.moveToNext()) {
				TrafficInfo info = new TrafficInfo();

				info.setPhoneNum(cursor.getString(1));
				info.setImei(cursor.getString(2));
				info.setNetType(cursor.getString(3));
				info.setWifi_ssid(cursor.getString(4));
				info.setOperators(cursor.getString(5));
				info.setTime(cursor.getLong(6));
				info.setData(cursor.getLong(7));
				info.setBundleID(cursor.getString(8));

				infos.add(info);
			}
		}
		db.close();
		return infos;
	}

	/**
	 * 查询当前一天的数据(默认按时间升序)
	 * @param table 查询的表
	 * @param timeField 表中表示时间的字段，如time，其存储时间的类型为long;如果字段名字为null或空，则返回null；
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public List<TrafficInfo> selectTrafficInfoByToday(String table,String timeField,String selection, String[] selectionArgs,String groupBy, String having, String orderBy){
		if(TextUtils.isEmpty(timeField))return null;
		
		String append = selection!=null?" and "+selection:null;
		
		orderBy = orderBy!=null?orderBy:timeField + " asc";
		
		List<TrafficInfo> infos = selectTrafficInfo(table,
						null, timeField+">=? and "+timeField+"<=?"+append,
				CommonUtils.mergeArray(new String[] {
						String.valueOf(CommonUtils.getTodayStartTime()), String.valueOf(CommonUtils.getTodayEndTime()) },
						selectionArgs), null, null, orderBy);
		return infos;
	}
	
	/**
	 * 查询昨天的数据(默认按时间升序)
	 * @param table 查询的表
	 * @param timeField 表中表示时间的字段，如time，其存储时间的类型为long;如果字段名字为null或空，则返回null；
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public List<TrafficInfo> selectTrafficInfoByYesterday(String table,String timeField,String selection, String[] selectionArgs,String groupBy, String having, String orderBy){
		if(TextUtils.isEmpty(timeField))return null;
		
		String append = selection!=null?" and "+selection:null;
		
		orderBy = orderBy!=null?orderBy:timeField + " asc";
		
		List<TrafficInfo> infos = selectTrafficInfo(table,
						null, timeField+">=? and "+timeField+"<=?"+append,
				CommonUtils.mergeArray(new String[] {
						String.valueOf(CommonUtils.getYesterDayStartTime()), String.valueOf(CommonUtils.getYesterDayEndTime())},
						selectionArgs), null, null, orderBy);
		return infos;
	}
	
	/**
	 * 插入流量信息数据（可批量插入）
	 * 
	 * @param trafficInfos 封装TrafficInfo的列表
	 */
	public void insertWifiTrafficInfo(List<TrafficInfo> trafficInfos) {
		if (trafficInfos == null)
			return;

		SQLiteDatabase db = getWritableDatabase();

		String sql = "insert into "+TABLE_WIFI_TRAFFIC_INFO+"(bundleID,wifi_ssid,time,data) values(?,?,?,?,?,?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);

		db.beginTransaction();
		try {
			for (TrafficInfo info : trafficInfos) {
				stat.bindString(1, info.getPhoneNum());
				stat.bindString(2, info.getImei());
				stat.bindString(3, info.getNetType());
				stat.bindString(4, info.getWifi_ssid());
				stat.bindString(5, info.getOperators());
				stat.bindLong(6, info.getTime());
				stat.bindLong(7, info.getData());
				stat.bindString(8, info.getBundleID());
				stat.executeInsert();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	/**
	 * 插入流量信息数据（可批量插入）
	 * 
	 * @param trafficInfos 封装TrafficInfo的列表
	 */
	public void insertTrafficInfo(List<? extends TrafficInfo> trafficInfos) {
		if (trafficInfos == null)
			return;

		SQLiteDatabase db = getWritableDatabase();

		String sql = "insert into "+TABLE_TRAFFIC_INFO+"(phoneNum,imei,netType,wifi_ssid,operators,time,data,bundleID) values(?,?,?,?,?,?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);

		db.beginTransaction();
		try {
			for (TrafficInfo info : trafficInfos) {
				stat.bindString(1, info.getPhoneNum());
				stat.bindString(2, info.getImei());
				stat.bindString(3, info.getNetType());
				stat.bindString(4, info.getWifi_ssid());
				stat.bindString(5, info.getOperators());
				stat.bindLong(6, info.getTime());
				stat.bindLong(7, info.getData());
				stat.bindString(8, info.getBundleID());
				stat.executeInsert();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
	}
	
	/**
	 * 删除数据（可批量删除）
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 */
	public void deleteTrafficInfo(String table, String whereClause,
			String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			db.delete(table, whereClause, whereArgs);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	/**
	 * 更新数据（可批量更新）
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public void updateTrafficInfo(String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			db.update(table, values, whereClause, whereArgs);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	
	
	
	
	// @SuppressLint("SimpleDateFormat")
	// public ArrayList<AppModel> queryByTime(Context context, String time) {//
	// today,yesterday,samemonth
	// SQLiteDatabase db = getReadableDatabase();
	// String time1;
	// String time2;
	// Date d = new Date();
	// SimpleDateFormat sDateFormat = new SimpleDateFormat(
	// "yyyy-MM-dd 00:00:00");
	//
	// if (time.equals("today")) {
	// time1 = sDateFormat.format(d.getTime());
	//
	// time2 = sDateFormat.format(new Date(d.getTime() + 24 * 60 * 60
	// * 1000));
	// } else if (time.equals("yesterday")) {
	// time1 = sDateFormat.format(new Date(d.getTime() - 24 * 60 * 60
	// * 1000));
	//
	// time2 = sDateFormat.format(d.getTime());
	// } else {
	// Calendar a = Calendar.getInstance();
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	// a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
	//
	// time1 = df.format(a.getTime());
	// a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
	//
	// time2 = df.format(a.getTime());
	// }
	//
	// String sql =
	// "select sum(WIFI),sum(GPRS),packagename from TrafficDoctorInfo where time>='"
	// + time1 + "' and time<'" + time2 + "' Group by packagename";
	//
	// ArrayList<AppModel> appList = new ArrayList<AppModel>();
	//
	// PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
	//
	// Cursor cursor = db.rawQuery(sql, null);
	//
	// while (cursor.moveToNext()) {
	// long WIFILong = cursor.getLong(0);
	// long GPRSLong = cursor.getLong(1);
	// String PackNameString = cursor.getString(2);
	// Log.i("SQLiteHelper", GPRSLong + "," + WIFILong + PackNameString);
	//
	// AppModel temp = new AppModel();
	// try {
	// Drawable icon = pm.getApplicationIcon(PackNameString);
	// String name = pm.getApplicationLabel(
	// pm.getApplicationInfo(PackNameString, 0)).toString();
	// temp.setAppicon(icon);
	// temp.setAppName(name);
	// } catch (NameNotFoundException e) {
	// e.printStackTrace();
	// }
	// temp.setGPRS(GPRSLong);
	// temp.setWIFI(WIFILong);
	// appList.add(temp);
	// }
	//
	// Collections.sort(appList, new Comparator<AppModel>() {
	// @Override
	// public int compare(AppModel info0, AppModel info1) {
	// if (info0.getGPRS() != info1.getGPRS()) {
	// return (int) (info1.getGPRS() - info0.getGPRS());
	// } else {
	// return (int) (info1.getWIFI() - info0.getWIFI());
	// }
	// }
	// });
	// db.close();
	// return appList;
	// }
}
