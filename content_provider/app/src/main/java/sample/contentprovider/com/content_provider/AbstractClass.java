package sample.contentprovider.com.content_provider;

import android.content.Context;

public abstract class AbstractClass {

    public abstract String getFullMessage();

    public abstract int getRoll();

    public void printAll(Context context){
        PrinterClass.getInstance(context).printThem(this);
    }

}
