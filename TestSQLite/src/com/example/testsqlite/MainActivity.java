package com.example.testsqlite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("", "start");
        try {
			Log.e("", readFileData("/data/anr/traces.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
