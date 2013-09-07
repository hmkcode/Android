package com.hmkcode.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		// if extending Activity
		//setContentView(R.layout.activity_main);
		
		// 1. pass context and data to the custom adapter
		MyAdapter adapter = new MyAdapter(this, generateData());
		
		// if extending Activity 2. Get ListView from activity_main.xml
		//ListView listView = (ListView) findViewById(R.id.listview);
		
		// 3. setListAdapter
		//listView.setAdapter(adapter); if extending Activity
		setListAdapter(adapter);
	}
	
	
	private ArrayList<Item> generateData(){
		ArrayList<Item> items = new ArrayList<Item>();
	    items.add(new Item("Item 1","First Item on the list"));
	    items.add(new Item("Item 2","Second Item on the list"));
	    items.add(new Item("Item 3","Third Item on the list"));
	    
	    return items;
	}

} 
