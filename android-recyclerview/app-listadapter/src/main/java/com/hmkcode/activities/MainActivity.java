package com.hmkcode.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.hmkcode.R;
import com.hmkcode.adapters.MyListAdapter;
import com.hmkcode.models.Link;
import com.hmkcode.utils.LinkDiffCallback;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Link> links;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        // layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setClickable(true);

        // list adapter
        links = getListData();

        listAdapter = new MyListAdapter(new LinkDiffCallback(),
                link -> updateList(link));
        recyclerView.setAdapter(listAdapter);

        listAdapter.submitList(getListData());

    }

    private void updateList(Link link){
        boolean removed = links.remove(link);
        listAdapter.submitList(new LinkedList(links));
    }

    //generate a list of Link
    private List<Link> getListData(){
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
