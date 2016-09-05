package com.colorwheel.practice.colorwheel;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.awt.font.TextAttribute;

public class MainActivity extends AppCompatActivity {

    private SeekBar greenSeeker;
    private SeekBar redSeeker;
    private SeekBar blueSeeker;
    private TextView colorWheel;
    private View redChipView;
    private View greenChipView;
    private View blueChipView;
    private TextView tag;
    private static Handler mHandler;
    private FrameLayout tagHolder;

    int red, green, blue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redChipView = (View) findViewById(R.id.Red_chip);
        greenChipView = (View) findViewById(R.id.Green_chip);
        blueChipView = (View) findViewById(R.id.Blue_chip);

        redSeeker = (SeekBar) findViewById(R.id.redSpeedJumper);
        greenSeeker = (SeekBar) findViewById(R.id.greenSpeedJumper);
        blueSeeker = (SeekBar) findViewById(R.id.blueSpeedJumper);
        colorWheel = (TextView) findViewById(R.id.colorWheel);

        init();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // arg1 : color type
                // arg2 : color value
                switch (msg.arg1) {
                    case Constants.RED:
                        red = msg.arg2;
                        // set back ground for chip
                        setColorChipBackground(Constants.RED,red);
                        break;
                    case Constants.GREEN:
                        green = msg.arg2;
                        // set back ground for chip
                        setColorChipBackground(Constants.GREEN,green);
                        break;
                    case Constants.BLUE:
                        blue = msg.arg2;
                        // set back ground for chip
                        setColorChipBackground(Constants.BLUE,blue);
                        break;
                }

                // setbackground of color wheel from combining
                ((GradientDrawable) colorWheel.getBackground()).setColor(Color.rgb(red, green, blue));

            }
        };

        findViewById(R.id.startColorWheelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // starting red thread
                new ThreadMas()
                        .setColorType(Constants.RED)
                        .setDelay(redSeeker.getProgress())
                        .setHandler(mHandler)
                        .start();


                // starting green thread
                new ThreadMas()
                        .setColorType(Constants.GREEN)
                        .setDelay(greenSeeker.getProgress())
                        .setHandler(mHandler)
                        .start();


                // starting blue thread
                new ThreadMas()
                        .setColorType(Constants.BLUE)
                        .setDelay(blueSeeker.getProgress())
                        .setHandler(mHandler)
                        .start();
            }
        });

    }

    public void setColorChipBackground(int colorType , int value){

        switch (colorType){
            case Constants.RED:
                tagHolder = (FrameLayout) redChipView.findViewById(R.id.tagHolder);
                ((GradientDrawable) tagHolder.getBackground()).setColor(Color.rgb(value,0,0));
                break;
            case Constants.GREEN:
                tagHolder = (FrameLayout) greenChipView.findViewById(R.id.tagHolder);
                ((GradientDrawable) tagHolder.getBackground()).setColor(Color.rgb(0,value,0));
                break;
            case Constants.BLUE:
                tagHolder = (FrameLayout) blueChipView.findViewById(R.id.tagHolder);
                ((GradientDrawable) tagHolder.getBackground()).setColor(Color.rgb(0,0,value));
                break;
        }

    }

    public void init() {
        // initialize seekers with value and color filter
        setColorFilter(redSeeker, R.color.Red);
        setColorFilter(greenSeeker, R.color.Green);
        setColorFilter(blueSeeker, R.color.Blue);

        // init color wheel
        red = green = blue = 0;


    }

    public void setColorFilter(View v, int color) {

        ((SeekBar) v).getProgressDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN);
        ((SeekBar) v).getThumb().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
