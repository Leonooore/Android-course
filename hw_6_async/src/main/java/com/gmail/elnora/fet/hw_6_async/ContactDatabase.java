package com.gmail.elnora.fet.hw_6_async;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    public static ContactDatabase database;

    public static ContactDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), ContactDatabase.class, "contact_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract ContactDao getContactDao();

}
