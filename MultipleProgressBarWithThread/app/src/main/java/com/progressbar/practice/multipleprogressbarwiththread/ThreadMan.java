package com.progressbar.practice.multipleprogressbarwiththread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Ankit on 9/5/2016.
 */
public class ThreadMan extends Thread {

    private Handler mHandler;
    private int progressBarId;

    public ThreadMan() {
    }

    private static Context context;
    private static ThreadMan mInstance;

    public ThreadMan(Context context) {
        this.context = context;
    }

    public static ThreadMan getInstance(Handler handler , int progressBarId) {
        if (mInstance == null) {
            mInstance = new ThreadMan(handler ,progressBarId);
        }
        return mInstance;
    }

    public ThreadMan(Handler handler , int progressBarId) {
        this.mHandler = handler;
        this.progressBarId = progressBarId;
    }

    @Override
    public void run() {


        for (int i = 0; i < 100; i++) {
            Message msg = Message.obtain();
            msg.arg1 = this.progressBarId;
            msg.arg2 = i;
            mHandler.sendMessage(msg);

            try {
                Log.d(Thread.currentThread().getName()," Sleeping..");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
