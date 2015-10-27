package com.smartsoftware.android.hearthbeat.ui;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:43
 * Email: mrahbar.azad@gmail.com
 */
public class ScreenContainer {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.activity_content)
    ViewGroup container;

    private ActionBarDrawerToggle drawerToggle;

    public ViewGroup bind(BaseActivity activity) {
        activity.setContentView(R.layout.base_activity);
        ButterKnife.bind(this, activity);
        setupDrawerLayout(activity);
        initToolbar(activity);
        return container;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    private void setupDrawerLayout(final BaseActivity activity) {
        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setStatusBarBackground(R.color.colorToolbar);
    }

    private void initToolbar(BaseActivity activity) {
        activity.setSupportActionBar(toolbar);
        final ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
