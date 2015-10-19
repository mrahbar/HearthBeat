package com.smartsoftware.android.hearthbeat.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 19:24
 * Email: mrahbar.azad@gmail.com
 */
public class Card extends RealmObject {

    @PrimaryKey
    private String id;

    private String artistName;
    private String cardName;
    private String cardSet;
    private String cardTextInHand;
    private String cardType;
    private String className;
    private int classId;

    private boolean collectible;
    private int cost;
    private boolean elite;
    private int enchantmentBirthVisual;
    private int enchantmentIdleVisual;

    private String flavorText;
    private String goldImage;
    private String image;
    private String locale;
    private String rarity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getCardTextInHand() {
        return cardTextInHand;
    }

    public void setCardTextInHand(String cardTextInHand) {
        this.cardTextInHand = cardTextInHand;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public int getEnchantmentBirthVisual() {
        return enchantmentBirthVisual;
    }

    public void setEnchantmentBirthVisual(int enchantmentBirthVisual) {
        this.enchantmentBirthVisual = enchantmentBirthVisual;
    }

    public int getEnchantmentIdleVisual() {
        return enchantmentIdleVisual;
    }

    public void setEnchantmentIdleVisual(int enchantmentIdleVisual) {
        this.enchantmentIdleVisual = enchantmentIdleVisual;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getGoldImage() {
        return goldImage;
    }

    public void setGoldImage(String goldImage) {
        this.goldImage = goldImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}
