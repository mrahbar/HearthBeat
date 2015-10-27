package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;
import android.text.TextUtils;

import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.presenter.DeckBuilderPresenter;
import com.smartsoftware.android.hearthbeat.presenter.DeckListPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.internal.operators.OperatorToMultimap;
import rx.observables.GroupedObservable;

public class DeckBuilderActivity extends BaseActivity implements DeckBuilderPresenter.DeckBuilderPresenterListener {

    private static final String NEUTRAL_CLASS = "Neutral";

    @Inject DatabaseGateway databaseGateway;
    private DeckBuilderPresenter deckListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApp().getApplicationComponent().inject(this);

        databaseGateway.open(DeckBuilderActivity.class, this);
        List<Card> cards = databaseGateway.query(DeckBuilderActivity.class, Card.class);
        Map<String, Collection<Card>> map = null;

        Observable.from(cards)
                .toMultimap((card -> !TextUtils.isEmpty(card.getPlayerClass()) ? card.getPlayerClass() : NEUTRAL_CLASS))
                .subscribe(collectionMap -> {
                    deckListPresenter = new DeckBuilderPresenter(DeckBuilderActivity.this, collectionMap);
                });
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
