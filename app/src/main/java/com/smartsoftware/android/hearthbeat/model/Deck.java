package com.smartsoftware.android.hearthbeat.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 18.10.2015
 * Time: 22:48
 * Email: mrahbar.azad@gmail.com
 */
public class Deck extends RealmObject {

    @PrimaryKey
    private String id;

    private String remoteId;
    private String title;
    private String notes;
    private RealmList<Card> cards;
    private Cardback back;

    private boolean is_public;
    private boolean active;
    private Date date_created;
    private Date date_edited;
    private int deck_type_id;
    private int classId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RealmList<Card> getCards() {
        return cards;
    }

    public void setCards(RealmList<Card> cards) {
        this.cards = cards;
    }

    public Cardback getBack() {
        return back;
    }

    public void setBack(Cardback back) {
        this.back = back;
    }

    public boolean is_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_edited() {
        return date_edited;
    }

    public void setDate_edited(Date date_edited) {
        this.date_edited = date_edited;
    }

    public int getDeck_type_id() {
        return deck_type_id;
    }

    public void setDeck_type_id(int deck_type_id) {
        this.deck_type_id = deck_type_id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
