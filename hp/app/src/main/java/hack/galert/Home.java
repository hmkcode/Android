package hack.galert;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    private TextView addInterestBtn;
    private DrawerLayout mDrawerLayout;
    private ArrayList<ArticlesModel> articleResultsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Handler handler;
    private ActionBarDrawerToggle mDrawerToggle;
    private EditText interestInput;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        setUpNavigationDrawer();
        initialize();

        if(savedInstanceState==null)
        requestData();

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(Constants.EXTRAA_RESULTS, articleResultsList);

        outState.putBoolean(Constants.EXTRAA_DATA_LOADED_FLAG, true);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.containsKey(Constants.EXTRAA_RESULTS)){
            ArticlesRecylerAdapter adapter = ArticlesRecylerAdapter.getInstance(this);
            recyclerView.setAdapter(adapter);
            adapter.setArticles(savedInstanceState.<ArticlesModel>getParcelableArrayList(Constants.EXTRAA_RESULTS));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            SharedPreferenceManager.getInstance(this).setLoginStatus(false);
            SharedPreferenceManager.getInstance(this).setUserEmail("");
            SharedPreferenceManager.getInstance(this).setUserFullName("");
            SharedPreferenceManager.getInstance(this).setUserToken("");

            startActivity(new Intent(Home.this, Login.class));
            finish();
        }
        if (id == R.id.action_setting) {
            SharedPreferenceManager.getInstance(this).setLoginStatus(false);
            startActivity(new Intent(Home.this, Settings.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NowKnowMore");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void setUpNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
        transactFragment();
        Log.d("Home", "" + R.id.fragmentNav);

    }

    public void transactFragment(){

        NavigationFragment navigationFragment = new NavigationFragment();
        navigationFragment.setmActionListener(new NavigationFragment.ActionEventLister() {
            @Override
            public void onNewInterest() {
                takeNoteOfInterest();
            }

            @Override
            public void onItemTapped(int topicIndex) {
                SharedPreferenceManager.getInstance(Home.this).setLastLoadedSubs(topicIndex);
                mDrawerLayout.closeDrawers();
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Log.d("Home", "reqs for " + topicIndex);
                requestData();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentNav, navigationFragment)
                .commit();
    }

    public void initialize() {

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresser);
        recyclerView = (RecyclerView) findViewById(R.id.articlesRecyclerView);
        progressBar = (ProgressBar) findViewById(R.id.homeProgressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        handler = new Handler();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.PrimaryColor), Color.WHITE, getResources().getColor(R.color.SecondaryColor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshFeeds();
                        Log.e("Feeds", "handler posted");
                    }
                }, 0);
            }
        });

    }

    public void takeNoteOfInterest(){

        View view = LayoutInflater.from(this).inflate(R.layout.add_interest_dialog_layout,null,false);

        final Dialog interestDialog = new Dialog(this);
        interestDialog.setCancelable(true);
        interestDialog.setTitle("Interest");
        interestDialog.setContentView(view);

        addInterestBtn = (TextView) view.findViewById(R.id.addInterestDialogBtn);
        interestInput = (EditText) view.findViewById(R.id.interestEditBox);

        addInterestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!interestInput.getText().toString().isEmpty()) {
                    addInterest(interestInput.getText().toString());
                    interestDialog.dismiss();
                }
            }
        });

        mDrawerLayout.closeDrawers();
        interestDialog.show();

    }

    private void addInterest(String topic) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Interest...");

        if(ConnectionUtils.getInstance(this).isConnected()){

            progressDialog.show();
            requestAddingInterest(topic);
        }

    }

    public void requestAddingInterest(String interest){

        final String TAG = "interestAdding";
        // TODO: append interest
        final String url = Constants.SERVER_URL_ADD_INTEREST;
        JSONObject jsonBody = null;
        final String token = SharedPreferenceManager.getInstance(this).getUserToken();
        try {
            jsonBody = new JSONObject("{\"term\":\"" + interest + "\"}");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest addSubsJsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                        int id = jsonObject.getInt("id");

                            if(id>-1){
                                transactFragment();
                                progressDialog.dismiss();
                                makeToast("Added Your Interest");

                            }
                            else{
                                progressDialog.hide();
                                progressDialog.dismiss();
                                makeSnackbar("Cannot Add Your Interest ! ");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            makeSnackbar("Cannot Add Your Interest !");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Log.d("Home",volleyError.toString());
                        makeSnackbar("Adding Failed!");
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT " + token);
                return params;
            }
        };

        progressDialog.show();

        addSubsJsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(addSubsJsonRequest, TAG, this);
    }

    public void makeToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void refreshFeeds() {

        if (ConnectionUtils.getInstance(this).isConnected()) {

            requestData();
            if(!swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(true);

        } else {
            Snackbar.make(swipeRefreshLayout, "No Connectivity !!", Snackbar.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    public void requestData() {

        final String TAG = "articleFetch";

        // TODO: append email and password to url
        int subId = SharedPreferenceManager.getInstance(this).getLastLoadedSubs();
        if (subId == -1) {
            // dismiss progress and show error
            progressBar.setVisibility(View.GONE);
            makeSnackbar("You Lacks Interests. Plz Add One or More ");
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }

        final String url = Constants.SERVER_URL_FEEDS+""+subId;

        StringRequest articlesReq = new StringRequest(
                StringRequest.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("Home",s);
                        parseFeeds(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        makeSnackbar("Authentication Failed !");
                    }
                });

        articlesReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(articlesReq, TAG, this);
    }

    private void parseFeeds(String response) {

        ArrayList<ArticlesModel> articles = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(response);
            JSONArray jarr = jobj.getJSONArray("articles");

            for(int i=0;i<jarr.length();i++){
                JSONObject inObj = jarr.getJSONObject(i);
                articles.add(new ArticlesModel(inObj.getInt("id"),
                        inObj.getString("website")
                    ,""+(inObj.getString("text").length()/1000), inObj.getString("title"),
                        inObj.getString("text"),
                        inObj.getString("image")
                        ));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArticlesRecylerAdapter adapter = ArticlesRecylerAdapter.getInstance(this);
        adapter.setArticles(articles);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);

        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void makeSnackbar(String msg) {
        ///Snackbar.make(passwordIconText, msg, Snackbar.LENGTH_LONG).show();
    }

}
