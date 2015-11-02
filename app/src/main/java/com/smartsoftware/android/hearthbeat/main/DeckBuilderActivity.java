package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.presenter.DeckBuilderPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class DeckBuilderActivity extends BaseActivity implements DeckBuilderPresenter.DeckBuilderPresenterListener {

    @Inject DatabaseGateway databaseGateway;
    private DeckBuilderPresenter deckListPresenter;
    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApp().getApplicationComponent().inject(this);

        databaseGateway.open(DeckBuilderActivity.class, this);
        List<Card> cards = databaseGateway.queryList(DeckBuilderActivity.class, Card.class);

        String neutralClassName = getString(R.string.class_neutral);
        Observable.from(cards)
                .toMultimap((card -> !TextUtils.isEmpty(card.getPlayerClass()) ? card.getPlayerClass() : neutralClassName))
                .subscribe(collectionMap -> {
                    deckListPresenter = new DeckBuilderPresenter(DeckBuilderActivity.this, collectionMap);
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paused) {
            deckListPresenter.onResume();
            paused = false;
        }
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseGateway.close(DeckBuilderActivity.class);
    }
}
