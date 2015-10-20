package com.smartsoftware.android.hearthbeat.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartsoftware.android.hearthbeat.BuildConfig;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.main.MainApplication;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.09.2015
 * Time: 23:15
 * Email: mrahbar.azad@gmail.com
 */
@Module
public class ApplicationModule {

    private HearthStoneApiService hearthStoneApiService;
    private MainApplication app;
    private Gson gson;
    private Prefs prefs;
    private DatabaseGateway databaseGateway;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://omgvamp-hearthstone-v1.p.mashape.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofit.client().networkInterceptors().add(chain -> {
            final Request request = chain.request().newBuilder()
                    .addHeader("X-Mashape-Key", BuildConfig.MASHAPE_KEY)
                    .build();

            return chain.proceed(request);
        });

        hearthStoneApiService = retrofit.create(HearthStoneApiService.class);
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
    DatabaseGateway provideDatabaseGateway() {
        return databaseGateway;
    }

}
