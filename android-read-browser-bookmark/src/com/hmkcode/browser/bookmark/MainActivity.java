package com.hmkcode.browser.bookmark;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;

import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		getBrowserHist();
	}
  
	public void getBrowserHist()  {

        Cursor cursor = getContentResolver().query(Browser.BOOKMARKS_URI, null, null, null, null);
        
       // Cursor cursor = getContentResolver().query(Browser.SEARCHES_URI, null, null, null, null);
        	 
        MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this,cursor,0);
		 listView.setAdapter(myCursorAdapter);      
      }
}
