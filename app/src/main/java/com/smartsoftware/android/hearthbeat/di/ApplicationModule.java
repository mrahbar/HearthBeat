package com.smartsoftware.android.hearthbeat.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartsoftware.android.hearthbeat.BuildConfig;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.api.RedditApiService;
import com.smartsoftware.android.hearthbeat.main.MainApplication;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.squareup.okhttp.Request;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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
    private DatabaseGateway databaseGateway;
    private final RedditApiService redditApiService;

    public ApplicationModule(MainApplication app) {
        this.app = app;

        RealmConfiguration config = new RealmConfiguration.Builder(app).build();
        Realm.setDefaultConfiguration(config);
        databaseGateway = new DatabaseGateway();

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
    DatabaseGateway provideDatabaseGateway() {
        return databaseGateway;
    }

}
