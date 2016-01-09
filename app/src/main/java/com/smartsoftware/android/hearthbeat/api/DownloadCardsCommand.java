package com.smartsoftware.android.hearthbeat.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.model.ApiCard;
import com.smartsoftware.android.hearthbeat.model.ApiCardback;
import com.smartsoftware.android.hearthbeat.model.ApiHearthStoneCards;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 06.01.2016
 * Time: 22:42
 * Email: mrahbar.azad@gmail.com
 */
public class DownloadCardsCommand {

    public interface CallListener {
        void onDownloadFinished();
        void onDownloadError(Throwable e);
    }

    private HearthStoneApiService apiService;
    private DatabaseGateway databaseGateway;
    private Context context;
    private CallListener callListener;

    public DownloadCardsCommand(HearthStoneApiService apiService, DatabaseGateway databaseGateway, Context context) {
        this.apiService = apiService;
        this.databaseGateway = databaseGateway;
        this.context = context;
    }

    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }

    public void call(String locale) {
        Observable.zip(apiService.getCards(locale), apiService.getCardbacks(locale),
                (hearthStoneApiCards, cardbacks) -> store(hearthStoneApiCards, cardbacks))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                    if (callListener != null) {
                        callListener.onDownloadFinished();
                    }
                }, e -> {
                    if (callListener != null) {
                        callListener.onDownloadError(e);
                    }
                });
    }

    private Void store(ApiHearthStoneCards cards, List<ApiCardback> cardbacks) {
        databaseGateway.open(DownloadCardsCommand.class, context);
        databaseGateway.execute(DownloadCardsCommand.class, () -> {
            Observable.from(cardbacks)
                    .forEach(apiCardback -> databaseGateway.store(DownloadCardsCommand.class, apiCardback.toModel()));

            Observable.from(cards.toList())
                    .filter(ApiCard::isCollectible)
                    .forEach(apiCard -> {
                        if (TextUtils.equals(apiCard.getType(), "Hero"))
                            databaseGateway.store(DownloadCardsCommand.class, apiCard.toHeroModel());
                        else
                            databaseGateway.store(DownloadCardsCommand.class, apiCard.toCardModel());
                    });
        });
        databaseGateway.close(DownloadCardsCommand.class);
        return null;
    }
}
