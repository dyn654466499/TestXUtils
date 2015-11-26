package com.example.testsqlite;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * SQLite数据库操作类
 * 
 * @author 邓耀宁
 */
public class DBHelper extends SQLiteOpenHelper {

	/**
	 * 设计为单例，避免多线程并发错误。
	 */
	private static DBHelper dbHelper = null;

	public static DBHelper getInstance(Context context) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(context);
		}
		return dbHelper;
	}

	public DBHelper(Context context) {
		super(context, "TrafficDoctorDB", null, 1);
		Log.i("SQLiteHelper", "SQLiteHelper>>>>>>>>start");
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		String CREATE_TrafficInfo_SQL = "CREATE TABLE TrafficDoctorInfo("
//				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
//				+ "WIFI long default(0)," + "GPRS long default(0),"
//				+ "time TIMESTAMP default (datetime('now', 'localtime')),"
//				+ "packagename varchar(50))";

		String CREATE_TrafficInfo_SQL = "CREATE TABLE TrafficDoctorInfo("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "phoneNum varchar(20)," 
				+ "imei varchar(20),"
				+ "time TIMESTAMP default (datetime('now', 'localtime')),"
				+ "data long,"
				+ "bundleID varchar(30))";
		
		String CREATE_Plans_SQL = "CREATE TABLE Plans("
				+ "company varchar(20)," + "planName varchar(50) primary key,"
				+ "planTraffic int," + "planPrice int)";
		// String DATE_TRIGGER ="";

		db.execSQL(CREATE_TrafficInfo_SQL);
		db.execSQL(CREATE_Plans_SQL);

		Log.i("SQLiteHelper", "onCreate>>>>>>>>>>>start");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

//	public void insertTrafficGPRS(long traffic, String packagename) {
//		SQLiteDatabase db = getWritableDatabase();
//		String sql = "insert into TrafficDoctorInfo(GPRS,packagename) values("
//				+ traffic + ",'" + packagename + "')";
//		db.execSQL(sql);
//		Log.i("SQLiteHelper", "insertTrafficGPRS>>>>>>>>>>>");
//		db.close();
//	}
//
//	public void insertTrafficWIFI(long traffic, String packagename) {
//		SQLiteDatabase db = getWritableDatabase();
//		String sql = "insert into TrafficDoctorInfo(WIFI,packagename) values("
//				+ traffic + ",'" + packagename + "')";
//		db.execSQL(sql);
//		Log.i("SQLiteHelper", "insertTrafficWIFI>>>>>>>>>>>");
//		db.close();
//	}

	/**
	 * 批量插入
	 * @param trafficInfo
	 */
	public void batchInsertTraffic(List<TrafficInfo> trafficInfo) {
		if(trafficInfo == null)return;
		
		SQLiteDatabase db = getWritableDatabase();
		
		String sql = "insert into TrafficDoctorInfo(phoneNum,imei,time,data,bundleID) values(?,?,?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);
		
		db.beginTransaction();
		try {
			for (TrafficInfo info : trafficInfo) {
				stat.bindString(1, info.getPhoneNum());
				stat.bindString(2, info.getImei());
				stat.bindLong(3, info.getTime());
				stat.bindLong(4, info.getData());
				stat.bindString(5, info.getBundleID());
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
	 * 高效地批量删除
	 * @param trafficInfo
	 */
	public void batchDeleteTraffic(List<TrafficInfo> trafficInfo) {
		if(trafficInfo == null)return;
		
		SQLiteDatabase db = getWritableDatabase();
		
		String sql = "delete from TrafficDoctorInfo where phoneNum = ?";
		SQLiteStatement stat = db.compileStatement(sql);
		
		db.beginTransaction();
		try {
			for (TrafficInfo info : trafficInfo) {
				stat.bindString(1, info.getPhoneNum());
				stat.executeUpdateDelete();
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
	}
	
	public void batchUpdateTraffic(List<TrafficInfo> trafficInfo) {
		if(trafficInfo == null)return;
		
		SQLiteDatabase db = getWritableDatabase();
		
		String sql = "insert into TrafficDoctorInfo(phoneNum,imei,time,data,bundleID) values(?,?,?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);
		
		db.beginTransaction();
		try {
			for (TrafficInfo info : trafficInfo) {
				stat.bindString(1, info.getPhoneNum());
				stat.bindString(2, info.getImei());
				stat.bindLong(3, info.getTime());
				stat.bindLong(4, info.getData());
				stat.bindString(5, info.getBundleID());
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
