package com.hmkcode.android.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class GcmMessageHandler extends IntentService {

     String mes;
     private Handler handler;
	public GcmMessageHandler() {
		super("GcmMessageHandler");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
	}
	@Override
	protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

       mes = extras.getString("title");
       showToast();
       Log.i("GCM", "Received : (" +messageType+")  "+extras.getString("title"));
       
              
        
        GcmBroadcastReceiver.completeWakefulIntent(intent);

	}
	
	public void showToast(){
		handler.post(new Runnable() {
		    public void run() {
		        Toast.makeText(getApplicationContext(),mes , Toast.LENGTH_LONG).show();
		    }
		 });

	}
	
	

}
