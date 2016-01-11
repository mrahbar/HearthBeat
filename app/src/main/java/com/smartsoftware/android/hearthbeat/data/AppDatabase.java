package com.smartsoftware.android.hearthbeat.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 11.01.2016
 * Time: 14:57
 * Email: mrahbar.azad@gmail.com
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, foreignKeysSupported = true)
public class AppDatabase {
    public static final String NAME = "Database";
    public static final int VERSION = 1;
}