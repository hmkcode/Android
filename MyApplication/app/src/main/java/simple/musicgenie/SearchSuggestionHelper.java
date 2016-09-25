package simple.musicgenie;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Ankit on 9/25/2016.
 */
public class SearchSuggestionHelper {


    private static final String TAG = "SuggestionHelper";
    private static Context context;
    private static int resultLimit;
    private static ArrayList<SearchSuggestion> searchSuggestions;
    private static SearchSuggestionHelper mInstance;
    public OnFindSuggestionListener onFindSuggestionListener;

    public interface OnFindSuggestionListener{
        void onResult(ArrayList<SearchSuggestion> list);
    }

    public SearchSuggestionHelper(Context context) {
        this.context = context;
    }

    public static SearchSuggestionHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SearchSuggestionHelper(context);
        }
        return mInstance;
    }

    public static ArrayList<SearchSuggestion> getHistory(Context context) {

        ArrayList<SearchSuggestion> suggestionList = new ArrayList<>();
        SearchSuggestion sSuggestion;
        if(searchSuggestions!=null)
            for (int i = 0; i < searchSuggestions.size(); i++) {
                sSuggestion = searchSuggestions.get(i);
                sSuggestion.setIsHistory(true);
                suggestionList.add(sSuggestion);
            }
        return suggestionList;
    }

    public void findSuggestion(String query, final OnFindSuggestionListener callback){
        // this.onFindSuggestionListener = callback;

        String url = "http://suggestqueries.google.com/complete/search?q="+ URLEncoder.encode(query)+"&client=firefox&hl=en&ds=yt";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //log("response "+response);
                ArrayList<SearchSuggestion> suggestionsList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    //JSONArray suggestions = new JSONArray();
                    String suggestionsArr = jsonArray.get(1).toString();
                    JSONArray sarr = new JSONArray(suggestionsArr);
                    for(int i=0;i<sarr.length();i++){
                        suggestionsList.add(new SearchSuggestion(sarr.get(i).toString()));
                    }

                    searchSuggestions = suggestionsList;
                    if(callback!=null){
                        callback.onResult(suggestionsList);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                log("[fireSearch()] Error While searching :" + volleyError);
            }
        });

        VolleyUtils.getInstance().addToRequestQueue(request, TAG, context);



    }

    private void log(String s) {
        Log.d(TAG, "log " + s);
    }
}
