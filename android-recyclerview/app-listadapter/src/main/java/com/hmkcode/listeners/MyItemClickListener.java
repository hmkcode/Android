package com.hmkcode.listeners;

import com.hmkcode.models.Link;

@FunctionalInterface
public interface MyItemClickListener {
    public void onClick(Link link);
}
