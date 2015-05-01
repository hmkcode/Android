package com.hmkcode.drawing.shapes;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class Face {

	Paint facePaint;
	Paint mePaint;
	
	
	float radius; 
	float adjust;
	
	float mouthLeftX, mouthRightX, mouthTopY, mouthBottomY;
	RectF mouthRectF; 
	Path mouthPath;
	
	RectF eyeLeftRectF, eyeRightRectF;
	float eyeLeftX, eyeRightx, eyeTopY, eyeBottomY;
	
	
	 public Face(float radius){
		this.radius= radius;
		
		facePaint = new Paint();
		facePaint.setColor(0xfffed325); // yellow 
		facePaint.setDither(true);                  
		facePaint.setStrokeJoin(Paint.Join.ROUND);   
		facePaint.setStrokeCap(Paint.Cap.ROUND);      
		facePaint.setPathEffect(new CornerPathEffect(10) );   
		facePaint.setAntiAlias(true);  
		facePaint.setShadowLayer(4, 2, 2, 0x80000000);
		
		mePaint = new Paint();
		mePaint.setColor(0xff2a2a2a);
		mePaint.setDither(true);       
		mePaint.setStyle(Paint.Style.STROKE); 
		mePaint.setStrokeJoin(Paint.Join.ROUND); 
		mePaint.setStrokeCap(Paint.Cap.ROUND);     
		mePaint.setPathEffect(new CornerPathEffect(10) );   
		mePaint.setAntiAlias(true); 
		mePaint.setStrokeWidth(radius / 14.0f);
		
		
		
		adjust = radius / 3.2f;

		
		// Left Eye
		eyeLeftX = radius-(radius*0.43f);
		eyeRightx = eyeLeftX+ (radius*0.3f);
		eyeTopY = radius-(radius*0.5f);
		eyeBottomY = eyeTopY + (radius*0.4f);
		
		eyeLeftRectF = new RectF(eyeLeftX+adjust,eyeTopY+adjust,eyeRightx+adjust,eyeBottomY+adjust);

		// Right Eye
		eyeLeftX = eyeRightx + (radius*0.3f);
		eyeRightx = eyeLeftX + (radius*0.3f);

		eyeRightRectF = new RectF(eyeLeftX+adjust,eyeTopY+adjust,eyeRightx+adjust,eyeBottomY+adjust);
		
		
		// Smiley Mouth
		mouthLeftX = radius-(radius/2.0f);
		mouthRightX = mouthLeftX+ radius;
		mouthTopY = radius - (radius*0.2f);
		mouthBottomY = mouthTopY + (radius*0.5f);

		mouthRectF = new RectF(mouthLeftX+adjust,mouthTopY+adjust,mouthRightX+adjust,mouthBottomY+adjust);
		mouthPath = new Path();
		
		mouthPath.arcTo(mouthRectF, 30, 120, true);
	}
	
	public void draw(Canvas canvas) {
		
		// 1. draw face
		canvas.drawCircle(radius+adjust, radius+adjust, radius, facePaint);
	 
	    // 2. draw mouth
		mePaint.setStyle(Paint.Style.STROKE); 

		canvas.drawPath(mouthPath, mePaint);
		
		// 3. draw eyes
		mePaint.setStyle(Paint.Style.FILL); 
		canvas.drawArc(eyeLeftRectF, 0, 360, true, mePaint);
		canvas.drawArc(eyeRightRectF, 0, 360, true, mePaint);
 
	}
}
