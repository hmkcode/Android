package com.hmkcode.android;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.hmkcode.android.adapters.MyFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        setPagerAdapter();
        setTabLayout();
    }

    private void setPagerAdapter(){

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

    }

    private void setTabLayout() {

        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("First");
        tabLayout.getTabAt(1).setText("Second");
    }
}
