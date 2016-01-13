package com.smartsoftware.android.hearthbeat.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.chrome.CustomTabActivityHelper;
import com.smartsoftware.android.hearthbeat.chrome.WebviewFallback;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.presenter.FeedPresenter;

import javax.inject.Inject;

public class FeedActivity extends BaseActivity implements CustomTabActivityHelper.ConnectionCallback, FeedPresenter.FeedsListener {

    @Inject HearthStoneApiService apiService;
    private FeedPresenter feedPresenter;
    private CustomTabActivityHelper customTabActivityHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedPresenter = new FeedPresenter(this, getApplicationComponent());
        customTabActivityHelper = new CustomTabActivityHelper();
        customTabActivityHelper.setConnectionCallback(this);
    }

    @Override
    public void injectActivity(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (customTabActivityHelper != null) {
            customTabActivityHelper.bindCustomTabsService(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (customTabActivityHelper != null) {
            customTabActivityHelper.unbindCustomTabsService(this);
        }
    }

    @Override
    public void openURL(String url) {
        Uri uri = Uri.parse(url);
        customTabActivityHelper.mayLaunchUrl(uri, null, null);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        intentBuilder.setShowTitle(true);

        CustomTabActivityHelper.openCustomTab(this, intentBuilder.build(),
                uri, new WebviewFallback());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        feedPresenter.onCreateOptionsMenu(getMenuInflater(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       return feedPresenter.onOptionsItemSelected(item);
    }

    @Override
    public void onCustomTabsConnected() {
    }

    @Override
    public void onCustomTabsDisconnected() {
    }
}
