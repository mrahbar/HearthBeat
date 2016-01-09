package com.smartsoftware.android.hearthbeat.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.ui.widget.AccessibleLinearLayout;

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

    private AccessibleLinearLayout progressView;

    public ViewGroup bind(BaseActivity activity) {
        activity.setContentView(R.layout.base_activity);
        ButterKnife.bind(this, activity);
        initToolbar(activity);
        setupDrawerLayout(activity);
        return container;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private void setupDrawerLayout(final BaseActivity activity) {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setStatusBarBackground(R.color.colorToolbar);

        drawerToggle.syncState();
    }

    private void initToolbar(BaseActivity activity) {
        activity.setSupportActionBar(toolbar);
        final ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    public void changeProgressVisibility(final boolean show) {
        if (progressView == null) {
            ViewStub viewStub = (ViewStub) drawerLayout.findViewById(R.id.activity_progress_stub);
            progressView = (AccessibleLinearLayout) viewStub.inflate();
            progressView.setStealTouchEvent(true);
        }

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate()
                .setDuration(drawerLayout.getResources().getInteger(android.R.integer.config_shortAnimTime))
                .alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
    }
}
