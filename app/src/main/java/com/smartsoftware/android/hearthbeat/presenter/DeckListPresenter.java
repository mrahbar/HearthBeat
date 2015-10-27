package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.content.res.Resources;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.main.DeckBuilderActivity;
import com.smartsoftware.android.hearthbeat.ui.view.DeckListView;

import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:36
 * Email: mrahbar.azad@gmail.com
 */
public class DeckListPresenter implements DeckListView.DeckListViewListener {

    private DeckListView launchView;
    private DecksPresenterListener listener;

    public interface DecksPresenterListener {
        BaseActivity getActivity();
    }

    public DeckListPresenter(DecksPresenterListener listener) {
        this.listener = listener;
        launchView = new DeckListView(this);
        launchView.bind(getActivity());
    }

    private BaseActivity getActivity() {
        return listener.getActivity();
    }

    @Override
    public void bindViews(DeckListView view) {
        ButterKnife.bind(view, getActivity());
    }

    @Override
    public void onLaunchDeckBuilder() {
        getActivity().startActivity(new Intent(getActivity(), DeckBuilderActivity.class));
    }

    @Override
    public Resources getResources() {
        return getActivity().getResources();
    }
}
