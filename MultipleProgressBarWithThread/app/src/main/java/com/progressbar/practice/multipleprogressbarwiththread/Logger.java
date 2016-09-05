package com.progressbar.practice.multipleprogressbarwiththread;

import android.content.Context;
import android.util.Log;

/**
 * Created by Ankit on 9/5/2016.
 */
public class Logger {

    private static Context context;
    private static Logger mInstance;

    public Logger(Context context) {
        this.context = context;
    }

    public static Logger getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Logger(context);
        }
        return mInstance;
    }

    public void d(String owner,String msg){
        Log.d(owner,msg);
    }

}
