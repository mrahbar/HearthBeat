package com.smartsoftware.android.hearthbeat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 20.10.2015
 * Time: 18:44
 * Email: mrahbar.azad@gmail.com
 */
public class ApiHearthStoneCards {

    private List<ApiCard> Missions;
    private List<ApiCard> Classic;
    private List<ApiCard> Naxxramas;

    @SerializedName("Goblins vs Gnomes")
    private List<ApiCard> Goblins_vs_Gnomes;
    private List<ApiCard> Reward;
    private List<ApiCard> Credits;

    @SerializedName("Tavern Brawl")
    private List<ApiCard> Tavern_Brawl;
    private List<ApiCard> System;
    private List<ApiCard> Debug;

    @SerializedName("Hero Skins")
    private List<ApiCard> Hero_Skins;
    private List<ApiCard> Basic;
    private List<ApiCard> Promotion;

    @SerializedName("The Grand Tournament")
    private List<ApiCard> The_Grand_Tournament;

    @SerializedName("Blackrock Mountain")
    private List<ApiCard> Blackrock_Mountain;

    public List<ApiCard> getMissions() {
        return Missions;
    }

    public void setMissions(List<ApiCard> missions) {
        Missions = missions;
    }

    public List<ApiCard> getClassic() {
        return Classic;
    }

    public void setClassic(List<ApiCard> classic) {
        Classic = classic;
    }

    public List<ApiCard> getNaxxramas() {
        return Naxxramas;
    }

    public void setNaxxramas(List<ApiCard> naxxramas) {
        Naxxramas = naxxramas;
    }

    public List<ApiCard> getGoblins_vs_Gnomes() {
        return Goblins_vs_Gnomes;
    }

    public void setGoblins_vs_Gnomes(List<ApiCard> goblins_vs_Gnomes) {
        Goblins_vs_Gnomes = goblins_vs_Gnomes;
    }

    public List<ApiCard> getReward() {
        return Reward;
    }

    public void setReward(List<ApiCard> reward) {
        Reward = reward;
    }

    public List<ApiCard> getCredits() {
        return Credits;
    }

    public void setCredits(List<ApiCard> credits) {
        Credits = credits;
    }

    public List<ApiCard> getTavern_Brawl() {
        return Tavern_Brawl;
    }

    public void setTavern_Brawl(List<ApiCard> tavern_Brawl) {
        Tavern_Brawl = tavern_Brawl;
    }

    public List<ApiCard> getSystem() {
        return System;
    }

    public void setSystem(List<ApiCard> system) {
        System = system;
    }

    public List<ApiCard> getDebug() {
        return Debug;
    }

    public void setDebug(List<ApiCard> debug) {
        Debug = debug;
    }

    public List<ApiCard> getHero_Skins() {
        return Hero_Skins;
    }

    public void setHero_Skins(List<ApiCard> hero_Skins) {
        Hero_Skins = hero_Skins;
    }

    public List<ApiCard> getBasic() {
        return Basic;
    }

    public void setBasic(List<ApiCard> basic) {
        Basic = basic;
    }

    public List<ApiCard> getPromotion() {
        return Promotion;
    }

    public void setPromotion(List<ApiCard> promotion) {
        Promotion = promotion;
    }

    public List<ApiCard> getThe_Grand_Tournament() {
        return The_Grand_Tournament;
    }

    public void setThe_Grand_Tournament(List<ApiCard> the_Grand_Tournament) {
        The_Grand_Tournament = the_Grand_Tournament;
    }

    public List<ApiCard> getBlackrock_Mountain() {
        return Blackrock_Mountain;
    }

    public void setBlackrock_Mountain(List<ApiCard> blackrock_Mountain) {
        Blackrock_Mountain = blackrock_Mountain;
    }

    public List<ApiCard> toList() {
        List<ApiCard> newList = new ArrayList<>();
        newList.addAll(Missions);
        newList.addAll(Classic);
        newList.addAll(Naxxramas);
        newList.addAll(Goblins_vs_Gnomes);
        newList.addAll(Reward);
        newList.addAll(Credits);
        newList.addAll(Tavern_Brawl);
        newList.addAll(System);
        newList.addAll(Debug);
        newList.addAll(Hero_Skins);
        newList.addAll(Basic);
        newList.addAll(Promotion);
        newList.addAll(The_Grand_Tournament);
        newList.addAll(Blackrock_Mountain);

        return newList;
    }
}
