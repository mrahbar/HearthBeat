package com.smartsoftware.android.hearthbeat.api;


import com.smartsoftware.android.hearthbeat.model.ApiCardback;
import com.smartsoftware.android.hearthbeat.model.Cardback;
import com.smartsoftware.android.hearthbeat.model.ApiHearthStoneCards;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.09.2015
 * Time: 20:16
 * Email: mrahbar.azad@gmail.com
 */
public interface HearthStoneApiService {

    @GET("cards?collectible=1")
    Observable<ApiHearthStoneCards> getCards(@Query("locale") String locale);

    @GET("cardbacks")
    Observable<List<ApiCardback>> getCardbacks(@Query("locale") String locale);
}
