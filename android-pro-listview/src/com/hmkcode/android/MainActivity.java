package com.hmkcode.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
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
	
	
	private ArrayList<Model> generateData(){
		ArrayList<Model> models = new ArrayList<Model>();
	    models.add(new Model("Group Title"));
	    models.add(new Model(R.drawable.action_help_32,"Menu Item 1","1"));
	    models.add(new Model(R.drawable.action_search_32,"Menu Item 2","2"));
	    models.add(new Model(R.drawable.collections_cloud_32,"Menu Item 3","12"));
	    
	    return models;
	}

} 
