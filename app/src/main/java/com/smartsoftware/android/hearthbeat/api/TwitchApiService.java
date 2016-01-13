package com.smartsoftware.android.hearthbeat.api;

import com.smartsoftware.android.hearthbeat.model.twitch.Streams;

import retrofit.http.GET;
import rx.Observable;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 12.01.2016
 * Time: 20:22
 * Email: mrahbar.azad@gmail.com
 */
public interface TwitchApiService {

    @GET("streams?game=Hearthstone%3A%20Heroes%20of%20Warcraft&stream_type=live")
    Observable<Streams> getLiveHearthStoneStreams();
}
