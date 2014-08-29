package com.hmkcode.android.image;


import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	TextView txtSDK;
	Button btnSelectImage;
	TextView txtUriPath,txtRealPath;
	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// get reference to views
		txtSDK = (TextView) findViewById(R.id.txtSDK);
		btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
		txtUriPath = (TextView) findViewById(R.id.txtUriPath);
		txtRealPath = (TextView) findViewById(R.id.txtRealPath);
		imageView = (ImageView) findViewById(R.id.imgView);
		
		// add click listener to button
		btnSelectImage.setOnClickListener(this);
		
		
	}
	
	@Override
	public void onClick(View view) {
		
		// 1. on Upload click call ACTION_GET_CONTENT intent
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// 2. pick image only
        intent.setType("image/*");
        // 3. start activity
        startActivityForResult(intent, 0);
		
        // define onActivityResult to do something with picked image 
	}
	
	
	@Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
		if(resCode == Activity.RESULT_OK && data != null){
			String realPath;
			// SDK < API11
			if (Build.VERSION.SDK_INT < 11)
				realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
			
			// SDK >= 11 && SDK < 19
			else if (Build.VERSION.SDK_INT < 19)
				realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
			
			// SDK > 19 (Android 4.4)
			else
	            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
				
			
			setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(),realPath);
		}
    }
	
	private void setTextViews(int sdk, String uriPath,String realPath){
		
		this.txtSDK.setText("Build.VERSION.SDK_INT: "+sdk);
		this.txtUriPath.setText("URI Path: "+uriPath);
		this.txtRealPath.setText("Real Path: "+realPath);
		
		Uri uriFromPath = Uri.fromFile(new File(realPath));
		
		// you have two ways to display selected image
		
		// ( 1 ) imageView.setImageURI(uriFromPath);

		// ( 2 ) imageView.setImageBitmap(bitmap); 
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		imageView.setImageBitmap(bitmap); 
		
		Log.d("HMKCODE", "Build.VERSION.SDK_INT:"+sdk);
		Log.d("HMKCODE", "URI Path:"+uriPath);
	    Log.d("HMKCODE", "Real Path: "+realPath);
	}

}
