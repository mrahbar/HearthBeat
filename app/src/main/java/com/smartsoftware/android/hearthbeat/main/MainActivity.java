package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.presenter.DecksPresenter;
import com.smartsoftware.android.hearthbeat.presenter.LaunchPresenter;

public class MainActivity extends BaseActivity implements DecksPresenter.DecksPresenterListener {

    private DecksPresenter decksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApp().getApplicationComponent().inject(this);
        decksPresenter = new DecksPresenter(this);
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }
}
