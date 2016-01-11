package com.smartsoftware.android.hearthbeat.di;

import android.content.Context;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.PersistenceConfig;
import com.codeslap.persistence.SqlAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartsoftware.android.hearthbeat.BuildConfig;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.api.RedditApiService;
import com.smartsoftware.android.hearthbeat.data.DataManager;
import com.smartsoftware.android.hearthbeat.main.MainApplication;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.model.CardMechanics;
import com.smartsoftware.android.hearthbeat.model.Cardback;
import com.smartsoftware.android.hearthbeat.model.Deck;
import com.smartsoftware.android.hearthbeat.model.Hero;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.squareup.okhttp.Request;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.09.2015
 * Time: 23:15
 * Email: mrahbar.azad@gmail.com
 */
@Module
public class ApplicationModule {

    private MainApplication app;
    private Gson gson;
    private Prefs prefs;
    private HearthStoneApiService hearthStoneApiService;
    private RedditApiService redditApiService;
    private DataManager dataManager;
    private SqlAdapter sqlAdapter;

    public ApplicationModule(MainApplication app) {
        this.app = app;
        dataManager = new DataManager();

        DatabaseSpec database = PersistenceConfig.registerSpec(/**db version**/1);
        database.match(Card.class, Cardback.class, CardMechanics.class, Hero.class, Deck.class);
        sqlAdapter = Persistence.getAdapter(app);

        prefs = Prefs.with(app);
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        Retrofit redditApiRetrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Retrofit hearthstoneApiRetrofit = new Retrofit.Builder()
                .baseUrl("https://omgvamp-hearthstone-v1.p.mashape.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        hearthstoneApiRetrofit.client().networkInterceptors().add(chain -> {
            final Request original = chain.request();
            final Request request = original.newBuilder()
                    .addHeader("X-Mashape-Key", BuildConfig.MASHAPE_KEY)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        hearthStoneApiService = hearthstoneApiRetrofit.create(HearthStoneApiService.class);
        redditApiService = redditApiRetrofit.create(RedditApiService.class);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return gson;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }


    @Provides
    @Singleton
    SqlAdapter provideSqlAdapter() {
        return sqlAdapter;
    }

    @Provides
    @Singleton
    Prefs providePrefs() {
        return prefs;
    }

    @Provides
    @Singleton
    HearthStoneApiService provideHearthStoneApiService() {
        return hearthStoneApiService;
    }

    @Provides
    @Singleton
    RedditApiService provideRedditApiService() {
        return redditApiService;
    }

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return dataManager;
    }
}
