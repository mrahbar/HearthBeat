package com.smartsoftware.android.hearthbeat.model;


import com.codeslap.persistence.PrimaryKey;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 20:32
 * Email: mrahbar.azad@gmail.com
 */
public class CardMechanics {

    @PrimaryKey
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
