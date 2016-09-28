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

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class CloudManager {

    private static final int SERVER_TIMEOUT_LIMIT = 20000;
    private static final String TIME_SINCE_UPLOADED_LEFT_VACCANT = "";
    private static Context context;
    private static CloudManager mInstance;
    private DbHelper dbHelper;
    private boolean doReset;

    public CloudManager(Context context) {
        this.context = context;
        this.dbHelper = DbHelper.getInstance(context);
    }

    public static CloudManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CloudManager(context);
        }
        return mInstance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Trending Section
    ///////////////////////////////////////////////////////////////////////////

    public void lazyRequestTrending() {

        // clear out back stored data
        dbHelper.resetTrendingList();
        doReset = true;
        requestSupportedPlaylist();

    }

    /**
     * @param type section for trending e.g. pop , rock etc.
     */
    private void requestTrendingType(String type) {
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

    /**
     * @param response json response for trending items
     */
    private void handleTrending(String response) {

      //  L.m("CMG","resp - "+response);
        SectionModel trendingResult = null;
        ItemModel item;

        try {

            //L.m("CM(test trending )", "" + response);

            JSONObject resObj = new JSONObject(response);

            int count = Integer.parseInt(resObj.getJSONObject("metadata").getString("count"));

            String sections = resObj.getJSONObject("metadata").getString("type");

            JSONObject resultsSubObject = resObj.getJSONObject("results");


            //ArrayList<SectionModel> sectionModelsToDB = new ArrayList<>();   // adding all section items at a time

         ///   for (int i = 0; i < count; i++) {       // i represent current section Model item

                JSONArray typeArray = resultsSubObject.getJSONArray(sections);

                ArrayList<ItemModel> itemModelArrayList = new ArrayList<>();

                for (int j = 0; j < typeArray.length(); j++) {      // j represent current item model inside i`th section model

                    JSONObject songObj = (JSONObject) typeArray.get(j);
                    item = new ItemModel(songObj.getString("title"),
                            songObj.getString("length"),
                            songObj.getString("uploader"),
                            songObj.getString("thumb"),
                            songObj.getString("get_url"),
                            TIME_SINCE_UPLOADED_LEFT_VACCANT,
                            songObj.getString("views"),
                            sections);
    //                L.m("CMG","adding "+songObj.getString("title")+" of type "+item.type+" to local");
                    itemModelArrayList.add(item);
                }
  //          L.m("CMG"," creating model with list size "+itemModelArrayList.size());
                trendingResult = new SectionModel(sections, itemModelArrayList);
//            L.m("CMG","adding local list for db addition");
//                sectionModelsToDB.add(trendingResult);
            //}
            dbHelper.addTrendingList(trendingResult,doReset);
            doReset = false;

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        L.m("CM(test)", " Trending Type " + trendingResult.sectionTitle);

    }

    private void requestSupportedPlaylist() {

        final String url = URLS.URL_SUPPORTED_PLAYLIST;

        StringRequest playlistReq = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        handleSupportedPlaylists(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        playlistReq.setRetryPolicy(new DefaultRetryPolicy(
                SERVER_TIMEOUT_LIMIT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(playlistReq, "playReq", context);
    }

    private void handleSupportedPlaylists(String response) {

        //L.m("CM","handing "+response);

        ArrayList<String> playlists = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(response);

            int playlistCount = Integer.parseInt(object.getJSONObject("metadata").getString("count"));

            JSONArray playlistJsonArray = object.getJSONArray("results");

            for (int i = 0; i < playlistCount; i++) {

                requestTrendingType(playlistJsonArray.getJSONObject(i).getString("playlist"));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //L.m("CM(test) ","Supported Playlist");

    }

    ///////////////////////////////////////////////////////////////////////////
    // Results Section
    ///////////////////////////////////////////////////////////////////////////


    public void requestSearch(String term) {

        String url = URLS.URL_SEARCH_RESULT + "q=" + URLEncoder.encode(term);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                handleResultResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

      //          L.m("CM", "error " + volleyError);

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(SERVER_TIMEOUT_LIMIT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(request, "resultReq", context);
    }

    private void handleResultResponse(String response) {

        L.m("CM", "got result " + response);
        ArrayList<ItemModel> songs = new ArrayList<>();

        try {
            JSONObject rootObj = new JSONObject(response);
            int results_count = rootObj.getJSONObject("metadata").getInt("count");

            JSONArray results = rootObj.getJSONArray("results");
            for (int i = 0; i < results_count; i++) {
                String enc_v_id = results.getJSONObject(i).getString("get_url").substring(14);
               // L.m("CM", " video id " + enc_v_id);
                songs.add(new ItemModel(results.getJSONObject(i).getString("title"),
                        results.getJSONObject(i).getString("length"),
                        results.getJSONObject(i).getString("uploader"),
                        results.getJSONObject(i).getString("thumb"),
                        enc_v_id,
                        results.getJSONObject(i).getString("time"),
                        results.getJSONObject(i).getString("views")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // database write test
        dbHelper.addResultsList(new SectionModel("Results", songs));

    }

}
