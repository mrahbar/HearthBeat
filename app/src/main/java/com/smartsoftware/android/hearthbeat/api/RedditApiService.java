package com.smartsoftware.android.hearthbeat.api;

import com.smartsoftware.android.hearthbeat.model.reddit.SubmissionResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 20:26
 * Email: mrahbar.azad@gmail.com
 */
public interface RedditApiService {

    @GET("r/hearthstone/hot.json")
    Observable<SubmissionResponse> getPopularSubmissions();
}
