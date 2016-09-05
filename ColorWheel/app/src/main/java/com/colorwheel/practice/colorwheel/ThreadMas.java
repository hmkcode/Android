package com.colorwheel.practice.colorwheel;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Ankit on 9/5/2016.
 */
public class ThreadMas extends Thread {

    int delay = 200;
    int colorType;
    private Handler mHandler;

    public ThreadMas() {

    }

    public ThreadMas setHandler(Handler handler) {
        this.mHandler = handler;
        return this;
    }

    public ThreadMas setColorType(int type) {
        this.colorType = type;
        return this;
    }

    public ThreadMas setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    @Override
    public void run() {

        for (int i = 0; i <= 255; i++) {
            Message msg = Message.obtain();
            msg.arg1 = colorType;
            msg.arg2 = i;
            mHandler.sendMessage(msg);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
