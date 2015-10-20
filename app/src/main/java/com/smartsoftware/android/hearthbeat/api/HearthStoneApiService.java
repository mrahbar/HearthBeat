package com.smartsoftware.android.hearthbeat.api;


import com.smartsoftware.android.hearthbeat.model.Cardback;
import com.smartsoftware.android.hearthbeat.model.HearthStoneApiCards;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.09.2015
 * Time: 20:16
 * Email: mrahbar.azad@gmail.com
 */
public interface HearthStoneApiService {

    @GET("cards")
    Call<HearthStoneApiCards> getCards(@Query("locale") String locale);

    @GET("cardbacks")
    Call<List<Cardback>> getCardbacks(@Query("locale") String locale);
}
