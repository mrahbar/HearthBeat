package com.smartsoftware.android.hearthbeat;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.smartsoftware.android.hearthbeat.main.LaunchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.smartsoftware.android.hearthbeat.EspressoHelper.*;

/*
https://github.com/googlesamples/android-testing-templates
 */
@RunWith(AndroidJUnit4.class)
public class LaunchActivityTest {

    @Rule
    public ActivityTestRule<LaunchActivity> activityRule = new ActivityTestRule<>(LaunchActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    private void launchActivity() {
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void OnInitialLaunchShowDownloadScreen() {
        launchActivity();

        checkViewByStringVisible("Download");
    }
}