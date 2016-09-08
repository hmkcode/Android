package com.material.practice.socialsample;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PostDetailActivity extends AppCompatActivity {


    RecyclerView comments;
    CommentsAdapter adapter;
    ImageView userPic, likeImg, unlikeImg;
    TextView nameDisplay, userId, contentText, likeText, unlikeText, commentCount;
    CollegarePost post;
    DataStore dataStore;
    String pID;
    ProgressDialog progressDialoge;
    private Toolbar toolbar;
    private int position;
    private FeedsAdapter pda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

        if (getIntent() != null)
            pID = getIntent().getExtras().getString("postId");

        if (!InternetManager.getInstance(this).isConnectedToNet()) {

            post = DatabaseManager.getInstance(this).getPost(pID);
            userId.setText(post.id);
            userPic.setImageResource(R.drawable.user_pic);
            likeText.setText(post.LikeCount);
            unlikeText.setText(post.DisLikeCount);
            nameDisplay.setText(post.username);
            contentText.setText(post.content);
            commentCount.setText(post.comment.size() + "");
            getSupportActionBar().setTitle(post.username + "`s Post");
            int resIdL = (post.isLiked.equals("true")) ? R.drawable.upvote_48 : R.drawable.upvote_48_black;
            int resIdD = (post.isDisliked.equals("true")) ? R.drawable.downvote_48 : R.drawable.downvote_48_black;
            likeImg.setImageResource(resIdL);
            unlikeImg.setImageResource(resIdD);
            adapter.setComments(post.comment);

        } else {
            progressDialoge.show();
            requestData();
        }

    }

    private void initialize() {
        setContentView(R.layout.activity_individual_post);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        userId = (TextView) findViewById(R.id.userId);
        userPic = (ImageView) findViewById(R.id.userPic);
        nameDisplay = (TextView) findViewById(R.id.nameDisplay);
        contentText = (TextView) findViewById(R.id.contentText);
        likeText = (TextView) findViewById(R.id.likeText);
        unlikeText = (TextView) findViewById(R.id.unlikeText);
        likeImg = (ImageView) findViewById(R.id.likeImg);
        unlikeImg = (ImageView) findViewById(R.id.unlikeImg);
        commentCount = (TextView) findViewById(R.id.commentCount);

        dataStore = new DataStore(IndivisualPost.this);
        pda= FeedsAdapter.getInstance(this);
        progressDialoge = new ProgressDialog(this);
        progressDialoge.setIndeterminate(true);
        progressDialoge.setMessage("Crunching latest data...");
        progressDialoge.setCancelable(false);


        Bundle bucket = getIntent().getExtras();
        userId.setText(bucket.getString("uid"));
        nameDisplay.setText(bucket.getString("username"));
        contentText.setText(bucket.getString("content"));
        likeText.setText(bucket.getString("lc"));
        unlikeText.setText(bucket.getString("dc"));
        commentCount.setText(bucket.getString("comments"));
        toolbar.setTitle(bucket.getString("username") + "`s Post");
        int resIdL = (bucket.getString("isLiked").equals("true")) ? R.drawable.upvote_48 : R.drawable.upvote_48_black;
        int resIdD = (bucket.getString("isDisliked").equals("true")) ? R.drawable.downvote_48 : R.drawable.downvote_48_black;
        likeImg.setImageResource(resIdL);
        unlikeImg.setImageResource(resIdD);
        position = Integer.parseInt(bucket.getString("position"));
        post = new CollegarePost(
                bucket.getString("postid"),
                bucket.getString("content"),
                bucket.getString("username"),
                bucket.getString("doc"),
                bucket.getString("groupid"),
                bucket.getString("id"),
                bucket.getString("weight"),
                bucket.getString("pollid"),
                bucket.getString("lc"),
                bucket.getString("dc"),
                bucket.getString("isLiked"),
                bucket.getString("isDisliked"),
                null
        );

        likeImg.setOnClickListener(this);
        unlikeImg.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        adapter = new CommentsAdapter(this);
        comments = (RecyclerView) findViewById(R.id.commentHolder);
        comments.setLayoutManager(new LinearLayoutManager(this));
        comments.addItemDecoration(new RecyclerViewDecorator(this, 5, true, R.drawable.post_divider));
        comments.setAdapter(adapter);

    }

    @Override
    public void onPause(){
        Log.e("IP", "onPause()");
        super.onPause();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("IP","thread dispatched");
                ((UpdateListener) pda).update(post, position);
            }
        },2000);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        CollegareUser curr = DatabaseManager.getInstance(this).getUser();
        switch (id) {

            // case "likeBtn":
            case R.id.likeImg:
                if (InternetManager.getInstance(this).isConnectedToNet()) {

                    if (post.isDisliked.equals("true")) {
                        post.isDisliked = "false";
                        post.DisLikeCount = String.valueOf(Integer.parseInt(post.DisLikeCount) - 1);

                        unlikeImg.setImageResource(R.drawable.downvote_48_black);
                        unlikeText.setText(post.DisLikeCount);

                        backoff(post.postid, curr.id, curr.token);
                    } else if (post.isLiked.matches("false")) {
                        post.isLiked = "true";
                        post.LikeCount = String.valueOf(Integer.parseInt(post.LikeCount) + 1);

                        likeImg.setImageResource(R.drawable.upvote_48);
                        likeText.setText(post.LikeCount);

                        like(post.postid, curr.id, curr.token);
                    }

                } else {
                    Snackbar.make(commentCount, "No Internet Connectivity", Snackbar.LENGTH_LONG).show();
                }
                break;

            //  case "unlikeBtn":
            case R.id.unlikeImg:

                if (InternetManager.getInstance(this).isConnectedToNet()) {

                    if (post.isLiked.matches("true")) {
                        post.isLiked = "false";
                        post.LikeCount = String.valueOf(Integer.parseInt(post.LikeCount) - 1);

                        likeImg.setImageResource(R.drawable.upvote_48_black);
                        likeText.setText(post.LikeCount);

                        backoff(post.postid, curr.id, curr.token);
                    } else if (post.isDisliked.matches("false")) {
                        post.isDisliked = "true";
                        post.DisLikeCount = String.valueOf(Integer.parseInt(post.DisLikeCount) + 1);

                        unlikeImg.setImageResource(R.drawable.downvote_48);
                        unlikeText.setText(post.DisLikeCount);

                        dislike(post.postid, curr.id, curr.token);
                    }

                } else {
                    Snackbar.make(contentText, "No Internet Connectivity", Snackbar.LENGTH_LONG).show();
                }


        }

    }

}
