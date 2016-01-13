package com.smartsoftware.android.hearthbeat.di;

import com.smartsoftware.android.hearthbeat.main.CollectionActivity;
import com.smartsoftware.android.hearthbeat.main.LaunchActivity;
import com.smartsoftware.android.hearthbeat.main.FeedActivity;
import com.smartsoftware.android.hearthbeat.main.SingleCardActivity;
import com.smartsoftware.android.hearthbeat.presenter.FeedPresenter;
import com.smartsoftware.android.hearthbeat.view.FeedsView;

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
    void inject(FeedActivity activity);
    void inject(CollectionActivity activity);
    void inject(LaunchActivity activity);
    void inject(SingleCardActivity activity);

    void inject(FeedPresenter presenter);
    void inject(FeedsView feedsView);
}
