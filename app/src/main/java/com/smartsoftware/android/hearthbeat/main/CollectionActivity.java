package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;
import android.text.TextUtils;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.presenter.CollectionPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class CollectionActivity extends BaseActivity implements CollectionPresenter.CollectionPresenterListener {

    @Inject DatabaseGateway databaseGateway;
    private CollectionPresenter deckListPresenter;
    private boolean paused;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseGateway.open(CollectionActivity.class, this);
        List<Card> cards = databaseGateway.queryList(CollectionActivity.class, Card.class);

        String neutralClassName = getString(R.string.class_neutral);
        Observable.from(cards)
                .toMultimap(card -> !TextUtils.isEmpty(card.getPlayerClass()) ? card.getPlayerClass() : neutralClassName)
                .map(originalMap -> {
                    HashMap<String, List<Card>> map = new HashMap<>(originalMap.size());
                    for (String key : originalMap.keySet()) {
                        map.put(key, new ArrayList<>(originalMap.get(key)));
                    }
                    return map;
                })
                .subscribe(collectionMap -> {
                    deckListPresenter = new CollectionPresenter(CollectionActivity.this, collectionMap);
                });
    }

    @Override
    public void injectActivity(ApplicationComponent component) {
        component.inject(this);
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
        databaseGateway.close(CollectionActivity.class);
    }
}
