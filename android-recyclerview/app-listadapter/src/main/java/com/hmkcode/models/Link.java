package com.hmkcode.models;

public class Link {

    private int icon;
    private String title;
    private String url;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getUrl().equals(((Link)obj).getUrl());
    }

    @Override
    public String toString() {
        return "Link{" +
                "icon=" + icon +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
