package simple.musicgenie;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyUtils extends Application {
    public static final String TAG = VolleyUtils.class.getSimpleName();
    private RequestQueue bRequestQueue;
    private static VolleyUtils bInstance;
    Context baseContext;
    private static ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        bInstance = this;

    }

    public static synchronized ImageLoader getImageLoader(Context context) {

        if (imageLoader == null) {
            imageLoader = new ImageLoader(Volley.newRequestQueue(context), new ImageLoader.ImageCache() {
                LruCache<String, Bitmap> cache = new LruCache<>(4096);

                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bmp) {
                    cache.put(url, bmp);
                }
            });
        }
        return imageLoader;
    }

    public static synchronized VolleyUtils getInstance() {

        if (bInstance == null) {

            bInstance = new VolleyUtils();
        }

        return bInstance;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (bRequestQueue == null) {
            bRequestQueue = Volley.newRequestQueue(context);
        }
        return bRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag, Context context) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        baseContext = context;
        getRequestQueue(context).add(req);

    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue(baseContext).add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (bRequestQueue != null) {
            bRequestQueue.cancelAll(tag);
        }
    }


}