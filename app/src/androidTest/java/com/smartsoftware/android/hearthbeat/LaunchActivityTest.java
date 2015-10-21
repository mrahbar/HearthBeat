package com.smartsoftware.android.hearthbeat;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
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
import com.smartsoftware.android.hearthbeat.di.mock.MockDatabaseGateway;
import com.smartsoftware.android.hearthbeat.di.mock.MockPrefs;
import com.smartsoftware.android.hearthbeat.main.LaunchActivity;
import com.smartsoftware.android.hearthbeat.main.MainApplication;
import com.smartsoftware.android.hearthbeat.model.Cardback;
import com.smartsoftware.android.hearthbeat.utils.Utils;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Locale;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static com.smartsoftware.android.hearthbeat.EspressoHelper.checkViewByIdVisible;
import static com.smartsoftware.android.hearthbeat.EspressoHelper.checkViewByStringVisible;
import static com.smartsoftware.android.hearthbeat.EspressoHelper.clickOnViewById;
import static com.smartsoftware.android.hearthbeat.EspressoHelper.clickOnViewByText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

/*
https://github.com/googlesamples/android-testing-templates
 */
@RunWith(AndroidJUnit4.class)
public class LaunchActivityTest {

    @Rule public final MockWebServer server = new MockWebServer();

    private MockDatabaseGateway databaseGateway = new MockDatabaseGateway();

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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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
        checkViewByStringVisible(R.string.launch_download_cards);
    }

    @Test
    public void OnInitialLaunchSkipWillShowMessageDialog() {
        launchActivity();
        clickOnViewById(R.id.launch_bottom_button_continue);
        checkViewByStringVisible(R.string.launch_download_skipped_message);
    }

    @Test
    @Ignore
    public void OnInitialLaunchSelectDeviceLanguage() {
        launchActivity();
        clickOnViewById(R.id.launch_bottom_button_download);

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

        //onData wont work
        onData(allOf(is(instanceOf(String.class)), is(languageName)))
                .check(matches(isChecked()));
    }

    @Test
    public void SelectingLanguageFillsDatabaseAndForwardToMainActivity() throws IOException {
        launchActivity();
        prepareHearthStoneApiService();

        clickOnViewById(R.id.launch_bottom_button_download);
        clickOnViewByText(R.string.launch_download);

        //This is a check for MainActivity
        checkViewByIdVisible(R.id.main_deck_add);
        assertEquals(35, databaseGateway.getMap(Cardback.class).size());
    }

    private void prepareHearthStoneApiService() throws IOException {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MainApplication app = (MainApplication) instrumentation.getTargetContext().getApplicationContext();
        Resources resources = app.getResources();

        String cards = Utils.getStringFromInputStream(resources.getAssets().open("api_cards_deDe.json"));
        String cardBacks = Utils.getStringFromInputStream(resources.getAssets().open("cardBack_deDe.json"));

        server.enqueue(new MockResponse().setBody(cards));
        server.enqueue(new MockResponse().setBody(cardBacks));
    }
}