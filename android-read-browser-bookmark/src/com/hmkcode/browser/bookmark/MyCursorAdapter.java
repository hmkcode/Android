package com.hmkcode.browser.bookmark;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;
    
    // Default constructor
    public MyCursorAdapter(Context context, Cursor cursor, int flags) {
    	super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                      Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		TextView tvDate = (TextView) view.findViewById(R.id.date);
		TextView tvTitle = (TextView) view.findViewById(R.id.title);
		TextView tvBoV = (TextView) view.findViewById(R.id.bov);
		TextView tvURL = (TextView) view.findViewById(R.id.url);
		ImageView ivFavicon = (ImageView) view.findViewById(R.id.favicon);
		
	 //  long date = cursor.getLong(Browser.SEARCHES_PROJECTION_DATE_INDEX);
	 //  String title = cursor.getString(Browser.SEARCHES_PROJECTION_SEARCH_INDEX);
		
		long date = cursor.getLong(Browser.HISTORY_PROJECTION_DATE_INDEX);
		String title = cursor.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX);
	   String url = cursor.getString(Browser.HISTORY_PROJECTION_URL_INDEX);
	   byte[] icon = cursor.getBlob(Browser.HISTORY_PROJECTION_FAVICON_INDEX);
	   String bokmrk = cursor.getString(Browser.HISTORY_PROJECTION_BOOKMARK_INDEX);
	   String visits = cursor.getString(Browser.HISTORY_PROJECTION_VISITS_INDEX);
	   String id = cursor.getString(Browser.HISTORY_PROJECTION_ID_INDEX);
	    
	 //  String names[] = cursor.getColumnNames();

	   
	   tvDate.setText(new Date(date).toString());
	   tvBoV.setText(bokmrk.equals("1")?"Bookmarked":"Visited");
	   tvTitle.setText(title);
	   tvURL.setText(url);
	   if(icon != null){
		   Bitmap bm = BitmapFactory.decodeByteArray(icon, 0, icon.length);
		   ivFavicon.setImageBitmap(bm);
	   }

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return cursorInflater.inflate(R.layout.link, parent, false);
	}

   
}