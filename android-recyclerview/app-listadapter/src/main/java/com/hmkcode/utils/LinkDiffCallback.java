package com.hmkcode.utils;

import android.support.v7.util.DiffUtil;
import com.hmkcode.models.Link;

public class LinkDiffCallback extends DiffUtil.ItemCallback<Link> {
    @Override
    public boolean areItemsTheSame(Link oldItem, Link newItem) {
        return oldItem.getUrl().equals(newItem.getUrl());
    }

    @Override
    public boolean areContentsTheSame(Link oldItem, Link newItem) {
        return oldItem.getUrl().equals(newItem.getUrl());
    }
}
