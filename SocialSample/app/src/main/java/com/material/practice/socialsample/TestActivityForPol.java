package com.material.practice.socialsample;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TestActivityForPol extends AppCompatActivity {

    ListView op;
    PollOptionsAdapter adapter;
    ListView optionsListView;
    ImageView addBtn;
    PollOptionsEditListAdapter adapterEdit;
    EditText inputBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_card);




        op= (ListView) findViewById(R.id.Polloptions);
        adapter= new PollOptionsAdapter(this);
        adapter.addPollOption(new CollegarePollOption("12","Ankit Kumar","1"));
        adapter.addPollOption(new CollegarePollOption("5","Rajesh Kumar","1"));
        // these steps to be done in FeedsAdapter
        // only for test
        RelativeLayout lockHolder= (RelativeLayout) findViewById(R.id.lockHolder);
        final ImageView lock = (ImageView) findViewById(R.id.lock);
        lockHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getSelected()==-1)return;
                lock.setImageResource(R.drawable.lock_pressed);
                adapter.show();

            }
        });
        op.setAdapter(adapter);



/*

        optionsListView= (ListView) findViewById(R.id.pollOptionList);
        addBtn= (ImageView) findViewById(R.id.addBtn);
        adapterEdit = new PollOptionsEditListAdapter(this);
        inputBox= (EditText) findViewById(R.id.optionInputBox);
        optionsListView.setAdapter(adapterEdit);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("polltest", "" + inputBox.getText().toString());
                if (!inputBox.getText().toString().trim().isEmpty()) {
                    adapterEdit.add(inputBox.getText().toString());
                    inputBox.setText("");
                }
            }
        });



*/



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
