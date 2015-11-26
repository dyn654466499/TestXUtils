package com.example.testsqlite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ButtonListener listener = new ButtonListener();
        Button button_select = (Button)findViewById(R.id.button_select);
        Button button_insert = (Button)findViewById(R.id.button_insert);
        Button button_delete = (Button)findViewById(R.id.button_delete);
        Button button_update = (Button)findViewById(R.id.button_update);
        
        button_select.setOnClickListener(listener);
        button_insert.setOnClickListener(listener);
        button_delete.setOnClickListener(listener);
        button_update.setOnClickListener(listener);
        
        ListView listView = (ListView)findViewById(R.id.listView_data);
        LinkedList<TrafficInfo> trafficInfo = new LinkedList<TrafficInfo>();
        
        for (int i = 0; i < 10; i++) {
        	TrafficInfo info = new TrafficInfo();
        	info.setNetType("wifi");
        	info.setData(2000l);
        	info.setTime(System.currentTimeMillis());
        	info.setBundleID("com.example.testsqlite");
        	trafficInfo.add(info);
		}
        listView.setAdapter(new TrafficAdapter(this,trafficInfo));
        
//        Log.e("", "start");
//        try {
//			Log.e("", readFileData("/data/anr/traces.txt"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.button_delete:
				
				break;
			case R.id.button_select:
				
				break;
			case R.id.button_insert:
				
				break;
			case R.id.button_update:
				
				break;
				
			default:
				break;
			}
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
