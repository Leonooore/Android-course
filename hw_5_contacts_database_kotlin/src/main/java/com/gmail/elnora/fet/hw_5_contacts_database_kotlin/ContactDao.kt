package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import androidx.room.*

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): MutableList<Contact>
}