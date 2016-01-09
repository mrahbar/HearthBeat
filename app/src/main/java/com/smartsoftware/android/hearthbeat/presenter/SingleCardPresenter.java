package com.smartsoftware.android.hearthbeat.presenter;

import android.content.res.Resources;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.view.CollectionView;
import com.smartsoftware.android.hearthbeat.ui.view.SingleCardView;

import butterknife.ButterKnife;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:36
 * Email: mrahbar.azad@gmail.com
 */
public class SingleCardPresenter implements SingleCardView.SingleCardViewListener {

    private SingleCardPresenterListener listener;
    private SingleCardView view;

    public interface SingleCardPresenterListener {
        void onFinish();
        BaseActivity getActivity();
    }

    public SingleCardPresenter(SingleCardPresenterListener listener, CollectionView.CardIntentBundle intentBundle, Card card, boolean firstLaunch) {
        this.listener = listener;
        view = new SingleCardView(this, intentBundle, card, firstLaunch);
        view.bind(getActivity());
    }

    private BaseActivity getActivity() {
        return listener.getActivity();
    }

    @Override
    public void bindViews(SingleCardView view) {
        ButterKnife.bind(view, getActivity());
    }

    @Override
    public Resources getResources() {
        return getActivity().getResources();
    }

    public void finish() {
        view.finish();
    }

    @Override
    public void onFinish() {
        listener.onFinish();
    }
}
