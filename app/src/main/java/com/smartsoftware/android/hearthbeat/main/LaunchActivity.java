package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.smartsoftware.android.hearthbeat.api.DownloadCardsCommand;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.model.ApiCard;
import com.smartsoftware.android.hearthbeat.model.ApiCardback;
import com.smartsoftware.android.hearthbeat.model.ApiHearthStoneCards;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.persistance.PrefKeys;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.presenter.LaunchPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:13
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchActivity extends BaseActivity implements LaunchPresenter.LaunchPresenterListener, DownloadCardsCommand.CallListener {

    @Inject Prefs prefs;
    @Inject HearthStoneApiService apiService;
    @Inject DatabaseGateway databaseGateway;
    private LaunchPresenter launchPresenter;
    private DownloadCardsCommand downloadCardsCommand;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchPresenter = new LaunchPresenter(this, prefs);
        downloadCardsCommand = new DownloadCardsCommand(apiService, databaseGateway, this);
        downloadCardsCommand.setCallListener(this);
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
    public void onStartDownload(String locale) {
        Log.v("LaunchActivity", "Download started");
        prefs.save(PrefKeys.LANG_CODE, locale);
        downloadCardsCommand.call(locale);
    }

    public void onDownloadFinished() {
        Log.v("LaunchActivity", "Download finished");
        launchPresenter.onLaunchMainScreen();
    }

    public void onDownloadError(Throwable e) {
        e.printStackTrace();
        launchPresenter.onDownloadFailed();
    }
}
