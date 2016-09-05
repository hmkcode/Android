package com.colorwheel.practice.colorwheel;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        // set color filter to seekbar
        setColorFilter(redSeeker, R.color.Red);
        setColorFilter(greenSeeker, R.color.Green);
        setColorFilter(blueSeeker, R.color.Blue);

        tag = (TextView) redChipView.findViewById(R.id.Tag);
        tag.setTextColor(Color.parseColor("#00dd00"));






    }

    public void setColorFilter(View v , int color){

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
