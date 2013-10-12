package com.hmkcode.android;

import java.util.ArrayList;

import com.hmkcode.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Item> {

		private final Context context;
		private final ArrayList<Item> itemsArrayList;

		public MyAdapter(Context context, ArrayList<Item> itemsArrayList) {
			 
			super(context, R.layout.target_item, itemsArrayList);
			
			this.context = context;
			this.itemsArrayList = itemsArrayList;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    
			// 1. Create inflater 
			LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    
			// 2. Get rowView from inflater
			
			View rowView = null;
			if(!itemsArrayList.get(position).isGroupHeader()){
				rowView = inflater.inflate(R.layout.target_item, parent, false);
			
				// 3. Get icon,title & counter views from the rowView
				ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon); 
				TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
				TextView counterView = (TextView) rowView.findViewById(R.id.item_counter);
		    
			    // 4. Set the text for textView 
			    imgView.setImageResource(itemsArrayList.get(position).getIcon());
			    titleView.setText(itemsArrayList.get(position).getTitle());
			    counterView.setText(itemsArrayList.get(position).getCounter());
			}
			else{
					rowView = inflater.inflate(R.layout.group_header_item, parent, false);
					TextView titleView = (TextView) rowView.findViewById(R.id.header);
				    titleView.setText(itemsArrayList.get(position).getTitle());

			}
		   
		    // 5. retrn rowView
		    return rowView;
		}
}
