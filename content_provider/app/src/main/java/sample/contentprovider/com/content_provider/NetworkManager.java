package sample.contentprovider.com.content_provider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ankit on 9/4/2016.
 */
public class NetworkManager {

    private static Context context;
    private static NetworkManager mInstance;

    public NetworkManager(Context context) {
        this.context = context;
    }

    public static NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    public boolean isConnected(){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info!=null && info.isConnected()){
            return true;
        }

        return false;
    }

}
