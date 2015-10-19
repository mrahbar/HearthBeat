package com.smartsoftware.android.hearthbeat.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 23:39
 * Email: mrahbar.azad@gmail.com
 */
public class Cardback extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String description;
    private String source;
    private String howToGet;
    private String img;
    private String imgAnimated;
    private String sortCategory;
    private String sortOrder;
}
