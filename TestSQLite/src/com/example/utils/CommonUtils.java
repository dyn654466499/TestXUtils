package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
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
	public static long getCurrentDayStartTime(){
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
	public static long getCurrentDayEndTime(){
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
	 * 获取当月的开始时间，格式化后,如 2015-12-1 00:00:00
	 * @return
	 */
	public static long getCurrentMonthStartTime(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		//可以根据需要设置时区
		cal.setTimeZone(TimeZone.getDefault());
		cal.set(Calendar.DAY_OF_MONTH, 1);
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
}
