package com.smartsoftware.android.hearthbeat.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 20.10.2015
 * Time: 18:44
 * Email: mrahbar.azad@gmail.com
 */
public class HearthStoneApiCards {

    private List<Card> Missions;
    private List<Card> Classic;
    private List<Card> Naxxramas;

    @SerializedName("Goblins vs Gnomes")
    private List<Card> Goblins_vs_Gnomes;
    private List<Card> Reward;
    private List<Card> Credits;

    @SerializedName("Tavern Braw")
    private List<Card> Tavern_Brawl;
    private List<Card> System;
    private List<Card> Debug;

    @SerializedName("Hero Skins")
    private List<Card> Hero_Skins;
    private List<Card> Basic;
    private List<Card> Promotion;

    @SerializedName("The Grand Tournamen")
    private List<Card> The_Grand_Tournament;

    @SerializedName("Blackrock Mountain")
    private List<Card> Blackrock_Mountain;

    public List<Card> getMissions() {
        return Missions;
    }

    public void setMissions(List<Card> missions) {
        Missions = missions;
    }

    public List<Card> getClassic() {
        return Classic;
    }

    public void setClassic(List<Card> classic) {
        Classic = classic;
    }

    public List<Card> getNaxxramas() {
        return Naxxramas;
    }

    public void setNaxxramas(List<Card> naxxramas) {
        Naxxramas = naxxramas;
    }

    public List<Card> getGoblins_vs_Gnomes() {
        return Goblins_vs_Gnomes;
    }

    public void setGoblins_vs_Gnomes(List<Card> goblins_vs_Gnomes) {
        Goblins_vs_Gnomes = goblins_vs_Gnomes;
    }

    public List<Card> getReward() {
        return Reward;
    }

    public void setReward(List<Card> reward) {
        Reward = reward;
    }

    public List<Card> getCredits() {
        return Credits;
    }

    public void setCredits(List<Card> credits) {
        Credits = credits;
    }

    public List<Card> getTavern_Brawl() {
        return Tavern_Brawl;
    }

    public void setTavern_Brawl(List<Card> tavern_Brawl) {
        Tavern_Brawl = tavern_Brawl;
    }

    public List<Card> getSystem() {
        return System;
    }

    public void setSystem(List<Card> system) {
        System = system;
    }

    public List<Card> getDebug() {
        return Debug;
    }

    public void setDebug(List<Card> debug) {
        Debug = debug;
    }

    public List<Card> getHero_Skins() {
        return Hero_Skins;
    }

    public void setHero_Skins(List<Card> hero_Skins) {
        Hero_Skins = hero_Skins;
    }

    public List<Card> getBasic() {
        return Basic;
    }

    public void setBasic(List<Card> basic) {
        Basic = basic;
    }

    public List<Card> getPromotion() {
        return Promotion;
    }

    public void setPromotion(List<Card> promotion) {
        Promotion = promotion;
    }

    public List<Card> getThe_Grand_Tournament() {
        return The_Grand_Tournament;
    }

    public void setThe_Grand_Tournament(List<Card> the_Grand_Tournament) {
        The_Grand_Tournament = the_Grand_Tournament;
    }

    public List<Card> getBlackrock_Mountain() {
        return Blackrock_Mountain;
    }

    public void setBlackrock_Mountain(List<Card> blackrock_Mountain) {
        Blackrock_Mountain = blackrock_Mountain;
    }
}
