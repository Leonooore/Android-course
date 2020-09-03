package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.ContactAdapter.OnContactClickListener
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.ContactDatabase.Companion.getDatabase
import kotlinx.android.synthetic.main.activity_main.searchContactsToolbar
import kotlinx.android.synthetic.main.activity_main.searchContact
import kotlinx.android.synthetic.main.activity_main.textSearchView
import kotlinx.android.synthetic.main.activity_main.buttonAddContact
import kotlinx.android.synthetic.main.activity_main.textViewNoContacts
import kotlinx.android.synthetic.main.activity_main.contactListRecyclerView

class MainActivity : AppCompatActivity() {

    private var contacts: MutableList<Contact> = ArrayList()
    private lateinit var database: ContactDatabase
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = getDatabase(applicationContext)
        contacts = database.getContactDao().getAllContacts()
        if (savedInstanceState != null) {
            contactListRecyclerView.adapter = savedInstanceState.getParcelable<Parcelable>("ADAPTER") as ContactAdapter
            textViewNoContacts.visibility = savedInstanceState.getInt("VISIBLE")
        } else {
            contactListRecyclerView.adapter = ContactAdapter(contacts, contacts, object : OnContactClickListener {
                override fun onContactClick(contact: Contact) {
                    val intent = Intent(this@MainActivity, EditContactActivity::class.java)
                    intent.putExtra("EDIT_CONTACT", contact)
                    startActivityForResult(intent, EDIT_REQUEST_CODE)
                }
            })
        }
        contactListRecyclerView.layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager(this)
        } else {
            GridLayoutManager(this, 2)
        }
        adapter = contactListRecyclerView.adapter as ContactAdapter
        setVisibility(adapter.itemCount)
        setSupportActionBar(searchContactsToolbar)
        setButtonAddContactListener()
        setSearchViewListener()
    }

    private fun setVisibility(itemCount: Int) {
        if (itemCount > 0) {
            textViewNoContacts.visibility = View.INVISIBLE
        } else {
            textViewNoContacts.visibility = View.VISIBLE
        }
    }

    private fun setButtonAddContactListener() {
        buttonAddContact.setOnClickListener {
            val intent = Intent(this@MainActivity, AddContactActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_REQUEST_CODE) {
                val contact = data.getSerializableExtra("ADD_CONTACT") as Contact?
                if (contact != null) {
                    database.getContactDao().insert(contact)
                    adapter.addContact(contact)
                }
            } else if (requestCode == EDIT_REQUEST_CODE) {
                val changeContact: Contact? = data.getSerializableExtra("CHANGE_CONTACT") as Contact?
                val deleteContact = data.getSerializableExtra("REMOVE_CONTACT") as Contact?
                if (changeContact != null) {
                    database.getContactDao().update(changeContact)
                    adapter.editContact(changeContact)
                    textViewNoContacts.visibility = View.INVISIBLE
                } else if (deleteContact != null) {
                    database.getContactDao().delete(deleteContact)
                    adapter.removeContact(deleteContact)
                }
            }
            setVisibility(adapter.itemCount)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("VISIBLE", textViewNoContacts.visibility)
        outState.putParcelable("ADAPTER", adapter)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    companion object {
        private const val ADD_REQUEST_CODE = 111
        private const val EDIT_REQUEST_CODE = 222
    }
}