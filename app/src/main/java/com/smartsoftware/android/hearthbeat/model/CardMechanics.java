package com.smartsoftware.android.hearthbeat.model;

import io.realm.RealmObject;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 20:32
 * Email: mrahbar.azad@gmail.com
 */
public class CardMechanics extends RealmObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
