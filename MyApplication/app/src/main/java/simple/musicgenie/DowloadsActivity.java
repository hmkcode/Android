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
        //color filter to tab 0
//        int leftTabIconColor = ContextCompat.getColor(DowloadsActivity.this, R.color.White);
//        tabLayout.getTabAt(0).getIcon().setColorFilter(leftTabIconColor, PorterDuff.Mode.SRC_IN);
//        // color filter to tab1
//        int rightTabIconColor = ContextCompat.getColor(DowloadsActivity.this, R.color.TabUnselectionColor);
//        tabLayout.getTabAt(1).getText().set
//        tabLayout.getTabAt(1).getIcon().setColorFilter(rightTabIconColor, PorterDuff.Mode.SRC_IN);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
//                int tabIconColor = ContextCompat.getColor(DowloadsActivity.this, R.color.White);
//                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void log(String msg){
        Log.d("DownloadsActivity", msg);
    }

}
