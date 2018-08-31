package com.hmkcode.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hmkcode.R
import com.hmkcode.adapters.MyAdapter
import com.hmkcode.model.Link
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // layout manager
        val layoutManager:RecyclerView.LayoutManager  = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager;

        // adapter
        val adapter:RecyclerView.Adapter<MyAdapter.MyViewHolder> = MyAdapter(getListData())
        recyclerView.adapter = adapter;
    }

    //generate a list of Link
    private fun getListData(): List<Link> {
        val links = LinkedList<Link>()

        var link = Link()
        link.icon = R.drawable.ic_link;
        link.title ="HMKCODE BLOG";
        link.url = "hmkcode.com";

        links.add(link)

        link = Link()
        link.icon = R.drawable.ic_twitter
        link.title = "@HMKCODE"
        link.url = "twitter.com/hmkcode"

        links.add(link)

        link = Link()
        link.icon = R.drawable.ic_github
        link.title = "HMKCODE"
        link.url = "github.com/hmkcode"

        links.add(link)
        return links
    }
}
