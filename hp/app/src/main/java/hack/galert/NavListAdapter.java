package hack.galert;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ankit on 9/10/2016.
 */
public class NavListAdapter extends ArrayAdapter<NavItems> {
    private static Context context;
    private static NavListAdapter mInstance;
    private ArrayList<NavItems> items;
    int checkedIndex;
    private TextView removeInterestBtn;
    private TextView title;

    public NavListAdapter(Context context) {
        super(context, 0);
        this.context = context;
        this.items = new ArrayList<>();
    }

    public static NavListAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NavListAdapter(context);
        }
        return mInstance;
    }

    public void setItems(ArrayList<NavItems> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public void setCheckedIndex(int check) {
        checkedIndex = check;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.navigation_item_layout, null, false);
        }

        //TextView icon = (TextView) view.findViewById(R.id.itemIcon);
        title = (TextView) view.findViewById(R.id.listItem);
        removeInterestBtn = (TextView) view.findViewById(R.id.removeInterestBtnText);

        Typeface robotoMedium = FontManager.getInstance(context).getTypeFace(FontManager.FONT_ROBOTO_REGULAR);
        Typeface materialIcon = FontManager.getInstance(context).getTypeFace(FontManager.FONT_MATERIAL);
        //icon.setText(items.get(position).getIcon());
        title.setText(items.get(position).getTitle());
        //icon.setTypeface(materialIcon);
        title.setTypeface(robotoMedium);

        removeInterestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionUtils.getInstance(context).isConnected()) {
                    removeInterest(items.get(position).id);
                    items.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    public void removeInterest(int id) {
        final JSONObject object = new JSONObject();
        SharedPreferenceManager utils = SharedPreferenceManager.getInstance(context);
        try {
            object.put("access_token", "JWT " + utils.getUserToken());
            // object.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = Constants.SERVER_URL_DELETE_INTEREST + "" + id;

        JsonObjectRequest deleteInterestReq = new JsonObjectRequest(Request.Method.DELETE, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        deleteInterestReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(deleteInterestReq, "deleteInterest", context);

    }

    @Override
    public int getCount() {
        return items.size();
    }
}
