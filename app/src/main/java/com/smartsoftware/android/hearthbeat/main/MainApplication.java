package com.smartsoftware.android.hearthbeat.main;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;
import com.smartsoftware.android.hearthbeat.di.ApplicationModule;
import com.smartsoftware.android.hearthbeat.di.DaggerApplicationComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 19:55
 * Email: mrahbar.azad@gmail.com
 */
public class MainApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @VisibleForTesting
    public void setTestComponent(ApplicationComponent appComponent) {
        applicationComponent = appComponent;
    }
}
