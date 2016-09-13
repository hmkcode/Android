package simple.music;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Ankit on 9/13/2016.
 */
public class AppConfig extends Application {

    private static final String TAG = "AppConfig";
    private static Context context;
    private static AppConfig mInstance;

    public AppConfig(Context context) {
        this.context = context;
    }

    public static AppConfig getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppConfig(context);
        }
        return mInstance;
    }

    public static void configureDevice() {

        String savePref = SharedPrefrenceUtils.getInstance(context).getFileSavingLocation();
        int tasks_pending = TaskHandler.getInstance(context).getTaskCount();
        if(tasks_pending>0)LocalNotificationManager.getInstance(context).launchNotification("You Have "+ tasks_pending +" Tasks Pending");

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            PermissionManager.getInstance(context).seek();
        }

        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root+ "/Musicgenie/Audio");

        boolean s = false;
        if (dir.exists() == false) {
            s = dir.mkdirs();
        }

        Log.d(TAG, "configureDevice : made directory " + s);

    }
}
