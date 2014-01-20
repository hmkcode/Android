package com.hmkcode;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.hmkcode.http.HttpHandler;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnRequest;
	private EditText etResponse;
	private TextView tvIsConnected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
		btnRequest = (Button) findViewById(R.id.btnRequest);
		etResponse = (EditText) findViewById(R.id.etRespose);
		
		if(isConnected()){
			tvIsConnected.setBackgroundColor(0xFF00CC00);
			tvIsConnected.setText("You are conncted");
        }
		else{
			tvIsConnected.setText("You are NOT conncted");
		}
		
		btnRequest.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
		new HttpHandler() {
			@Override
			public HttpUriRequest getHttpRequestMethod() {

				return new HttpGet("http://hmkcode.com/examples/index.php");
				
				// return new HttpPost(url)
			}
			@Override
			public void onResponse(String result) {
				Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
				etResponse.setText(result);
			}
			
		}.execute();
	}
	public boolean isConnected(){
    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
    	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    	    if (networkInfo != null && networkInfo.isConnected()) 
    	    	return true;
    	    else
    	    	return false;	
    }

	

}
