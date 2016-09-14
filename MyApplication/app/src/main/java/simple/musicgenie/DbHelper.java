package simple.musicgenie;

import android.content.Context;


/**
 * Create Local Cache Trending (temp)
 *
 */
public class DbHelper {

    private static Context context;
    private static DbHelper mInstance;

    public DbHelper(Context context) {
        this.context = context;
    }

    public static DbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DbHelper(context);
        }
        return mInstance;
    }


}
