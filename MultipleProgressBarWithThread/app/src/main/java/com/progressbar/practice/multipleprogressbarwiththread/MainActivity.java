package com.progressbar.practice.multipleprogressbarwiththread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static Handler mHandler;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // find the progress bar and set the progress
                // arg1 : id of progress bar to update
                // arg2 : progress value (0 - 100%)
                int prog_id = msg.arg1;
                ((ProgressBar) findViewById(prog_id)).setProgress(msg.arg2);

                // enabling button after finish
                if(msg.arg2==100){

                    switch (prog_id){
                        case R.id.progressBar:
                            findViewById(R.id.button).setEnabled(true);
                            break;
                        case R.id.progressBar2:
                            findViewById(R.id.button2).setEnabled(true);
                            break;
                        case R.id.progressBar3:
                            findViewById(R.id.startThreadBtn).setEnabled(true);
                    }
                }

            }
        };

        // set onClickListeners
        findViewById(R.id.startThreadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disable multiple thread to start for single view
                findViewById(R.id.startThreadBtn).setEnabled(false);
                new ThreadMan(mHandler, R.id.progressBar3).start();
                Log.d("Main ", " Started Prog 3");
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disable multiple thread to start for single view
                findViewById(R.id.button).setEnabled(false);

                new ThreadMan(mHandler, R.id.progressBar).start();
                Log.d("Main ", " Started Prog 1");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disable multiple thread to start for single view
                findViewById(R.id.button2).setEnabled(false);

                new ThreadMan(mHandler, R.id.progressBar2).start();
                Log.d("Main ", " Started Prog 2");
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
