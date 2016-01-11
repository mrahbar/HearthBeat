package com.smartsoftware.android.hearthbeat.ui.adapter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.data.DataLoadingSubject;
import com.smartsoftware.android.hearthbeat.ui.feed.FeedPost;
import com.smartsoftware.android.hearthbeat.ui.feed.RedditPost;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 23:27
 * Email: mrahbar.azad@gmail.com
 */
public class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_REDDIT_POST = 0;
    private static final int TYPE_LOADING_MORE = -1;

    private List<FeedPost> items;
    private LayoutInflater layoutInflater;
    private DataLoadingSubject dataLoading;

    public FeedsAdapter(List<FeedPost> items, LayoutInflater layoutInflater, DataLoadingSubject dataLoading) {
        this.items = items;
        this.layoutInflater = layoutInflater;
        this.dataLoading = dataLoading;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private FeedPost getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() && getItemCount() > 0) {
            FeedPost item = getItem(position);
            if (item instanceof RedditPost) {
                return TYPE_REDDIT_POST;
            }
        }
        return TYPE_LOADING_MORE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_REDDIT_POST:
                return createRedditPostHolder(parent);
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(
                        layoutInflater.inflate(R.layout.infinite_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_REDDIT_POST:
                bindReditPost((RedditPost) getItem(position), (RedditPostViewHolder) holder);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) holder);
                break;
        }
    }

    @NonNull
    private RedditPostViewHolder createRedditPostHolder(ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.feed_post_reddit, parent, false);
        return new RedditPostViewHolder(view);
    }

    private void bindReditPost(final RedditPost post, final RedditPostViewHolder holder) {
        holder.title.setText(post.title);
        holder.comments.setText(String.valueOf(post.num_comments));
        holder.score.setText(String.valueOf(post.score));
    }

    private void bindLoadingViewHolder(LoadingMoreHolder holder) {
        // only show the infinite load progress spinner if there are already items in the
        // grid i.e. it's not the first item & data is being loaded
        holder.progress.setVisibility((holder.getAdapterPosition() > 0
                && dataLoading.isDataLoading()) ? View.VISIBLE : View.INVISIBLE);
    }

    /* package */ class RedditPostViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.story_title) TextView title;
        @Bind(R.id.story_comments) TextView comments;
        @Bind(R.id.story_score) TextView score;

        public RedditPostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /* package */ class LoadingMoreHolder extends RecyclerView.ViewHolder {

        ProgressBar progress;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progress = (ProgressBar) itemView;
        }

    }
}
