package com.gmail.elnora.fet.hw_6_async.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static final String CONTACT_DATABASE_NAME = "contact_database";

    public static volatile ContactDatabase INSTANCE;

    public abstract ContactDao getContactDao();

    public static ContactDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactDatabase.class, CONTACT_DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
