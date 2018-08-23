package com.hmkcode.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.hmkcode.R;
import com.hmkcode.adapters.MyAdapter;
import com.hmkcode.model.Link;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        // layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // adapter
        RecyclerView.Adapter adapter = new MyAdapter(getListData());
        recyclerView.setAdapter(adapter);

    }

    //generate a list of Link
    private static List<Link> getListData(){
        List<Link> links = new LinkedList<Link>();

        Link link = new Link();
        link.setIcon(R.drawable.ic_link);
        link.setTitle("HMKCODE BLOG");
        link.setUrl("hmkcode.com");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.ic_twitter);
        link.setTitle("@HMKCODE");
        link.setUrl("twitter.com/hmkcode");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.ic_github);
        link.setTitle("HMKCODE");
        link.setUrl("github.com/hmkcode");

        links.add(link);
        return links;
    }
}
