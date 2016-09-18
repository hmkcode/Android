
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