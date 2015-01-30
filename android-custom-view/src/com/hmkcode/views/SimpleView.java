package com.hmkcode.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView extends View {

	float dim;
	int shape;
	Paint paint;
	
	public static final int CIRCLE = 0;
	public static final int SQUARE = 1;
	public SimpleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SimpleView,
                0, 0
        );
		
		try {
            dim = a.getDimension(R.styleable.SimpleView_dim, 20f);
            shape = a.getInteger(R.styleable.SimpleView_shape, 0);
        } finally {
            a.recycle();
        }
		
		paint = new Paint();
		paint.setColor(0xfffed325); // yellow 
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// draw circle
		switch(shape){
			case CIRCLE:
				canvas.drawCircle(dim, dim, dim, paint);
				break;
			case SQUARE:
				canvas.drawRect(0, 0, dim, dim, paint);
				break;

		}

	}
	

}
