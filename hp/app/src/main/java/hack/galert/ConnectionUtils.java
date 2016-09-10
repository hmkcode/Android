package hack.galert;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Ankit on 9/10/2016.
 */
public class ConnectionUtils {

    private static Context context;
    private static ConnectionUtils mInstance;

    public ConnectionUtils(Context context) {
        this.context = context;
    }

    public static ConnectionUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ConnectionUtils(context);
        }
        return mInstance;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo mobileData = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        final android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mobileData.isConnected()) {
            return true;
        } else if (wifi.isConnected()) {
            return true;
        }
        return false;
    }

}
