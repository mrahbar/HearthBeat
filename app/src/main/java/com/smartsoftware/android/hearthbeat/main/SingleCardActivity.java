package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.presenter.SingleCardPresenter;
import com.smartsoftware.android.hearthbeat.ui.view.CollectionView;

import javax.inject.Inject;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 29.10.2015
 * Time: 20:48
 * Email: mrahbar.azad@gmail.com
 */
public class SingleCardActivity extends BaseActivity implements SingleCardPresenter.SingleCardPresenterListener {

    public static final String EXTRA_BUNDLE = "extraBundle";

    @Inject DatabaseGateway databaseGateway;
    private SingleCardPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlecard);

        Bundle bundle = getIntent().getBundleExtra(EXTRA_BUNDLE);
        CollectionView.CardIntentBundle intentBundle = CollectionView.CardIntentBundle.fromBundle(bundle);
        databaseGateway.open(SingleCardActivity.class, this);
        Card card = databaseGateway.querySingle(SingleCardActivity.class, Card.class, intentBundle.cardId);
        presenter = new SingleCardPresenter(this, intentBundle, card, savedInstanceState == null);
    }

    @Override
    public void injectActivity(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseGateway.close(SingleCardActivity.class);
    }

    @Override
    public void onFinish() {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }

    @Override
    public void onBackPressed() {
        presenter.finish();
    }
}
