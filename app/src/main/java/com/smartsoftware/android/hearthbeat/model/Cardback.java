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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHowToGet() {
        return howToGet;
    }

    public void setHowToGet(String howToGet) {
        this.howToGet = howToGet;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgAnimated() {
        return imgAnimated;
    }

    public void setImgAnimated(String imgAnimated) {
        this.imgAnimated = imgAnimated;
    }

    public String getSortCategory() {
        return sortCategory;
    }

    public void setSortCategory(String sortCategory) {
        this.sortCategory = sortCategory;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
