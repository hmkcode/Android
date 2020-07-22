package com.example.mayur.video;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    VideoView videoView;

    String VideoURL="http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        videoView= (VideoView) findViewById(R.id.videoView);

        progressDialog=new ProgressDialog(MainActivity.this);
        //dialog box title
        progressDialog.setTitle("Android Video Player");
        //dialog box message
        progressDialog.setMessage("Buffering...");

        progressDialog.setCancelable(false);
        progressDialog.show();

        try{

            MediaController mediaController=new MediaController(MainActivity.this);
            mediaController.setAnchorView(videoView);

            Uri uriparser = Uri.parse(VideoURL);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uriparser);

            //videoView.setVideoPath();

            Log.d("Status","Video Buffering Successful");


        }
        catch (Exception e )
        {
            Log.e("Error",e.getMessage());
            e.printStackTrace();

        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
                videoView.start();
            }
        });


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
