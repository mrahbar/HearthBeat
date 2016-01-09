package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.main.FeedActivity;
import com.smartsoftware.android.hearthbeat.persistance.PrefKeys;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.ui.view.LaunchView;

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
        prefs.save(PrefKeys.LAUNCH_VIEW_SHOWN, true);
        activity.startActivity(new Intent(activity, FeedActivity.class));
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
    public Context getContext() {
        return activity;
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
