package hack.galert;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankit on 9/10/2016.
 */
public class NavigationFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private NavListAdapter adapter;
    private TextView addInterestBtn;
    private ArrayList<NavItems> navItemsArrayList;
    private TextView userEmail;
    private TextView userFullName;

    public NavigationFragment(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
    }

    public NavigationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Log.d("NavigationFragment","return "+R.layout.navigation_drawer_fragment);
        View v = inflater.inflate(R.layout.navigation_drawer_fragment, null, false);
        //  Log.d("NavigationFragment","return "+v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        listView = (ListView) view.findViewById(R.id.navList);
        adapter = NavListAdapter.getInstance(getActivity());
        listView.setAdapter(adapter);
        addInterestBtn = (TextView) view.findViewById(R.id.addInterestBtnText);
        userEmail = (TextView) view.findViewById(R.id.userEmil);
        userFullName = (TextView) view.findViewById(R.id.userName);

        SharedPreferenceManager utils = SharedPreferenceManager.getInstance(getActivity());
        userEmail.setText(utils.getUserEmail());
        userFullName.setText(utils.getUserName());

        requestNavigationItems();

        addInterestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActionListener != null) {
                    mActionListener.onNewInterest();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                view.setSelected(true);
                if (mActionListener != null) {
                    int id = navItemsArrayList.get(pos).id;
                    mActionListener.onItemTapped(id);
                }
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    public void requestNavigationItems() {

        //final String url = Constants.SERVER_URL_NAVIGATION_ITEMS;
        SharedPreferenceManager utils = SharedPreferenceManager.getInstance(getActivity());
        final String token = utils.getUserToken();

        final String url = Constants.SERVER_URL_NAVIGATION_ITEMS;

        StringRequest navItemsListRequest = new StringRequest(
                StringRequest.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Log.d("Home Nav",s);
                        parseNavigationItemsResponse(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT " + token);
                return params;
            }
        };

        navItemsListRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(navItemsListRequest, "navItemsReq", getActivity());

    }

    public void parseNavigationItemsResponse(String response) {

        navItemsArrayList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                navItemsArrayList.add(new NavItems(obj.getString("term"), obj.getInt("id"), obj.getString("last_fetched")));
            }

            updateListAdapter(navItemsArrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void updateListAdapter(ArrayList<NavItems> navItems) {

        adapter.setItems(navItems);
        listView.setAdapter(adapter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private ActionEventLister mActionListener;

    public void setmActionListener(ActionEventLister mActionListener) {
        this.mActionListener = mActionListener;
    }

    public interface ActionEventLister {
        void onNewInterest();

        void onItemTapped(int topicInd);
    }

}
