package simple.music;

import android.content.Context;


/**
 * Create Local cache for trending songs Only with images
 */
public class LocalCacheManager {

    private static Context context;
    private static LocalCacheManager mInstance;

    public LocalCacheManager(Context context) {
        this.context = context;
    }

    public static LocalCacheManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocalCacheManager(context);
        }
        return mInstance;
    }


}
