package com.smartsoftware.android.hearthbeat.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.smartsoftware.android.hearthbeat.data.AppDatabase;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 19:24
 * Email: mrahbar.azad@gmail.com
 */
@Table(database = AppDatabase.class)
public class Hero extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;
    
    @Column String cardId;
    @Column String name;
    @Column String cardSet;
    @Column String type;
    @Column String rarity;

    @Column int health;
    @Column boolean collectible;

    @Column String playerClass;
    @Column String img;
    @Column String imgGold;
    @Column String locale;

    public long getId() {
        return id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgGold() {
        return imgGold;
    }

    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
