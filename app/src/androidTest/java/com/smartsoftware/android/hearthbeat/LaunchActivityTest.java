package com.smartsoftware.android.hearthbeat;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartsoftware.android.hearthbeat.api.HearthStoneApiService;
import com.smartsoftware.android.hearthbeat.di.DaggerTestApplicationComponent;
import com.smartsoftware.android.hearthbeat.di.TestApplicationComponent;
import com.smartsoftware.android.hearthbeat.di.TestApplicationModule;
import com.smartsoftware.android.hearthbeat.di.mock.MockPrefs;
import com.smartsoftware.android.hearthbeat.main.LaunchActivity;
import com.smartsoftware.android.hearthbeat.main.MainApplication;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Locale;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.smartsoftware.android.hearthbeat.EspressoHelper.*;

/*
https://github.com/googlesamples/android-testing-templates
 */
@RunWith(AndroidJUnit4.class)
public class LaunchActivityTest {

    @Rule public final MockWebServer server = new MockWebServer();

    private DatabaseGateway databaseGateway = Mockito.mock(DatabaseGateway.class);

    @Rule
    public ActivityTestRule<LaunchActivity> activityRule = new ActivityTestRule<>(LaunchActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    private void launchActivity() {
        activityRule.launchActivity(new Intent());
    }

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MainApplication app = (MainApplication) instrumentation.getTargetContext().getApplicationContext();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HearthStoneApiService hearthStoneApiService = retrofit.create(HearthStoneApiService.class);

        MockPrefs prefs = new MockPrefs(app);
        TestApplicationModule applicationModule = new TestApplicationModule(app, gson,
                prefs, hearthStoneApiService, databaseGateway);

        TestApplicationComponent component = DaggerTestApplicationComponent.builder()
                .testApplicationModule(applicationModule)
                .build();
        app.setTestComponent(component);
    }

    @Test
    public void OnInitialLaunchShowDownloadScreen() {
        launchActivity();
        checkViewByStringVisible(R.string.launch_title);

        swipeViewpagerLeft(R.id.launch_viewpager);
        checkViewByStringVisible(R.string.launch_download);
    }

    @Test
    public void OnInitialLaunchSelectDeviceLanguage() {
        launchActivity();
        swipeViewpagerLeft(R.id.launch_viewpager);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MainApplication app = (MainApplication) instrumentation.getTargetContext().getApplicationContext();

        Resources resources = app.getResources();
        Locale current = resources.getConfiguration().locale;
        String languageCode = current.getLanguage()+current.getCountry();

        final String[] langcodes = resources.getStringArray(R.array.langcodes);
        final String[] names = resources.getStringArray(R.array.langcodes_names);
        String languageName = null;

        for (int i = 0, langcodesLength = langcodes.length; i < langcodesLength; i++) {
            String code = langcodes[i];
            if (TextUtils.equals(code, languageCode)) {
                languageName = names[i];
                break;
            }
        }

        checkViewByStringVisible(languageName);
    }
}