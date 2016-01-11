package com.smartsoftware.android.hearthbeat.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import com.smartsoftware.android.hearthbeat.data.AppDatabase;


/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 19.10.2015
 * Time: 20:32
 * Email: mrahbar.azad@gmail.com
 */
@Table(database = AppDatabase.class)
public class CardMechanics extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column String name;

    @ForeignKey(saveForeignKeyModel = false)
    ForeignKeyContainer<Card> cardForeignKeyContainer;

    public void associateCard(Card card) {
        cardForeignKeyContainer = FlowManager.getContainerAdapter(Card.class).toForeignKeyContainer(card);
    }
}
