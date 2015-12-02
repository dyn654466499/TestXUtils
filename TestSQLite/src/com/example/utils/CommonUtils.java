package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
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
}
