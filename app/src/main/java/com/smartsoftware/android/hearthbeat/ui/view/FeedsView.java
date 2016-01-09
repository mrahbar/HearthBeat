package com.smartsoftware.android.hearthbeat.ui.view;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.ui.ActivityView;
import com.smartsoftware.android.hearthbeat.ui.ScreenContainer;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:32
 * Email: mrahbar.azad@gmail.com
 */
public class FeedsView implements ActivityView {

    private ScreenContainer screenContainer;
    private FeedsViewListener listener;

    @Bind(R.id.main_deck_layout)
    CoordinatorLayout layout;

    public interface FeedsViewListener {
        void bindViews(FeedsView view);
        void onLaunchCollectionActivity();
        Resources getResources();
    }

    public FeedsView(FeedsViewListener listener) {
        this.listener = listener;
        screenContainer = new ScreenContainer();
    }

    public void bind(BaseActivity activity) {
        ViewGroup viewContainer = screenContainer.bind(activity);
        activity.getLayoutInflater().inflate(getLayout(), viewContainer);
        listener.bindViews(this);
    }

    public int getLayout() {
        return R.layout.activity_feed;
    }

    @OnClick(R.id.main_deck_add)
    public void onClickFab() {
        listener.onLaunchCollectionActivity();
    }

    public void showProgressView() {
        screenContainer.changeProgressVisibility(true);
    }

    public void hideProgressView() {
        screenContainer.changeProgressVisibility(false);
    }

    public void showMessage(@StringRes int msgId) {
        Snackbar.make(layout, msgId, Snackbar.LENGTH_LONG).show();
    }
}
