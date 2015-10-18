package com.smartsoftware.android.hearthbeat.main;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 19:55
 * Email: mrahbar.azad@gmail.com
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
    }
}
