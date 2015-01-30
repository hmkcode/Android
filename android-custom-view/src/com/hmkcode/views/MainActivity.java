package com.hmkcode.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

	SimpleView svCircle;
	SimpleView svSquare;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		svCircle = (SimpleView) findViewById(R.id.simpleViewCircle);
		svSquare = (SimpleView) findViewById(R.id.simpleViewSquare);
		tv = (TextView) findViewById(R.id.tv);
		
		svCircle.setOnClickListener(this);
		svSquare.setOnClickListener(this);

	}


	@Override
	public void onClick(View view) {
	
		switch(view.getId()){
			case R.id.simpleViewCircle:
				tv.setText("Circle");
				break;
			case R.id.simpleViewSquare:
				tv.setText("Square");
				break;
			
		}
		
	}
	
	

}
