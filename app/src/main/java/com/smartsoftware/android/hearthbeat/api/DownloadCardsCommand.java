package com.smartsoftware.android.hearthbeat.api;

import android.text.TextUtils;

import com.smartsoftware.android.hearthbeat.model.ApiHearthStoneCards;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.model.CardMechanics;
import com.smartsoftware.android.hearthbeat.model.Cardback;

import java.util.List;

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
    private CallListener callListener;

    public DownloadCardsCommand(HearthStoneApiService apiService) {
        this.apiService = apiService;
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

    private Void store(ApiHearthStoneCards cards, List<Cardback> cardbacks) {
        Observable.from(cardbacks)
                .forEach((cardback) -> {
                    cardback.save();
                });

        Observable.from(cards.toList())
                .filter(Card::isCollectible)
                .forEach(c -> {
                    if (TextUtils.equals(c.getType(), "Hero"))
                        c.toHeroModel().save();
                    else {
                        for (CardMechanics cm : c.getMechanics()) {
                            cm.associateCard(c);
                        }
                        c.save();
                    }
                });
        return null;
    }
}
