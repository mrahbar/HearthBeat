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
}
