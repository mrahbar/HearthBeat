package com.smartsoftware.android.hearthbeat.main;

import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject DatabaseGateway databaseGateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        databaseGateway.close(MainActivity.class); only when opened
    }
}
