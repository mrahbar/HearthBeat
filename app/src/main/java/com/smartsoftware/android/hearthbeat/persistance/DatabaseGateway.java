package com.smartsoftware.android.hearthbeat.persistance;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 20.10.2015
 * Time: 19:11
 * Email: mrahbar.azad@gmail.com
 */
public class DatabaseGateway {

    private HashMap<Class, Realm> instanceMap;

    public DatabaseGateway() {
        instanceMap = new HashMap<>();
    }

    public void open(Class clazz, Context context) {
        if (hasInstance(clazz)) {
            close(clazz);
        }

        instanceMap.put(clazz, Realm.getInstance(context));
    }

    public void execute(Class clazz, Runnable runnable) {
        if (hasInstance(clazz)) {
            Realm realm = getInstance(clazz);
            realm.executeTransaction(realm1 -> runnable.run());
        } else {
            throwError(clazz);
        }
    }

    public void close(Class clazz) {
        if (hasInstance(clazz)) {
            getInstance(clazz).close();
            instanceMap.remove(clazz);
        }
    }

    private Realm getInstance(Class clazz) {
        return instanceMap.get(clazz);
    }

    private boolean hasInstance(Class clazz) {
        return instanceMap.containsKey(clazz);
    }

    public <E extends RealmObject> void store(Class clazz, E object) {
        if (hasInstance(clazz)) {
            Realm realm = getInstance(clazz);
            realm.copyToRealm(object);
        } else {
            throwError(clazz);
        }
    }

    public <T extends RealmObject> List<T> query(Class clazz, Class<T> objectClass) {
        if (hasInstance(clazz)) {
            Realm realm = getInstance(clazz);
            RealmResults<? extends RealmObject> results = realm.where(objectClass).findAll();

            List<T> list = new ArrayList<>(results.size());
            for (Object o : results) {
                list.add((T) o);
            }

            return list;
        } else {
            throwError(clazz);
        }

        return null;
    }

    private void throwError(Class clazz) {
        throw new RuntimeException("No open database gateway for class: "+clazz.getSimpleName());
    }
}
