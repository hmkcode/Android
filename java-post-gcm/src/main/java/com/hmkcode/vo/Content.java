package com.hmkcode.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Content implements Serializable {

	
	
	private List<String> registration_ids;
	private Map<String,String> data;
	
	
	public void addRegId(String regId){
		if(registration_ids == null)
			registration_ids = new LinkedList<String>();
		registration_ids.add(regId);
	}
	
	public void createData(String title, String message){
		if(data == null)
			data = new HashMap<String,String>();
	
		data.put("title", title);
		data.put("message", message);
	}
	
	
	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
