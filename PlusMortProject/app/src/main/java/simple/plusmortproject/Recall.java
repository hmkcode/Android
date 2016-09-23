package simple.plusmortproject;

import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

public class Recall extends AppCompatActivity {


    public Recall() {
        super();
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    @Override
    public boolean supportRequestWindowFeature(int featureId) {
        return super.supportRequestWindowFeature(featureId);
    }

    @Override
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        super.onSupportActionModeStarted(mode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        ListenToState listener = new ListenToState();
        telephonyManager.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
    }

    class ListenToState extends PhoneStateListener {
        boolean callEnded = false;

        public void onCallStateChanged(int state, String incomingNumber){
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.e("Call"," >> idle");
                    if(callEnded){

                    }else{

                    }

                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(Recall.this, "ringing", Toast.LENGTH_LONG).show();
                    Log.e("Call", ">> ringing");

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.e("Call",">> offhook");
                    record();
                    break;

            }
        }

    }

    public void record(){
        final MediaRecorder callRecorder = new MediaRecorder();
        callRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_UPLINK);
        callRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        callRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        String filepath= "recorder/data";
        callRecorder.setOutputFile(filepath);

        try {
            callRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        callRecorder.start();
    }


}
