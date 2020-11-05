package com.gmail.elnora.fet.hw_5_contacts_database_kotlin.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.database.ContactDao
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.database.ContactDatabase

class DataContentProvider : ContentProvider() {

    private lateinit var contactDao: ContactDao

    override fun onCreate(): Boolean {
        contactDao = ContactDatabase.getDatabase(context!!).getContactDao()
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        var cursor: Cursor? = null
        if (uriMatcher.match(uri) == URI_CONTACTS_CODE) {
            cursor = contactDao.getAll()
        }
        return cursor
    }

    override fun getType(p0: Uri): String? = "com.gmail.elnora.fet.hw_5_contacts_database_kotlin.contentprovider/object"

    override fun insert(p0: Uri, p1: ContentValues?): Uri? = null

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int = 0

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int = 0

    companion object {
        private const val URI_CONTACTS_CODE = 111
        private const val AUTHORITY = "com.gmail.elnora.fet.hw_5_contacts_database_kotlin"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "data/contacts", URI_CONTACTS_CODE)
        }
    }

}