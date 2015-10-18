package com.smartsoftware.android.hearthbeat.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.model.Card;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open the default realm for the UI thread.
        realm = Realm.getInstance(this);

        findViewById(R.id.test_btn).setOnClickListener(v -> {
            final Card card= realm.where(Card.class).equalTo("id", "CS1_129").findFirst();
            card.getId();
        });

        findViewById(R.id.go_btn).setOnClickListener(v -> loadJsonFromStream());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void loadJsonFromStream(){
        realm.executeTransaction(realm -> {
            try(InputStream stream = getAssets().open("deDE_mod.json")) {
                realm.createAllFromJson(Card.class, stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
