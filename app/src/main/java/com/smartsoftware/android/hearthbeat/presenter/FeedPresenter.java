package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.api.DownloadCardsCommand;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.api.RedditApiService;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.main.CollectionActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.persistance.PrefKeys;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.ui.view.FeedsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:36
 * Email: mrahbar.azad@gmail.com
 */
public class FeedPresenter extends BasePresenter implements FeedsView.FeedsViewListener {

    @Inject Prefs prefs;
    @Inject HearthStoneApiService hearthStoneApiService;
    @Inject RedditApiService redditApiService;
    private FeedsView feedsView;
    private FeedsListener listener;

    public interface FeedsListener {
        BaseActivity getActivity();
    }

    public FeedPresenter(FeedsListener listener, ApplicationComponent applicationComponent) {
        this.listener = listener;
        applicationComponent.inject(this);

        feedsView = new FeedsView(this, applicationComponent);
        feedsView.bind(getActivity());
    }

    private BaseActivity getActivity() {
        return listener.getActivity();
    }

    @Override
    public void bindViews(FeedsView view) {
        ButterKnife.bind(view, getActivity());
    }

    @Override
    public void onLaunchCollectionActivity() {
        List<Card> cards = SQLite.select()
                .from(Card.class)
                .queryList();

        if (cards.size() == 0) {
            new MaterialDialog.Builder(getActivity())
                    .content(R.string.launch_download_now)
                    .positiveText(android.R.string.yes)
                    .negativeText(android.R.string.no)
                    .cancelable(true)
                    .onPositive((materialDialog, dialogAction) -> startCardDownload())
                    .show();
        } else {
            getActivity().startActivity(new Intent(getActivity(), CollectionActivity.class));
        }
    }

    @Override
    public void onRefreshFeed() {
        redditApiService.getPopularSubmissions()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    //TODO convert to proper model
                });
    }

    private void startCardDownload() {
        final String locale = prefs.getString(PrefKeys.LANG_CODE, null);

        if (!TextUtils.isEmpty(locale)) {
            feedsView.showProgressView();
            DownloadCardsCommand downloadCardsCommand = new DownloadCardsCommand(hearthStoneApiService);
            downloadCardsCommand.setCallListener(new DownloadCardsCommand.CallListener() {
                @Override
                public void onDownloadFinished() {
                    feedsView.hideProgressView();
                    Toast.makeText(getActivity(), R.string.launch_download_finished, Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), CollectionActivity.class));
                }

                @Override
                public void onDownloadError(Throwable e) {
                    feedsView.hideProgressView();
                    feedsView.showMessage(R.string.launch_download_failed);
                }
            });
            downloadCardsCommand.call(locale);
        }
    }
}
