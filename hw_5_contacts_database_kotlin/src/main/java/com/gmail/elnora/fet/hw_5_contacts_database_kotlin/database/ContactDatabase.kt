package com.gmail.elnora.fet.hw_5_contacts_database_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        private const val CONTACT_DATABASE_NAME = "contact_database"

        @JvmStatic
        fun getDatabase(context: Context): ContactDatabase {
            if (INSTANCE == null) {
                synchronized(ContactDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, ContactDatabase::class.java, CONTACT_DATABASE_NAME)
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return INSTANCE as ContactDatabase
        }
    }
}