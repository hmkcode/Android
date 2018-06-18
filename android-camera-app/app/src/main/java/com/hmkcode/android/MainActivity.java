package com.hmkcode.android;

import java.io.File;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	Button btnTackPic;
	TextView tvHasCamera, tvHasCameraApp;
	ImageView ivThumbnailPhoto;
	Bitmap bitMap;
	static int TAKE_PICTURE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		// Get reference to views
		tvHasCamera = (TextView) findViewById(R.id.tvHasCamera);
		tvHasCameraApp = (TextView) findViewById(R.id.tvHasCameraApp);
		btnTackPic = (Button) findViewById(R.id.btnTakePic);
		ivThumbnailPhoto = (ImageView) findViewById(R.id.ivThumbnailPhoto);

		// Does your device have a camera?
		if(hasCamera()){
			tvHasCamera.setBackgroundColor(0xFF00CC00);
			tvHasCamera.setText("You have Camera");
		}
		
		// Do you have Camera Apps?
		if(hasDefualtCameraApp(MediaStore.ACTION_IMAGE_CAPTURE)){
			tvHasCameraApp.setBackgroundColor(0xFF00CC00);
			tvHasCameraApp.setText("You have Camera Apps");
		}
		
		// add onclick listener to the button
		btnTackPic.setOnClickListener(this);
		
	}
	
	// on button "btnTackPic" is clicked
	@Override
	public void onClick(View view) {
		
		// create intent with ACTION_IMAGE_CAPTURE action 
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		// to save picture remove comment
		/*File file = new File(Environment.getExternalStorageDirectory(),
		"my-photo.jpg");
		Uri photoPath = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath); */
		
		// start camera activity
	    startActivityForResult(intent, TAKE_PICTURE);

	}
	
	// The Android Camera application encodes the photo in the return Intent delivered to onActivityResult() 
	// as a small Bitmap in the extras, under the key "data"
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		if (requestCode == TAKE_PICTURE && resultCode== RESULT_OK && intent != null){
			// get bundle
			Bundle extras = intent.getExtras();
			
			// get 
			bitMap = (Bitmap) extras.get("data");
			ivThumbnailPhoto.setImageBitmap(bitMap);
			
		}
	}

	// method to check you have a Camera
	private boolean hasCamera(){
		return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}
	
	// method to check you have Camera Apps
	private boolean hasDefualtCameraApp(String action){
		final PackageManager packageManager = getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    
	    return list.size() > 0;

	}
}
