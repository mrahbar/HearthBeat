package com.smartsoftware.android.hearthbeat.di;

import android.content.Context;

import com.google.gson.Gson;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.main.MainApplication;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.smartsoftware.android.hearthbeat.persistance.Prefs;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.09.2015
 * Time: 23:15
 * Email: mrahbar.azad@gmail.com
 */
@Module
public class TestApplicationModule {

    private MainApplication app;
    public final Gson gson;
    private Prefs prefs;
    private HearthStoneApiService hearthStoneApiService;
    private DatabaseGateway databaseGateway;

    public TestApplicationModule(MainApplication app, Gson gson, Prefs prefs,
                                 HearthStoneApiService hearthStoneApiService,
                                 DatabaseGateway databaseGateway) {
        this.app = app;
        this.gson = gson;
        this.prefs = prefs;
        this.hearthStoneApiService = hearthStoneApiService;
        this.databaseGateway = databaseGateway;
    }

    @Provides
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
