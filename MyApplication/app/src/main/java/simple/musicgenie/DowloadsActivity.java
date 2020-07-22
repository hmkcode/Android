package simple.musicgenie;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by Ankit on 9/25/2016.
 */

public class DowloadsActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    DownloadTab tabLayout;
    Toolbar toolbar;

    //TODO: make active download page and downloaded items page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dowload);
        tabLayout = (DownloadTab) findViewById(R.id.tab_layout);
        tabLayout.setUpTab(tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                int tabIconColor = ContextCompat.getColor(DowloadsActivity.this, R.color.TabUnselectionColor);
//                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Downloads");
        //TODO: handle diff sdk versions
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        log("tasks >" + SharedPrefrenceUtils.getInstance(this).getTaskSequence());
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unRegisterBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.getInstance(this).setFirstPageLoadedStatus(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//
//        Intent intent= new Intent(DowloadsActivity.this,MainActivity.class);
//        overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
        finish();
    }

    public void log(String msg){
        Log.d("DownloadsActivity",msg);
    }

}
