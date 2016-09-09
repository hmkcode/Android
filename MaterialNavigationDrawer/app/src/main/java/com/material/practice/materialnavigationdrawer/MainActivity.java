package com.material.practice.materialnavigationdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        setUpNavigationDrawer();

    }

    public void initializeComponents() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        TextView fontIcon = (TextView) findViewById(R.id.sendIconText);
        fontIcon.setTypeface(FontManager.getInstance(this).getTypeFace(FontManager.FONT_FLATICON));

    }

    public void setUpNavigationDrawer() {

        NavigationFragment navigationFragment = new NavigationFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentNav,navigationFragment)
                .commit();

    }

}
