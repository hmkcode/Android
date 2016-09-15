package simple.musicgenie;


import android.content.ClipData;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CloudManager {

    private static final int SERVER_TIMEOUT_LIMIT = 20000;
    private static final String TIME_SINCE_UPLOADED_LEFT_VACCANT = "";
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

        ArrayList<SectionModel> trendingResults = new ArrayList<>();
        ItemModel item;
        try {
            JSONObject resObj = new JSONObject(response);

            int count = Integer.parseInt(resObj.getJSONObject("metadata").getString("count"));
            ArrayList<String> sections = new Segmentor().getParts(resObj.getJSONObject("metadata").getString("type"), ',');

            JSONObject resultsSubObject = resObj.getJSONObject("results");

            for (int i = 0; i < count; i++) {

                JSONArray typeArray = resultsSubObject.getJSONArray(sections.get(i));
//String title, String trackDuration, String uploadedBy,
// String thumbnail_url, String video_id, String timeSinceUploaded, String userViews, String type
                for (int j = 0; j < typeArray.length(); j++) {
                    JSONObject songObj = (JSONObject) typeArray.get(j);
                        item = new ItemModel(songObj.getString("title"),
                                songObj.getString("length"),
                                songObj.getString("uploader"),
                                songObj.getString("thumb"),
                                songObj.getString("get_url"),
                                TIME_SINCE_UPLOADED_LEFT_VACCANT,
                                songObj.getString("views"),
                                sections.get(i));
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
