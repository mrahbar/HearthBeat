package com.smartsoftware.android.hearthbeat.di;

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
                TestApplicationModule.class
        }
)
public interface TestApplicationComponent extends ApplicationComponent {
}
