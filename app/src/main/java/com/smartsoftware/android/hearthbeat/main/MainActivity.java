package com.smartsoftware.android.hearthbeat.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.R;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open the default realm for the UI thread.
        realm = Realm.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
