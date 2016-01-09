package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.content.res.Resources;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.main.SingleCardActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.view.CollectionView;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:36
 * Email: mrahbar.azad@gmail.com
 */
public class CollectionPresenter implements CollectionView.DeckBuilderViewListener {

    private CollectionView view;
    private CollectionPresenterListener listener;

    public interface CollectionPresenterListener {
        BaseActivity getActivity();
    }
    public CollectionPresenter(CollectionPresenterListener listener, Map<String, List<Card>> cards) {
        this.listener = listener;
        view = new CollectionView(this, cards);
        view.bind(listener.getActivity());
    }

    public void onResume() {
        view.onResume();
    }

    private BaseActivity getActivity() {
        return listener.getActivity();
    }

    @Override
    public void bindViews(CollectionView view) {
        ButterKnife.bind(view, getActivity());
    }

    @Override
    public void showCard(CollectionView.CardIntentBundle cardBundle) {
        Intent intent = new Intent(getActivity(), SingleCardActivity.class);
        intent.putExtra(SingleCardActivity.EXTRA_BUNDLE, cardBundle.toBundle());
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onFinish() {
        getActivity().finish();
    }

    @Override
    public Resources getResources() {
        return getActivity().getResources();
    }
}
