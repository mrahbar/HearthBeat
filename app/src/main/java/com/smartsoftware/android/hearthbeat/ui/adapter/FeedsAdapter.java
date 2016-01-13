package com.smartsoftware.android.hearthbeat.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.data.DataLoadingSubject;
import com.smartsoftware.android.hearthbeat.ui.feed.FeedPost;
import com.smartsoftware.android.hearthbeat.ui.feed.RedditPost;
import com.smartsoftware.android.hearthbeat.ui.feed.TwitchPost;
import com.smartsoftware.android.hearthbeat.ui.widget.CardViewAware;
import com.smartsoftware.android.hearthbeat.utils.ImageLoaderUtils;

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
    private static final int TYPE_TWITCH_POST = 1;
    private static final int TYPE_LOADING_MORE = -1;

    private List<FeedPost> items;
    private LayoutInflater layoutInflater;
    private DataLoadingSubject dataLoading;
    private FeedAdapterListener listener;

    public interface FeedAdapterListener {
        void openURL(String url);
    }

    public FeedsAdapter(List<FeedPost> items, DataLoadingSubject dataLoading, FeedAdapterListener listener) {
        this.items = items;
        this.dataLoading = dataLoading;
        this.listener = listener;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private FeedPost getItem(int position) {
        return items.get(position);
    }

    public void updatePosts(List<FeedPost> posts) {
        int index;
        for (FeedPost post : posts) {
            index = items.indexOf(post);
            if (index == -1) {
                items.add(post);
            } else {
                items.set(index, post);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() && getItemCount() > 0) {
            FeedPost item = getItem(position);
            if (item instanceof RedditPost) {
                return TYPE_REDDIT_POST;
            }
            else if (item instanceof TwitchPost) {
                return TYPE_TWITCH_POST;
            }
        }
        return TYPE_LOADING_MORE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_REDDIT_POST:
                return createRedditPostHolder(parent);
            case TYPE_TWITCH_POST:
                return createTwitchPostHolder(parent);
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
                bindRedditPost((RedditPost) getItem(position), (RedditPostViewHolder) holder);
                break;
            case TYPE_TWITCH_POST:
                bindTwitchPost((TwitchPost) getItem(position), (TwitchPostViewHolder) holder);
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

    private void bindRedditPost(final RedditPost post, final RedditPostViewHolder holder) {
        holder.title.setText(post.title);
        holder.comments.setText(String.valueOf(post.num_comments));
        holder.score.setText(String.valueOf(post.score));
        holder.itemView.setOnClickListener(v -> {
            listener.openURL(post.url);
        });
    }

    @NonNull
    private TwitchPostViewHolder createTwitchPostHolder(ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.feed_post_twitch, parent, false);
        return new TwitchPostViewHolder(view);
    }

    private void bindTwitchPost(TwitchPost post, TwitchPostViewHolder holder) {
        CardViewAware viewAware = new CardViewAware(holder.preview, holder.preview.getMeasuredWidth(), holder.preview.getMeasuredHeight());
        ImageLoader.getInstance().displayImage(post.preview, viewAware, ImageLoaderUtils.PREVIEW_THUMBNAIL_NORMAL);
        holder.name.setText(post.displayName);
        holder.title.setText(post.title);
        holder.viewers.setText(String.valueOf(post.viewers));
        holder.itemView.setOnClickListener(v -> {
            listener.openURL(post.url);
        });
    }

    private void bindLoadingViewHolder(LoadingMoreHolder holder) {
        // only show the infinite load progress spinner if there are already items in the
        // grid i.e. it's not the first item & data is being loaded
        holder.progress.setVisibility((holder.getAdapterPosition() > 0
                && dataLoading.isDataLoading()) ? View.VISIBLE : View.INVISIBLE);
    }

    /* package */ class RedditPostViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.reddit_post_title) TextView title;
        @Bind(R.id.reddit_post_comments) TextView comments;
        @Bind(R.id.reddit_post_score) TextView score;

        public RedditPostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /* package */ class TwitchPostViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.twitch_post_preview) ImageView preview;
        @Bind(R.id.twitch_post_name) TextView name;
        @Bind(R.id.twitch_post_title) TextView title;
        @Bind(R.id.twitch_post_viewers) TextView viewers;

        public TwitchPostViewHolder(View itemView) {
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
