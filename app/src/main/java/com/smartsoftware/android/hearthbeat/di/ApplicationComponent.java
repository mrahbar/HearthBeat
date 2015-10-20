package com.smartsoftware.android.hearthbeat.di;

import com.smartsoftware.android.hearthbeat.main.LaunchActivity;
import com.smartsoftware.android.hearthbeat.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.09.2015
 * Time: 23:26
 * Email: mrahbar.azad@gmail.com
 */
@Singleton
@Component (
        modules = {
                ApplicationModule.class
        }
)
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(LaunchActivity activity);
}
