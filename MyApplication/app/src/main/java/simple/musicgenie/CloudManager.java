package simple.musicgenie;


import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudManager {

    private static final int SERVER_TIMEOUT_LIMIT = 20000;
    private static Context context;
    private static CloudManager mInstance;

    public CloudManager(Context context) {
        this.context = context;
    }

    public static CloudManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CloudManager(context);
        }
        return mInstance;
    }


    public void requestTrendingData(String type) {
        int count = 8; // max 25
        final String url = URLS.URL_TRENDING_API + "?type=" + type + "&number=" + count + "&offset=0";

        StringRequest trendingReq = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        handleTrending(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        trendingReq.setRetryPolicy(new DefaultRetryPolicy(
                SERVER_TIMEOUT_LIMIT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(trendingReq, "trendingReq", context);

    }

    private void handleTrending(String response) {


        try {
            JSONObject resObj = new JSONObject(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
