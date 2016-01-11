package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.presenter.FeedPresenter;

import javax.inject.Inject;

public class FeedActivity extends BaseActivity implements FeedPresenter.FeedsListener {

    @Inject HearthStoneApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new FeedPresenter(this, getApplicationComponent());
    }

    @Override
    public void injectActivity(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }
}
