package simple.musicgenie;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Ankit on 9/13/2016.
 */
public class PermissionManager {
    private static final int PERMISSION_GRANTED = 1;
    private static final int PERMISSION_DENIED = 0;
    private static Context context;
    private static PermissionManager mInstance;

    public PermissionManager(Context context) {
        this.context = context;
    }

    public static PermissionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PermissionManager(context);
        }
        return mInstance;
    }


    public void seek(){


        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {


                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
                );
            }
        }

    }
}
