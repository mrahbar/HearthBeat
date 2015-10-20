package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.presenter.LaunchPresenter;
import com.smartsoftware.android.hearthbeat.view.LaunchView;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:13
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchActivity extends BaseActivity {

    private LaunchView view;
    private LaunchPresenter launchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchPresenter = new LaunchPresenter(this);
        view = new LaunchView(launchPresenter);
    }
}
