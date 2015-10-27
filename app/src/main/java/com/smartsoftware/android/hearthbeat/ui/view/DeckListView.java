package com.smartsoftware.android.hearthbeat.ui.view;

import android.content.res.Resources;
import android.view.ViewGroup;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.ui.ScreenContainer;

import butterknife.OnClick;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:32
 * Email: mrahbar.azad@gmail.com
 */
public class DeckListView {

    private ScreenContainer screenContainer;
    private DeckListViewListener listener;
    private ViewGroup viewContainer;

    public interface DeckListViewListener {
        void bindViews(DeckListView view);
        void onLaunchDeckBuilder();
        Resources getResources();
    }

    public DeckListView(DeckListViewListener listener) {
        this.listener = listener;
        screenContainer = new ScreenContainer();
    }

    public void bind(BaseActivity activity) {
        viewContainer = screenContainer.bind(activity);
        activity.getLayoutInflater().inflate(R.layout.activity_decklist, viewContainer);
        listener.bindViews(this);
    }

    @OnClick(R.id.main_deck_add)
    public void onClickFab() {
        listener.onLaunchDeckBuilder();
    }
}
