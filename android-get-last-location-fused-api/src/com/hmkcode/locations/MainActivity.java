package com.hmkcode.locations;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements 
	ConnectionCallbacks, OnConnectionFailedListener {

	GoogleApiClient mGoogleApiClient;
	Location mLastLocation;
	TextView tvLatlong;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvLatlong = (TextView) findViewById(R.id.tvLatlong);
		
		buildGoogleApiClient();
		
		if(mGoogleApiClient!= null){
			mGoogleApiClient.connect();
		}
		else
			Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();

			
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
		
		if (mLastLocation != null) {
			tvLatlong.setText("Latitude: "+ String.valueOf(mLastLocation.getLatitude())+" - Longitude: "+
					String.valueOf(mLastLocation.getLongitude()));
        }

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();
		
	}
	
	protected synchronized void buildGoogleApiClient() {
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(LocationServices.API)
	        .build();
	}
}
