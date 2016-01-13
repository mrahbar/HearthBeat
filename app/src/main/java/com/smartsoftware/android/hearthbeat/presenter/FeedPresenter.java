package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.api.DownloadCardsCommand;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.api.RedditApiService;
import com.smartsoftware.android.hearthbeat.api.TwitchApiService;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.main.CollectionActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.model.reddit.Submission;
import com.smartsoftware.android.hearthbeat.model.reddit.SubmissionResponse;
import com.smartsoftware.android.hearthbeat.model.twitch.Stream;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseManager;
import com.smartsoftware.android.hearthbeat.persistance.PrefKeys;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.smartsoftware.android.hearthbeat.ui.feed.FeedPost;
import com.smartsoftware.android.hearthbeat.ui.feed.RedditPost;
import com.smartsoftware.android.hearthbeat.ui.feed.TwitchPost;
import com.smartsoftware.android.hearthbeat.view.FeedsView;

import java.util.ArrayList;
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
    @Inject TwitchApiService twitchApiService;
    @Inject DatabaseManager sqlAdapter;
    private FeedsView feedsView;
    private FeedsListener listener;

    public interface FeedsListener {
        BaseActivity getActivity();
        void openURL(String url);
    }

    public FeedPresenter(FeedsListener listener, ApplicationComponent applicationComponent) {
        this.listener = listener;
        applicationComponent.inject(this);

        List<Submission> submissions = sqlAdapter.findAll(Submission.KEY_SUFFIX, Submission.class);
        List<Stream> streams = sqlAdapter.findAll(Stream.KEY_SUFFIX, Stream.class);
        List<FeedPost> posts = new ArrayList<>(submissions.size()+streams.size());

        for (Submission sub : submissions) {
            posts.add(new RedditPost(sub.getId(), sub.getTitle(), sub.getUrl(),
                    sub.getNum_comments(), sub.getScore(), sub.getThumbnail()));
        }

        for (Stream s : streams) {
            posts.add(new TwitchPost(String.valueOf(s.getId()), s.getChannel().getStatus(),
                    s.getChannel().getUrl(), s.getChannel().getDisplayName(),
                    s.getPreview().getMedium(), s.getViewers()));
        }

        feedsView = new FeedsView(this, applicationComponent, posts);
        feedsView.bind(getActivity());
    }

    private BaseActivity getActivity() {
        return listener.getActivity();
    }

    public void onCreateOptionsMenu(MenuInflater menuInflater, Menu menu) {
        feedsView.onCreateOptionsMenu(menuInflater, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return feedsView.onOptionsItemSelected(item);
    }

    @Override
    public void bindViews(FeedsView view) {
        ButterKnife.bind(view, getActivity());
    }

    @Override
    public void onLaunchCollectionActivity() {
        List<Card> cards = sqlAdapter.findAll(Card.KEY_SUFFIX, Card.class);

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
        feedsView.showMessage(R.string.feed_refreshing);
        redditApiService.getPopularSubmissions()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    List<FeedPost> posts = new ArrayList<>(r.data.children.size());
                    for (SubmissionResponse.DataChildren child : r.data.children) {
                        final Submission sub = child.data;
                        sqlAdapter.store(sub);
                        posts.add(new RedditPost(sub.getId(), sub.getTitle(), sub.getUrl(),
                                sub.getNum_comments(), sub.getScore(), sub.getThumbnail()));
                    }
                    feedsView.onRefreshFinished(posts);
                });

        twitchApiService.getLiveHearthStoneStreams()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    List<FeedPost> posts = new ArrayList<>(r.getStreams().size());
                    for (Stream s : r.getStreams()) {
                        sqlAdapter.store(s);
                        posts.add(new TwitchPost(String.valueOf(s.getId()), s.getChannel().getStatus(),
                                s.getChannel().getUrl(), s.getChannel().getDisplayName(),
                                s.getPreview().getMedium(), s.getViewers()));
                    }
                    feedsView.onRefreshFinished(posts);
                });

    }

    @Override
    public void openURL(String url) {
        listener.openURL(url);
    }

    private void startCardDownload() {
        final String locale = prefs.getString(PrefKeys.LANG_CODE, null);

        if (!TextUtils.isEmpty(locale)) {
            feedsView.showProgressView();
            DownloadCardsCommand downloadCardsCommand = new DownloadCardsCommand(hearthStoneApiService, sqlAdapter);
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
