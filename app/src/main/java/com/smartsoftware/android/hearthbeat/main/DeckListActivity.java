package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.presenter.DeckListPresenter;

public class DeckListActivity extends BaseActivity implements DeckListPresenter.DecksPresenterListener {

    private DeckListPresenter deckListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApp().getApplicationComponent().inject(this);
        deckListPresenter = new DeckListPresenter(this);
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }
}
