package com.hmkcode.http;

import org.apache.http.client.methods.HttpUriRequest;

import com.hmkcode.http.AsyncHttpTask;

public abstract class HttpHandler {

	public abstract HttpUriRequest getHttpRequestMethod();
	
	public abstract void onResponse(String result);
	
	public void execute(){
		new AsyncHttpTask(this).execute();
	}

	
}
