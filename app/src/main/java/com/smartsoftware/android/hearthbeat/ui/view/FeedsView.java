package com.smartsoftware.android.hearthbeat.ui.view;

import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.data.DataManager;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.ui.ActivityView;
import com.smartsoftware.android.hearthbeat.ui.ScreenContainer;
import com.smartsoftware.android.hearthbeat.ui.adapter.FeedsAdapter;
import com.smartsoftware.android.hearthbeat.ui.recyclerview.InfiniteScrollListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindInt;
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
    private LayoutInflater layoutInflater;

    @Bind(R.id.main_deck_layout)
    CoordinatorLayout layout;

    @Bind(R.id.feeds_grid)
    RecyclerView grid;

    @BindInt(R.integer.num_columns)
    int columns;

    @Inject
    DataManager dataManager;

    public interface FeedsViewListener {
        void bindViews(FeedsView view);
        void onLaunchCollectionActivity();
        void onRefreshFeed();
    }

    public FeedsView(FeedsViewListener listener, ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
        this.listener = listener;
        screenContainer = new ScreenContainer();
    }

    public void bind(BaseActivity activity) {
        ViewGroup viewContainer = screenContainer.bind(activity);
        layoutInflater = activity.getLayoutInflater();
        layoutInflater.inflate(getLayout(), viewContainer);
        listener.bindViews(this);
        initializeToolbar();
        initializeGrid();
    }

    private void initializeToolbar() {
        final Toolbar toolbar = screenContainer.getToolbar();
        toolbar.inflateMenu(R.menu.feeds);
        toolbar.setOnMenuItemClickListener(item -> {
            listener.onRefreshFeed();
            return true;
        });
    }

    private void initializeGrid() {
        FeedsAdapter adapter = new FeedsAdapter(new ArrayList<>(), layoutInflater, dataManager);

        grid.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(grid.getContext(), columns);
        grid.setLayoutManager(layoutManager);
        grid.addOnScrollListener(gridScroll);
        grid.addOnScrollListener(new InfiniteScrollListener(layoutManager, dataManager) {
            @Override
            public void onLoadMore() {
                //TODO forward to DataManager
            }
        });
        grid.setHasFixedSize(true);
//        grid.addItemDecoration(new GridItemDividerDecoration(adapter.getDividedViewHolderClasses(),
//                this, R.dimen.divider_height, R.color.divider));
//        grid.setItemAnimator(new HomeGridItemAnimator());
    }

    private int gridScrollY = 0;
    private RecyclerView.OnScrollListener gridScroll = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            gridScrollY += dy;
            final Toolbar toolbar = screenContainer.getToolbar();
            if (gridScrollY > 0 && ViewCompat.getTranslationZ(toolbar) != -1f) {
                ViewCompat.setTranslationZ(toolbar, -1f);
            } else if (gridScrollY == 0 && ViewCompat.getTranslationZ(toolbar) != 0) {
                ViewCompat.setTranslationZ(toolbar, 0);
            }
        }
    };

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
