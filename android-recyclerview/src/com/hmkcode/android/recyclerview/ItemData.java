package com.hmkcode.android.recyclerview;

public class ItemData {


	private String title;
	private int imageUrl;
	
	public ItemData(String title,int imageUrl){
		
		this.title = title;
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(int imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
