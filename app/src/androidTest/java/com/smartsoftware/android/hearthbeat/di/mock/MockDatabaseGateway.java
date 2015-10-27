package com.smartsoftware.android.hearthbeat.di.mock;

import android.content.Context;

import com.smartsoftware.android.hearthbeat.persistance.DatabaseGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmObject;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 22:42
 * Email: mrahbar.azad@gmail.com
 */
public class MockDatabaseGateway extends DatabaseGateway {

    private Map<Class, List<RealmObject>> map;

    public MockDatabaseGateway() {
        map = new HashMap<>();
    }

    @Override
    public void open(Class clazz, Context context) {
    }

    @Override
    public void execute(Class clazz, Runnable runnable) {
        runnable.run();
    }

    @Override
    public void close(Class clazz) {
    }

    @Override
    public <E extends RealmObject> void store(Class clazz, E object) {
        List<RealmObject> realmObjects = map.get(object.getClass());
        if (realmObjects == null) {
            realmObjects = new ArrayList<>();
        }

        realmObjects.add(object);
        map.put(object.getClass(), realmObjects);
    }

    public List<RealmObject> getMap(Class clazz) {
        return map.get(clazz);
    }
}


