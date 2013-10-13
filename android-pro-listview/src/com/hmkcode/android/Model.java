package com.hmkcode.android;

public class Model {

	private int icon;
	private String title;
	private String counter;
	
	private boolean isGroupHeader = false;
	
	public Model(String title) {
		this(-1,title,null);
		isGroupHeader = true;
	}
	public Model(int icon, String title, String counter) {
		super();
		this.icon = icon;
		this.title = title;
		this.counter = counter;
	}
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
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}
	public boolean isGroupHeader() {
		return isGroupHeader;
	}
	public void setGroupHeader(boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}
	
	
	
}
