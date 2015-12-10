package com.example.utils;

import static com.example.contants.Const.PREFERENCE_SAVE_PACKAGE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 公共的工具类
 * @author 邓耀宁
 *
 */
public class CommonUtils {

	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String getFormatTime(long time){  
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        Date date=new Date(time); 
        //SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date);
        return format.format(date);  
    }
	
	/** 
     * 格式化流量单位,取小数后两位 (KB不取小数位)
     * @param size 
     * @return 格式化后的数据
     */  
    public static String getFormatTrafficSize(long size) {  
    	double divisor = 1024d;
    	double kiloByte = size / divisor;  
    	
        if (kiloByte < 1) {  
        	return "未使用";
        }  
  
        double megaByte = kiloByte / divisor;  
        if (megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(0, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "KB";  
        }  
  
        double gigaByte = megaByte / divisor;  
        if (gigaByte < 1) {  
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "MB";  
        }  
  
        double teraBytes = gigaByte / divisor;  
        if (teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "GB";  
        }  
        
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()  
                + "TB";  
    }  
    
    /**
     * 读取文件数据  
     * @param fileName
     * @return
     * @throws IOException
     */
    public String readFileData(String fileName) throws IOException{   
      String res="";   
      try{   
    	     File file = new File(fileName);
             FileInputStream fis = new FileInputStream(file);   
             int length = fis.available();   
             byte [] buffer = new byte[length];   
             fis.read(buffer);       
             res = EncodingUtils.getString(buffer, "UTF-8");   
             fis.close();       
         }   
         catch(Exception e){   
             e.printStackTrace();   
         }   
         return res;   
      
    }  
    
    public static void showTips(Context ctx,String message){
    	Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
    
	/**
	 * 合并字符串数组
	 * 
	 * @param a
	 *            数组a
	 * @param b
	 *            数组b
	 * @return 合并后的数组
	 */
	public static String[] mergeArray(String[] a, String[] b) {
		if (a != null && b != null) {
			String[] c = new String[a.length + b.length];
			System.arraycopy(a, 0, c, 0, a.length);
			System.arraycopy(b, 0, c, a.length, b.length);
			return c;
		}
		return null;
	} 
	
	/**
	 * 获取当天的开始时间，格式化后,如 2015-12-2 00:00:00
	 * @return
	 */
	public static long getTodayStartTime(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		//可以根据需要设置时区
		cal.setTimeZone(TimeZone.getDefault());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		long startTime = cal.getTimeInMillis();
		return startTime;
	}
	
	/**
	 * 获取当天的结束时间，格式化后,如 2015-12-2 23:59:59
	 * @return long型数据，可根据情况格式化。
	 */
	public static long getTodayEndTime(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		//可以根据需要设置时区
		cal.setTimeZone(TimeZone.getDefault());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		long endTime = cal.getTimeInMillis();
		return endTime;
	}
	
	/**
	 * 获取昨天的开始时间，格式化后,如 2015-12-2 00:00:00
	 * @return
	 */
	public static long getYesterDayStartTime(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		//可以根据需要设置时区
		cal.setTimeZone(TimeZone.getDefault());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		long startTime = cal.getTimeInMillis();
		return startTime;
	}
	
	/**
	 * 获取昨天的结束时间，格式化后,如 2015-12-2 23:59:59
	 * @return long型数据，可根据情况格式化。
	 */
	public static long getYesterDayEndTime(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		//可以根据需要设置时区
		cal.setTimeZone(TimeZone.getDefault());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		long endTime = cal.getTimeInMillis();
		return endTime;
	}
	
	/**
	 * 获取当月的开始时间，格式化后,如 2015-12-1 00:00:00
	 * @return
	 */
	public static long getCurrentMonthStartTime(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		//可以根据需要设置时区
		cal.setTimeZone(TimeZone.getDefault());
		cal.set(Calendar.DAY_OF_MONTH, 1);//前一天 cal.add(Calendar.DAY_OF_MONTH, -1)，注意是add。
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		long startTime = cal.getTimeInMillis();
		return startTime;
	}
	
	/**
	 * 获取当月的结束时间，格式化后,如 2015-12-31 23:59:59
	 * @return long型数据，可根据情况格式化。
	 */
	public static long getCurrentMonthEndTime(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		//可以根据需要设置时区
		cal.setTimeZone(TimeZone.getDefault());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		long endTime = cal.getTimeInMillis();
		return endTime;
	}
	
	/**
	 * 返回记录所有App packageName(key)和uid(value)的SharedPreferences。
	 * @param context
	 * @return 如果事先没有记录，返回null。
	 */
	public static SharedPreferences getPackageNamePreferences(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				PREFERENCE_SAVE_PACKAGE, Context.MODE_PRIVATE);
		if (preferences.contains(context.getPackageName()))
			return preferences;
		
		return null;
	}
	
	/**
	 * 判断是否有SD卡
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	//==================================                ===============================
	/**
	 * 返回指定包名的应用信息类
	 * @param context 上下文
	 * @param bundleID 指定的包名
	 * @return 返回ApplicationInfo对象，根据需求获取相应的参数；如果不存在则返回null
	 * @category
	 *  获取Drawable图标：info.loadIcon(PackageManager);<br>
	*	获取应用名字：info.loadLabel(PackageManager).toString();<br>
	*	获取进程名：info.processName;<br>
	 */
	public static ApplicationInfo getAppInfo(Context context,String bundleID){
		PackageManager pm = context.getPackageManager();
		ApplicationInfo info = null;
		try {
			info = pm.getApplicationInfo(bundleID, 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return info;
	}
	
	/**
	 * 判断地理位置状态是否打开,注意添加权限android.permission.ACCESS_FINE_LOCATION
	 * @param context 上下文
	 * @return 可用返回true，否则返回false
	 */
	public static boolean isLocationEnable(Context context) {
		// 获取地理位置管理器
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 获取所有可用的位置提供器
		List<String> providers = locationManager.getProviders(true);
		
		String provider = "";
		if(providers.contains(LocationManager.GPS_PROVIDER)){  
            //如果是GPS  
            provider = LocationManager.GPS_PROVIDER;  
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){  
            //如果是Network  
        	provider = LocationManager.NETWORK_PROVIDER;  
        }else{
        	//没有位置提供者，则状态应该是不可用。
            return false;  
        }  
		return true;
	}
	
	/**
	 * 返回经纬度
	 * @param context 上下文
	 * @return 获取经度map.get("latitude")<br>获取纬度map.get("longitude")
	 */
	public static HashMap<String,Double> getLocation(Context context) {
		HashMap<String,Double> map = null;
		// 获取地理位置管理器
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 获取所有可用的位置提供器
		List<String> providers = locationManager.getProviders(true);
		
		String provider = "";
		if(providers.contains(LocationManager.GPS_PROVIDER)){  
            //如果是GPS  
            provider = LocationManager.GPS_PROVIDER;  
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){  
            //如果是Network  
        	provider = LocationManager.NETWORK_PROVIDER;  
        }else{
        	//没有位置提供者，则返回null。
            return map;  
        }  
		map = new HashMap<String, Double>();
		Location location = locationManager.getLastKnownLocation(provider);
		//经度   
		double latitude = location.getLatitude();
		//纬度
		double longitude = location.getLongitude(); 
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		return map;
	}
	
	/**
	 * SIM卡信息：可用否，是否有数据连接，运营商，归属省份，经纬度，设备ID，电话号码；根据需求封装数据；注意添加权限
	 * android.permission.READ_PHONE_STATE；
     * @category
     *   dataActivity表示数据活动状态<br>
         * DATA_ACTIVITY_IN：活动，正在接受数据<br>
         * DATA_ACTIVITY_OUT：活动，正在发送数据<br>
         * DATA_ACTIVITY_INOUT：活动，正在接受和发送数据<br>
         * DATA_ACTIVITY_NONE：活动，但无数据发送和接收<br>
	 * @category
	 * dataState表示数据连接状态<br>
         * DATA_CONNECTED：已连接<br>
         * DATA_CONNECTING ：正在连接<br>
         * DATA_DISCONNECTED：断开<br>
         * DATA_SUSPENDED：暂停<br>
	 * @param context 上下文
	 * @return 获取设备IDmap.get("deviceID")<br>
	 *         获取手机号码map.get("phoneNumber")；手机号码可能为空，如今较为主流的方法是主动发送短信给运营商，通过在运营商返回的短信中获取号码；又或者用户在客户端用手机号码注册时获取。<br>
	 *         获取SIM串号map.get("simSerialNumber")<br>
	 *         获取运营商名称map.get("operatorName")<br>
	 *         获取数据连接状态map.get("dataActivity")<br> 
	 *         获取数据活动状态map.get("dataState")
	 *  
	 */
	public static HashMap<String,String> getSIMCardInfo(Context context){
		HashMap<String,String> map = null;
		TelephonyManager telManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		//设备ID
		String deviceID = telManager.getDeviceId();
		/**
		 * 获取手机号码，但是可能为空；如今较为主流的方法是主动发送短信给运营商，通过在运营商返回的短信中获取号码；又或者用户在客户端用手机号码注册时获取。
		 */
		String phoneNum = telManager.getLine1Number();
		//获得SIM卡的序号 （IMEI） 
		String simSerialNumber = telManager.getSimSerialNumber();
		//运营商名字，如中国移动
		String operatorName = telManager.getNetworkOperatorName();
		//归属省份
		
        /**
         * 获取数据活动状态
         * DATA_ACTIVITY_IN 数据连接状态：活动，正在接受数据
         * DATA_ACTIVITY_OUT 数据连接状态：活动，正在发送数据
         * DATA_ACTIVITY_INOUT 数据连接状态：活动，正在接受和发送数据
         * DATA_ACTIVITY_NONE 数据连接状态：活动，但无数据发送和接收
         */
		//telManager.getDataActivity();
        /**
         * 获取数据连接状态
         * DATA_CONNECTED 数据连接状态：已连接
         * DATA_CONNECTING 数据连接状态：正在连接
         * DATA_DISCONNECTED 数据连接状态：断开
         * DATA_SUSPENDED 数据连接状态：暂停
         */
		//telManager.getDataState();
		map = new HashMap<String, String>();
		map.put("deviceID", deviceID);
		map.put("phoneNumber", phoneNum);
		map.put("simSerialNumber", simSerialNumber);
		map.put("operatorName", operatorName);
		map.put("dataActivity", String.valueOf(telManager.getDataActivity()));
		map.put("dataState", String.valueOf(telManager.getDataState()));
		return map;
	}
}
