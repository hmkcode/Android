package simple.musicgenie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Ankit on 9/25/2016.
 */
public class UserPreferenceSetting extends AppCompatActivity{
    TextView trendingTxtView;
    TextView thumbnailTxtView;
    TextView issueTxtView;
    TextView issueBtnTxt;
    Switch trendingSwitch;
    Switch thumbnailSwitch;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference_setting);
        // initializes all view components
        init();
        // setsToolbar
        setUpToolbar();
        // loads settings
        loadSettings();
        // attach listeners to setting widgets
        attachListeners();
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void attachListeners() {
        trendingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                SharedPrefrenceUtils utils = SharedPrefrenceUtils.getInstance(UserPreferenceSetting.this);
                if (state) {
                    utils.setOptionsForTrendingAudio(true);
                } else {
                    utils.setOptionsForTrendingAudio(false);
                }
            }
        });


        thumbnailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                SharedPrefrenceUtils  utils = SharedPrefrenceUtils.getInstance(UserPreferenceSetting.this);
                if(state){
                    utils.setOptionsForThumbnailLoad(true);
                }else{
                    utils.setOptionsForThumbnailLoad(false);
                }
            }
        });

        issueBtnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: open email intent with our support email-id as TO field
            }
        });

    }

    private void loadSettings() {
        SharedPrefrenceUtils utils = SharedPrefrenceUtils.getInstance(this);

        // get trending choice
        trendingSwitch.setChecked(utils.getOptionForTrendingAudio());

        // get thumbnail choice
        thumbnailSwitch.setChecked(utils.getOptionsForThumbnailLoad());
    }

    private void init() {

        trendingTxtView = (TextView) findViewById(R.id.loadTrendingTextMessage);
        thumbnailTxtView = (TextView) findViewById(R.id.loadThumbnailTextMessage);
        issueTxtView= (TextView) findViewById(R.id.issuesTextMessage);
        issueBtnTxt = (TextView) findViewById(R.id.issueBtn);
        trendingSwitch = (Switch) findViewById(R.id.loadTrendingSwitch);
        thumbnailSwitch = (Switch) findViewById(R.id.loadThumbnailSwitch);


        Typeface tf = FontManager.getInstance(this).getTypeFace(FontManager.FONT_RALEWAY_REGULAR);
        Typeface materialIconFont = FontManager.getInstance(this).getTypeFace(FontManager.FONT_MATERIAL);
        trendingTxtView.setTypeface(tf);
        thumbnailTxtView.setTypeface(tf);
        issueTxtView.setTypeface(tf);
        issueBtnTxt.setTypeface(materialIconFont);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_preference_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dn) {
            Intent i= new Intent(UserPreferenceSetting.this,DowloadsActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
