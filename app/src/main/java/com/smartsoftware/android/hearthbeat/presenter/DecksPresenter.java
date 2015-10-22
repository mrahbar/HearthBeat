package com.smartsoftware.android.hearthbeat.presenter;

import android.content.res.Resources;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.ui.view.DecksView;

import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:36
 * Email: mrahbar.azad@gmail.com
 */
public class DecksPresenter implements DecksView.DecksViewListener {

    private BaseActivity activity;
    private DecksView launchView;
    private DecksPresenterListener listener;

    public interface DecksPresenterListener {
        BaseActivity getActivity();
    }

    public DecksPresenter(DecksPresenterListener listener) {
        this.listener = listener;
        this.activity = listener.getActivity();
        launchView = new DecksView(this);
        launchView.bind(activity);
    }

    @Override
    public Resources getResources() {
        return activity.getResources();
    }
}
