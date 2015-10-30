package com.smartsoftware.android.hearthbeat.presenter;

import android.content.Intent;
import android.content.res.Resources;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.main.DeckBuilderActivity;
import com.smartsoftware.android.hearthbeat.main.SingleCardActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.view.DeckBuilderView;

import java.util.Collection;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:36
 * Email: mrahbar.azad@gmail.com
 */
public class DeckBuilderPresenter implements DeckBuilderView.DeckBuilderViewListener {

    private DeckBuilderView view;
    private DeckBuilderPresenterListener listener;

    public interface DeckBuilderPresenterListener {
        BaseActivity getActivity();
    }
    public DeckBuilderPresenter(DeckBuilderPresenterListener listener, Map<String, Collection<Card>> cards) {
        this.listener = listener;
        view = new DeckBuilderView(this, cards);
        view.bind(listener.getActivity());
    }

    public void onResume() {
        view.onResume();
    }

    private BaseActivity getActivity() {
        return listener.getActivity();
    }

    @Override
    public void bindViews(DeckBuilderView view) {
        ButterKnife.bind(view, getActivity());
    }

    @Override
    public void showCard(DeckBuilderView.CardIntentBundle cardBundle) {
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
