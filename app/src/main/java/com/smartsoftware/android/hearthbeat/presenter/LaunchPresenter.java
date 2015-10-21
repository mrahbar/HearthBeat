package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
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

    private BaseActivity activity;
    private Prefs prefs;
    private LaunchView launchView;
    private LaunchPresenterListener listener;

    public interface LaunchPresenterListener {
        void onStartDownload(String locale);
        BaseActivity getActivity();
    }

    public LaunchPresenter(LaunchPresenterListener listener, Prefs prefs) {
        this.listener = listener;
        this.activity = listener.getActivity();
        this.prefs = prefs;
        launchView = new LaunchView(this);
    }

    @Override
    public void onLaunchMainScreen() {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    public void onStartDownload(String locale) {
        listener.onStartDownload(locale);
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
        return prefs;
    }

    public void onDownloadFailed() {
        launchView.onDownloadFailed();
    }
}
