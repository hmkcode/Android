package com.material.practice.materialnavigationdrawer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NavListAdapter extends ArrayAdapter<NavItems> {

    private static Context context;
    private static NavListAdapter mInstance;
    private ArrayList<NavItems> items;

    public NavListAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    public static NavListAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NavListAdapter(context);
        }
        return mInstance;
    }

    public void setItems(ArrayList<NavItems> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.navigation_drawer_item_layout, null, false);
        }

        TextView icon = (TextView) view.findViewById(R.id.itemIcon);
        TextView title = (TextView) view.findViewById(R.id.listItem);

        Typeface robotoMedium = FontManager.getInstance(context).getTypeFace(FontManager.FONT_ROBOTO);
        Typeface materialIcon = FontManager.getInstance(context).getTypeFace(FontManager.FONT_MATERIAL);

        icon.setText(items.get(position).getIcon());
        title.setText(items.get(position).getTitle());

        icon.setTypeface(materialIcon);
        title.setTypeface(robotoMedium);

        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
