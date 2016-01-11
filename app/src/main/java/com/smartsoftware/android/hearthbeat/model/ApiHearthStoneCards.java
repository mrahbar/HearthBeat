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

    private List<Card> Missions;
    private List<Card> Classic;
    private List<Card> Naxxramas;

    @SerializedName("Goblins vs Gnomes")
    private List<Card> Goblins_vs_Gnomes;
    private List<Card> Reward;
    private List<Card> Credits;

    @SerializedName("Tavern Brawl")
    private List<Card> Tavern_Brawl;
    private List<Card> System;
    private List<Card> Debug;

    @SerializedName("Hero Skins")
    private List<Card> Hero_Skins;
    private List<Card> Basic;
    private List<Card> Promotion;

    @SerializedName("The Grand Tournament")
    private List<Card> The_Grand_Tournament;

    @SerializedName("Blackrock Mountain")
    private List<Card> Blackrock_Mountain;

    @SerializedName("The League of Explorers")
    private List<Card> The_League_of_Explorers;

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

    public List<Card> getThe_League_of_Explorers() {
        return The_League_of_Explorers;
    }

    public void setThe_League_of_Explorers(List<Card> the_League_of_Explorers) {
        The_League_of_Explorers = the_League_of_Explorers;
    }

    public List<Card> toList() {
        List<Card> newList = new ArrayList<>();
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
        newList.addAll(The_League_of_Explorers);

        return newList;
    }
}
