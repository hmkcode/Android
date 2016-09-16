package com.material.practice.socialsample;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    CharSequence bTitle;
    CharSequence bDrawerTitle;
    NavigationView bNavigationView;
    DrawerLayout bDrawerLayout;
    ActionBarDrawerToggle bDrawerToggle;
    Toolbar toolbar;
    FloatingActionButton fab;
    TabLayout tabLayout;
    ViewPager viewPager;
    SessionManager sessionManager;
    PagerAdapter pagerAdapter;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        setContentView(R.layout.activity_home);
        init();
        TaskHandler.getInstance(this).initialize();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getHash(String str) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.toString().getBytes("UTF-8"));
            byte[] ret = md.digest();
            for (int i = 0; i < ret.length; i++) {
                sb.append(Integer.toString((ret[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i== KeyEvent.ACTION_DOWN && i== KeyEvent.KEYCODE_ENTER){
            Log.e("Login","enter from");
            attemptLogin();
        }
        return false;
    }
}
    @Override
    protected void onResume(){
        super.onResume();
        //   Log.e("Home", "vp stat ::" + viewPager);
        if(SessionManager.getSendType().equals("1")){
            viewPager.setCurrentItem(1);
            SessionManager.setSendType("0");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        bDrawerToggle.onConfigurationChanged(configuration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_Profile:
                Intent aboutUsIntent = new Intent(this, Profile.class);
                Bundle data=new Bundle();
                //TODO: hard coded user name
                data.putString("username","Ankit");
                aboutUsIntent.putExtras(data);
                startActivity(aboutUsIntent);
                break;
            case R.id.action_LogOut:
                logout();
                break;
            case R.id.action_CreatePoll:
                navigateToPollCreation();
        }
        return false;
    }

    private void like(final String PostID, final String UserId, final String UserToken) {

        StringRequest request = new StringRequest(Request.Method.POST, App_Config.Vote_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                Log.e("IP", "" + response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("IP",""+volleyError);
                Snackbar.make(userId, "Connection Problem ! ", Snackbar.LENGTH_LONG).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "upvote");
                Log.e("IP", UserId);
                params.put("id", UserId);
                params.put("postid", PostID);
                Log.e("IP", PostID);
                params.put("token", UserToken);
                Log.e("IP", UserToken);;
                return params;
            }

        };

        // Log.e("instanse", "" + AppManager.getInstance());
        AppManager.getInstance().addToRequestQueue(request, "lrq", this);

    }


    private void backoff(final String PostID,final String UserId,final String UserToken){
        String TAG = "Backoff Vote";
        StringRequest request = new StringRequest(Request.Method.POST, App_Config.Vote_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("IP","backoff>"+response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("IP",""+volleyError);
                Snackbar.make(userId,"Connection Problem !",Snackbar.LENGTH_LONG).show();



            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "none");
                params.put("id", UserId);
                params.put("postid",PostID);
                params.put("token", UserToken);
                return params;
            }

        };

        AppManager.getInstance().addToRequestQueue(request, "likeReq", new Contexter().getContext());
    }


    public void dislike(final String PostID, final String UserId, final String UserToken) {
        String TAG = "dislikeReqSEND";

        StringRequest request = new StringRequest(Request.Method.POST, App_Config.Vote_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("0")) {
                        //  ((UpdateListener) pda).update(post, position);
                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("IP",""+volleyError);
                Snackbar.make(userId, "Connection Problem ! ", Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "downvote");

                params.put("id", UserId);

                params.put("postid", PostID);

                params.put("token", UserToken);

                return params;
            }

        };

        AppManager.getInstance().addToRequestQueue(request, "drq", this);


    }


    private void requestData() {

        final CollegareUser user = DatabaseManager.getInstance(this).getUser();
        // Log.e("TT", "user id :" + user.id);
        StringRequest request = new StringRequest(Request.Method.POST, App_Config.Post_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                Log.e("IP", response + "");
                parseAndSet(response);
                progressDialoge.hide();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                timeOut();
                Log.e("volley", volleyError + "");

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "get");
                params.put("postid", pID);
                params.put("id", user.id);

                return params;
            }

        };

        AppManager.getInstance().addToRequestQueue(request, "reqPostSingle", this);

    }


    private void timeOut(){
        progressDialoge.dismiss();
        Snackbar.make(userId,"timeOut !!",Snackbar.LENGTH_LONG).show();

    }


    private void parseAndSet(String response) {
        ArrayList<CollegareComment> comments = new ArrayList<>();

        try {
            JSONObject postObj = new JSONObject(response);

            JSONArray comment = postObj.getJSONArray("comments");
            // comments parsing
            for (int i = 0; i < comment.length(); i++) {
                JSONObject temp = (JSONObject) comment.get(i);
                comments.add(new CollegareComment(
                                postObj.getString("postid"),
                                temp.getString("commentid"),
                                temp.getString("id"),
                                temp.getString("username"),
                                temp.getString("content"),
                                temp.getString("doc"))
                );
                CommentsAdapter.getInstance(this).setComments(comments);
            }
            post.comment=comments;
            post.DisLikeCount = postObj.getString("downcount");
            post.LikeCount = postObj.getString("upcount");
            post.content = postObj.getString("content");
            post.postid = postObj.getString("postid");

            userId.setText(postObj.getString("id"));
            userPic.setImageResource(R.drawable.user_pic);
            likeText.setText(postObj.getString("upcount"));

            unlikeText.setText(postObj.getString("downcount"));

            nameDisplay.setText(postObj.getString("username"));
            contentText.setText(postObj.getString("content"));
            commentCount.setText(postObj.getString("commentcount"));
            int resIdL = (postObj.getString("vote").equals("1")) ? R.drawable.upvote_48 : R.drawable.upvote_48_black;
            int resIdD = (postObj.getString("vote").equals("-1")) ? R.drawable.downvote_48 : R.drawable.downvote_48_black;

            likeImg.setImageResource(resIdL);
            unlikeImg.setImageResource(resIdD);


            return;


        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

}

}