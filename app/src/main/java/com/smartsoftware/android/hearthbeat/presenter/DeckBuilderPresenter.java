package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.main.DeckBuilderActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.view.DeckBuilderView;
import com.smartsoftware.android.hearthbeat.ui.view.DeckListView;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:36
 * Email: mrahbar.azad@gmail.com
 */
public class DeckBuilderPresenter implements DeckBuilderView.DeckBuilderViewListener {

    private DeckBuilderView launchView;
    private DeckBuilderPresenterListener listener;

    public interface DeckBuilderPresenterListener {
        BaseActivity getActivity();
    }

    public DeckBuilderPresenter(DeckBuilderPresenterListener listener, Map<String, Collection<Card>> cards) {
        this.listener = listener;
        launchView = new DeckBuilderView(this);
        launchView.bind(listener.getActivity(), cards);
    }

    private BaseActivity getActivity() {
        return listener.getActivity();
    }

    @Override
    public void bindViews(DeckBuilderView view) {
        ButterKnife.bind(view, getActivity());
    }

    @Override
    public Resources getResources() {
        return getActivity().getResources();
    }
}
