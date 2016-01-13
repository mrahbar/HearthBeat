package com.smartsoftware.android.hearthbeat.persistance;

import android.content.Context;

import com.smartsoftware.android.hearthbeat.model.Model;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.KeyIterator;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 13.01.2016
 * Time: 13:31
 * Email: mrahbar.azad@gmail.com
 */
public class DatabaseManager {

    private DB snappydb;

    public DatabaseManager(Context context) {
        try {
            snappydb = DBFactory.open(context);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            snappydb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public <T extends Model> void store(T value) {
        try {
            snappydb.put(value.buildKey(), value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public <T extends Model> T read(String key, Class<T> clazz) {
        try {
            return snappydb.getObject(key, clazz);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends Model> List<T> findAll(String prefix, Class<T> objectClass) {
        try {
            List<T> list = new ArrayList<>();

            for (String key : snappydb.findKeys(prefix)) {
                list.add(read(key, objectClass));
            }

            return list;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String key) {
        try {
            snappydb.del(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
