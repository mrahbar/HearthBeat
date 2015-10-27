package com.smartsoftware.android.hearthbeat.ui.view;

import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.ScreenContainer;
import com.smartsoftware.android.hearthbeat.ui.adapter.RecyclerViewAdapter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindInt;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:32
 * Email: mrahbar.azad@gmail.com
 */
public class DeckBuilderView implements RecyclerViewAdapter.InteractionListener {

    @BindInt(R.integer.staggered_grid_columns) int spanCount;

    @Bind(R.id.deckbuilder_recyclerview) RecyclerView recyclerView;

    private ScreenContainer screenContainer;
    private DeckBuilderViewListener listener;
    private ViewGroup viewContainer;


    public interface DeckBuilderViewListener {
        void bindViews(DeckBuilderView view);
        Resources getResources();
    }

    public DeckBuilderView(DeckBuilderViewListener listener) {
        this.listener = listener;
        screenContainer = new ScreenContainer();
    }

    public void bind(BaseActivity activity, Map<String, Collection<Card>> cards) {
        viewContainer = screenContainer.bind(activity);
        activity.getLayoutInflater().inflate(R.layout.activity_deckbuilder, viewContainer);
        listener.bindViews(this);
        initializeRecyclerView(cards);
    }

    private void initializeRecyclerView(Map<String, Collection<Card>> cards) {
        int width = listener.getResources().getDisplayMetrics().widthPixels/spanCount;
        int height = Math.round(1.5f * width);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(cards, new Point(width, height));
        adapter.setInteractionListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(),
                spanCount, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onCardClick(View cardView, Card card) {

    }

    @Override
    public void onCardLongClick(View v, Card card) {

    }
}
