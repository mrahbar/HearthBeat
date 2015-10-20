package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.model.ApiCard;
import com.smartsoftware.android.hearthbeat.model.ApiCardback;
import com.smartsoftware.android.hearthbeat.model.Cardback;
import com.smartsoftware.android.hearthbeat.model.ApiHearthStoneCards;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.persistance.PrefKeys;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.presenter.LaunchPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:13
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchActivity extends BaseActivity implements LaunchPresenter.LaunchPresenterListener {

    @Inject Prefs prefs;
    @Inject HearthStoneApiService apiService;
    @Inject DatabaseGateway databaseGateway;
    @Inject Gson gson;
    private LaunchPresenter launchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApp().getApplicationComponent().inject(this);
        launchPresenter = new LaunchPresenter(this, prefs);
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }

    @Override
    public void onStartDownload(String locale) {
        Log.v("LaunchActivity", "Download started");
        call(locale);
    }

    private void call(String locale) {
        Observable.zip(apiService.getCards(locale), apiService.getCardbacks(locale),
                (hearthStoneApiCards, cardbacks) -> store(hearthStoneApiCards, cardbacks))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> onDownloadFinished(), e -> onDownloadError(e));
    }

    private Void store(ApiHearthStoneCards cards, List<ApiCardback> cardbacks) {
        Log.v("LaunchActivity", "Download storing");
        databaseGateway.open(LaunchActivity.class, this);
        databaseGateway.execute(LaunchActivity.class, () -> {
            Observable.from(cardbacks)
                    .forEach(apiCardback -> databaseGateway.store(LaunchActivity.class, apiCardback.toModel()));

            Observable.from(cards.toList())
                    .filter(ApiCard::isCollectible)
                    .forEach(apiCard -> databaseGateway.store(LaunchActivity.class, apiCard.toModel()));
        });
        databaseGateway.close(LaunchActivity.class);
        return null;
    }

    private void onDownloadFinished() {
        Log.v("LaunchActivity", "Download finished");
        prefs.save(PrefKeys.SETUP_FINISHED, true);
        launchPresenter.onLaunchMainScreen();
    }

    private void onDownloadError(Throwable e) {
        e.printStackTrace();
        launchPresenter.onDownloadFailed();
    }
}
