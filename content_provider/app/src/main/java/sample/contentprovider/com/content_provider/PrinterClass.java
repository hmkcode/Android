package sample.contentprovider.com.content_provider;

import android.content.Context;
import android.util.Log;


public class PrinterClass {
    private static Context context;
    private static PrinterClass mInstance;

    public PrinterClass(Context context) {
        this.context = context;
    }

    public static PrinterClass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrinterClass(context);
        }
        return mInstance;
    }

    public void printThem(AbstractClass abstractClassObject){
        Log.d("PrinterClass", "Name  : "+abstractClassObject.getFullMessage());
        Log.d("PrinterClass", "Roll  : "+abstractClassObject.getRoll());
    }

}
