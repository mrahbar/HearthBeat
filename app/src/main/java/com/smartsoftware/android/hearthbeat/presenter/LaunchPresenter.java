package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;

import com.smartsoftware.android.hearthbeat.main.LaunchActivity;
import com.smartsoftware.android.hearthbeat.main.MainActivity;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.view.LaunchView;

import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 20.10.2015
 * Time: 16:23
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchPresenter implements LaunchView.LaunchViewListener {

    private LaunchActivity activity;

    public LaunchPresenter(LaunchActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onLaunchMainScreen() {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    public void setContentView(int id) {
        activity.setContentView(id);
    }

    @Override
    public void bindViews(LaunchView view) {
        ButterKnife.bind(view, activity);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return activity.getLayoutInflater();
    }

    @Override
    public Resources getResources() {
        return activity.getResources();
    }

    @Override
    public Prefs getPrefs() {
        return Prefs.with(activity);
    }
}
