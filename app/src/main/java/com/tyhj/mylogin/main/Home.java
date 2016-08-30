package com.tyhj.mylogin.main;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;
import com.tyhj.mylogin.R;

import waveNavigation.MyMenuFragment;
import custom.StatusBarUtil;
import mAdapter.HomeViewPage;
import waveNavigation.FlowingView;
import waveNavigation.LeftDrawerLayout;

public class Home extends FragmentActivity {
    private LeftDrawerLayout mLeftDrawerLayout;
    private LinearLayout llStyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        llStyle= (LinearLayout) findViewById(R.id.llStyle);
        llStyle.setBackgroundResource(R.mipmap.appstyle1);
        initdrawerLayout();
        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.vpTab);
        pager.setAdapter(new HomeViewPage(getSupportFragmentManager()));
        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        ImageView ivOpenMenu= (ImageView) findViewById(R.id.ivOpenMenu);
        ivOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLeftDrawerLayout.isShownMenu()) {
                    mLeftDrawerLayout.openDrawer();
                }
            }
        });
    }

    private void initdrawerLayout() {
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        MyMenuFragment mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);
    }
    @Override
    public void onBackPressed() {
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        }
    }
}
