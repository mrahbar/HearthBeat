package com.smartsoftware.android.hearthbeat.di.mock;

import android.content.Context;

import com.smartsoftware.android.hearthbeat.persistance.Prefs;

import java.util.HashMap;
import java.util.Set;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 13.09.2015
 * Time: 23:21
 * Email: mrahbar.azad@gmail.com
 */
public class MockPrefs extends Prefs {

    private HashMap<String, Boolean> booleanMap;

    public MockPrefs(Context context) {
        super(context);
        booleanMap = new HashMap<>();
    }

    @Override
    public void save(String key, boolean value) {
        booleanMap.put(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return booleanMap.containsKey(key) ? booleanMap.get(key) : defValue;
    }
}
