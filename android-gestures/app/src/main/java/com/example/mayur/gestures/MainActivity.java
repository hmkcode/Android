package com.example.mayur.gestures;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    ImageView imageView;
    TextView textView;

    GestureDetectorCompat gestureDetectorCompat;



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.gestureDetectorCompat.onTouchEvent(event);


        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
         textView= (TextView) findViewById(R.id.textView);

        this.gestureDetectorCompat=new GestureDetectorCompat(this,this);
        gestureDetectorCompat.setOnDoubleTapListener(this);

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {


        textView.setText("I'm double tapped");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        RelativeLayout.LayoutParams position = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT

        );

        position.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        position.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        imageView.setLayoutParams(position);

        RelativeLayout.LayoutParams size = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        size.width=300;
        size.height=300;

        imageView.setLayoutParams(size);



        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        imageView.setImageResource(R.drawable.images);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        textView.setText("I am flinged");
        return true;
    }
}
