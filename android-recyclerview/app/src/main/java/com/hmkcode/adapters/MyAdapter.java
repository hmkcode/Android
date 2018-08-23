package com.hmkcode.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmkcode.R;
import com.hmkcode.model.Link;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Link> links;

    // constructor
    public MyAdapter(List<Link> links){
        this.links = links;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflate item_layout
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);

        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemIcon.setImageResource(links.get(position).getIcon());
        holder.itemTitle.setText(links.get(position).getTitle());
        holder.itemUrl.setText(links.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        if(links != null)
            return links.size();
        else
            return 0;
    }

    // inner static class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitle;
        public TextView itemUrl;
        public ImageView itemIcon;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemTitle = itemLayoutView.findViewById(R.id.item_title);
            itemUrl = itemLayoutView.findViewById(R.id.item_url);
            itemIcon = itemLayoutView.findViewById(R.id.item_icon);
        }
    }
}
