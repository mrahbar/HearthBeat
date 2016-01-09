package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smartsoftware.android.hearthbeat.di.ApplicationComponent;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:13
 * Email: mrahbar.azad@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        ApplicationComponent component = getApplicationComponent();
        injectActivity(component);
    }

    public abstract void injectActivity(ApplicationComponent component);

    protected ApplicationComponent getApplicationComponent() {
        return getApp().getApplicationComponent();
    }

    protected MainApplication getApp() {
        return ((MainApplication) getApplication());
    }
}
