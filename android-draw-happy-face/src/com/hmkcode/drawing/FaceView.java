package com.hmkcode.drawing;


import com.hmkcode.drawing.shapes.Face;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FaceView extends View {	
	 private float radius;
	 Face face;
	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// get radius value
		TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FaceView,
                0, 0
        );
		
		try {
            radius = a.getDimension(R.styleable.FaceView_radius, 20.0f);
        } finally {
            a.recycle();
        }
		
		// initiate Face class
		face = new Face(radius);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		face.draw(canvas);
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	    int desiredWidth = (int) radius*2+(int) Math.ceil((radius/1.70));
	    int desiredHeight = (int) radius*2+(int)Math.ceil((radius/1.70));

	    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

	    int width;
	    int height;

	    //Measure Width
	    if (widthMode == MeasureSpec.EXACTLY) {
	        //Must be this size
	        width = widthSize;
	    } else if (widthMode == MeasureSpec.AT_MOST) {
	        //Can't be bigger than...
	        width = Math.min(desiredWidth, widthSize);
	    	Log.d("Width AT_MOST", "width: "+width);
	    } else {
	        //Be whatever you want
	        width = desiredWidth;
	    	Log.d("Width ELSE", "width: "+width);

	    }

	    //Measure Height
	    if (heightMode == MeasureSpec.EXACTLY) {
	        //Must be this size
	        height = heightSize;
	    } else if (heightMode == MeasureSpec.AT_MOST) {
	        //Can't be bigger than...
	        height = Math.min(desiredHeight, heightSize);
	    } else {
	        //Be whatever you want
	        height = desiredHeight;
	    }

	    //MUST CALL THIS
	    setMeasuredDimension(width, height);
	}
		public float getRadius() {
			return radius;
		}

		public void setRadius(float radius) {
			this.radius = radius;
		}
	
}
