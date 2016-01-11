package com.smartsoftware.android.hearthbeat.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.smartsoftware.android.hearthbeat.data.AppDatabase;

import java.util.List;


/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 19:24
 * Email: mrahbar.azad@gmail.com
 */
@Table(database = AppDatabase.class)
public class Card extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;
    
    @Column String cardId;
    @Column String name;
    @Column String cardSet;
    @Column String type;
    @Column String faction;
    @Column String rarity;

    @Column int cost;
    @Column int attack;
    @Column int health;

    @Column String text;
    @Column String inPlayText;
    @Column String flavor;
    @Column String artist;
    @Column int durability;

    @Column boolean collectible;
    @Column boolean elite;

    @Column String playerClass;
    @Column String howToGet;
    @Column String howToGetGold;
    @Column String race;
    @Column String img;
    @Column String imgGold;
    @Column String locale;

    List<CardMechanics> mechanics;

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

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInPlayText() {
        return inPlayText;
    }

    public void setInPlayText(String inPlayText) {
        this.inPlayText = inPlayText;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getHowToGet() {
        return howToGet;
    }

    public void setHowToGet(String howToGet) {
        this.howToGet = howToGet;
    }

    public String getHowToGetGold() {
        return howToGetGold;
    }

    public void setHowToGetGold(String howToGetGold) {
        this.howToGetGold = howToGetGold;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
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

    public List<CardMechanics> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<CardMechanics> mechanics) {
        this.mechanics = mechanics;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "mechanics")
    public List<CardMechanics> loadCardMechanics() {
        if (mechanics == null || mechanics.isEmpty()) {
            mechanics = SQLite.select()
                    .from(CardMechanics.class)
                    .where(CardMechanics_Table.cardForeignKeyContainer_id.eq(id))
                    .queryList();
        }
        return mechanics;
    }

    public long getId() {
        return id;
    }

    public Hero toHeroModel() {
        final Hero hero = new Hero();
        hero.setCardId(cardId);
        hero.setName(name);
        hero.setCardSet(cardSet);
        hero.setType(type);
        hero.setRarity(rarity);
        hero.setHealth(health);
        hero.setCollectible(collectible);
        hero.setPlayerClass(playerClass);
        hero.setImg(img);
        hero.setImgGold(imgGold);
        hero.setLocale(locale);


        return hero;
    }
}
