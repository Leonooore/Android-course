package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object {
        @Volatile
        private var database: ContactDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ContactDatabase {
            if (database == null) {
                database = Room.databaseBuilder(context.applicationContext, ContactDatabase::class.java, "contact_database")
                        .allowMainThreadQueries()
                        .build()
            }
            return database as ContactDatabase
        }
    }
}