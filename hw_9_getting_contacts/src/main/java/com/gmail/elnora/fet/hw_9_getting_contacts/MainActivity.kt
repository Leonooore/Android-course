package com.gmail.elnora.fet.hw_9_getting_contacts

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.searchContactsToolbar
import kotlinx.android.synthetic.main.activity_main.fabShowContacts
import kotlinx.android.synthetic.main.activity_main.contactListRecyclerView
import kotlinx.android.synthetic.main.activity_main.textViewNoContacts
import kotlinx.android.synthetic.main.activity_main.searchContact
import kotlinx.android.synthetic.main.activity_main.textSearchView

class MainActivity : AppCompatActivity() {

    private var contactList: MutableList<Contact> = ArrayList()
    private lateinit var contact: Contact
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(searchContactsToolbar)
        setSearchViewListener()
        setRecyclerList()
        fabShowContacts.setOnClickListener { getContactDatabase() }
    }

    private fun setRecyclerList() {
        adapter = ContactAdapter(contactList)
        contactListRecyclerView.adapter = adapter
        contactListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getContactDatabase() {
        val cursor = contentResolver.query(Uri.parse(URI_PATH), null, null, null, null)
        if (cursor != null) {
            val contactId = cursor.getColumnIndex("id")
            val contactName = cursor.getColumnIndex("name")
            val contactData = cursor.getColumnIndex("data")
            val contactDataType = cursor.getColumnIndex("dataType")
            while (cursor.moveToNext()) {
                contact = Contact(
                        cursor.getString(contactId),
                        cursor.getString(contactName),
                        cursor.getString(contactData),
                        cursor.getString(contactDataType)
                )
                adapter.updateContactList(contact)
            }
            cursor.close()
        }
        setVisibility(adapter.itemCount)
    }

    private fun setVisibility(itemCount: Int) {
        if (itemCount > 0) {
            textViewNoContacts.visibility = View.INVISIBLE
        } else {
            textViewNoContacts.visibility = View.VISIBLE
        }
    }

    private fun setSearchViewListener() {
        searchContact.setOnSearchClickListener { textSearchView.visibility = View.INVISIBLE }
        searchContact.setOnCloseListener {
            textSearchView.visibility = View.VISIBLE
            return@setOnCloseListener false
        }
        searchContact.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    companion object {
        private const val URI_PATH = "content://com.gmail.elnora.fet.hw_5_contacts_database_kotlin/data/contacts"
    }
}
