package com.smartsoftware.android.hearthbeat.model;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 23:39
 * Email: mrahbar.azad@gmail.com
 */
public class ApiCardback {

    private String cardBackId;
    private String name;
    private String description;
    private String source;
    private String howToGet;
    private String img;
    private String imgAnimated;
    private String sortCategory;
    private String sortOrder;
    private String locale;
    private String sourceDescription;
    private boolean enabled;

    public String getCardBackId() {
        return cardBackId;
    }

    public void setCardBackId(String cardBackId) {
        this.cardBackId = cardBackId;
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

    public Cardback toModel() {
        final Cardback cardback = new Cardback();
        cardback.setCardBackId(cardBackId);
        cardback.setName(name);
        cardback.setDescription(description);
        cardback.setSource(source);
        cardback.setHowToGet(howToGet);
        cardback.setImg(img);
        cardback.setImgAnimated(imgAnimated);
        cardback.setSortCategory(sortCategory);
        cardback.setSortOrder(sortOrder);
        cardback.setLocale(locale);
        cardback.setSourceDescription(sourceDescription);
        cardback.setEnabled(enabled);

        return cardback;
    }
}
