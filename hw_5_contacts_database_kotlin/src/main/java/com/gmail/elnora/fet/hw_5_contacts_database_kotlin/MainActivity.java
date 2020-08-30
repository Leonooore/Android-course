package com.gmail.elnora.fet.hw_5_contacts_database_kotlin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_REQUEST_CODE = 111;
    private static final int EDIT_REQUEST_CODE = 222;
    private List<Contact> contacts = new ArrayList<>();
    private ContactDatabase database;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private FloatingActionButton fabAddContact;
    private TextView textViewNoContacts;
    private TextView textSearchView;
    private Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = ContactDatabase.getDatabase(this);
        contacts = database.getContactDao().getAllContacts();

        recyclerView = findViewById(R.id.contactListRecyclerView);
        textViewNoContacts = findViewById(R.id.textViewNoContacts);
        if (savedInstanceState != null) {
            recyclerView.setAdapter((ContactAdapter) savedInstanceState.getParcelable("ADAPTER"));
            textViewNoContacts.setVisibility(savedInstanceState.getInt("VISIBLE"));
        } else {
            recyclerView.setAdapter(new ContactAdapter(database.getContactDao().getAllContacts(), database.getContactDao().getAllContacts(), new ContactAdapter.OnContactClickListener() {
                @Override
                public void onContactClick(Contact contact) {
                    Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                    intent.putExtra("EDIT_CONTACT", contact);
                    startActivityForResult(intent, EDIT_REQUEST_CODE );
                }
            }));
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        adapter = (ContactAdapter) recyclerView.getAdapter();

        if (adapter != null) {
            setVisibility(adapter.getItemCount());
        }

        setToolbar();
        setButtonAddContactListener();
        setSearchViewListener();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.searchContactsToolbar);
        setSupportActionBar(toolbar);
    }

    private void setVisibility(int itemCount) {
        if (itemCount > 0) {
            textViewNoContacts.setVisibility(View.INVISIBLE);
        } else {
            textViewNoContacts.setVisibility(View.VISIBLE);
        }

    }

    private void setChangeTextViewNoContactsVisibility() {
        if (contacts.size() == 0) {
            textViewNoContacts.setVisibility(View.VISIBLE);
        } else {
            textViewNoContacts.setVisibility(View.INVISIBLE);
        }
    }

    private void setButtonAddContactListener() {
        fabAddContact = findViewById(R.id.buttonAddContact);
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
    }

    private void setSearchViewListener() {
        textSearchView = findViewById(R.id.textSearchView);
        searchView = findViewById(R.id.searchContact);
        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View view) {
                textSearchView.setVisibility(View.INVISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                textSearchView.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == ADD_REQUEST_CODE) {
                Contact contact = (Contact) data.getSerializableExtra("ADD_CONTACT");
                if (contact != null) {
                    database.getContactDao().insert(contact);
                    adapter.addContact(contact);
                }
            } else if (requestCode == EDIT_REQUEST_CODE) {
                Contact changeContact;
                changeContact = (Contact) data.getSerializableExtra("CHANGE_CONTACT");
                Contact deleteContact = (Contact) data.getSerializableExtra("REMOVE_CONTACT");
                if (changeContact != null) {
                    database.getContactDao().update(changeContact);
                    adapter.editContact(changeContact);
                    textViewNoContacts.setVisibility(View.INVISIBLE);
                } else if (deleteContact != null) {
                    database.getContactDao().delete(deleteContact);
                    adapter.removeContact(deleteContact);
                }
            }
            setVisibility(adapter.getItemCount());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        TextView textViewNoContacts = findViewById(R.id.textViewNoContacts);
        outState.putInt("VISIBLE", textViewNoContacts.getVisibility());
        outState.putParcelable("ADAPTER", adapter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
