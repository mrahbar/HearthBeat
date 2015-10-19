package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;

import java.util.Locale;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 22:13
 * Email: mrahbar.azad@gmail.com
 */
public class LaunchActivity extends BaseActivity {

    public static final String SETUP_FINISHED = "SETUP_FINISHED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale current = getResources().getConfiguration().locale;
    }
}
