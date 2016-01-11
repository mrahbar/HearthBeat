package com.smartsoftware.android.hearthbeat.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.smartsoftware.android.hearthbeat.data.AppDatabase;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 23:39
 * Email: mrahbar.azad@gmail.com
 */
@Table(database = AppDatabase.class)
public class Cardback extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;
    
    @Column String cardBackId;
    @Column String name;
    @Column String description;
    @Column String source;
    @Column String howToGet;
    @Column String img;
    @Column String imgAnimated;
    @Column String sortCategory;
    @Column String sortOrder;
    @Column String locale;
    @Column String sourceDescription;
    @Column boolean enabled;

    public long getId() {
        return id;
    }

    public String getCardBackId() {
        return cardBackId;
    }

    public void setCardBackId(String cardBackId) {
        this.cardBackId = cardBackId;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
