package com.hmkcode.android.recyclerview;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        
        
        ItemData itemsData[] = { new ItemData("Help",R.drawable.help),
        						 new ItemData("Delete",R.drawable.content_discard),
        						 new ItemData("Cloud",R.drawable.collections_cloud),
        						new ItemData("Favorite",R.drawable.rating_favorite),
        						new ItemData("Like",R.drawable.rating_good),
        						new ItemData("Rating",R.drawable.rating_important)};
       
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        MyAdapter mAdapter = new MyAdapter(itemsData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
