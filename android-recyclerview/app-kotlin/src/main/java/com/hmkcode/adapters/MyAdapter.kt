package com.hmkcode.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hmkcode.R
import com.hmkcode.model.Link

class MyAdapter(private val links:List<Link>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder {

        val itemLayoutView:View =  LayoutInflater.from(parent.context)
                                    .inflate(R.layout.item_layout, null)

        val vh:MyViewHolder = MyViewHolder(itemLayoutView)
        return vh
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemIcon.setImageResource(links[position].icon)
        holder.itemTitle.text = links[position].title
        holder.itemUrl.text = links[position].url
    }

    override fun getItemCount(): Int {
        return links?.size ?: 0
    }

    class MyViewHolder(itemLayoutView: View)
        : RecyclerView.ViewHolder(itemLayoutView) {

        val itemTitle = itemLayoutView.findViewById<TextView>(R.id.item_title)
        val itemUrl = itemLayoutView.findViewById<TextView>(R.id.item_url)
        val itemIcon = itemLayoutView.findViewById<ImageView>(R.id.item_icon)

    }
}