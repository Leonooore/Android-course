package com.gmail.elnora.fet.hw_6_async;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gmail.elnora.fet.hw_6_async.adapter.ContactRecyclerViewAdapter;
import com.gmail.elnora.fet.hw_6_async.database.Contact;
import com.gmail.elnora.fet.hw_6_async.database.ContactDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_REQUEST_CODE = 111;
    private static final int EDIT_REQUEST_CODE = 222;
    private static final String THREADPOOLEXECUTER_HANDLER = "THREADPOOLEXECUTER_HANDLER";
    private static final String COMPLETABLEFUTURE_THREADPOOLEXECUTOR = "COPLETABLEFUTURE_THREADPOOLEXECUTOR";
    private static final String RXJAVA = "RXJAVA";
    private List<Contact> contacts = new ArrayList<>();
    private List<Contact> contactsFilter = new ArrayList<>();
    private ContactDatabase database;
    private RecyclerView recyclerView;
    private ContactRecyclerViewAdapter adapter;
    private FloatingActionButton fabAddContact;
    private TextView textViewNoContacts;
    private TextView textSearchView;
    private Toolbar toolbar;
    private SearchView searchView;
    private ImageButton imageButtonSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = ContactDatabase.getDatabase(MainActivity.this);
        contacts = database.getContactDao().getAllContacts();

        initViews();

        if (savedInstanceState != null) {
            recyclerView.setAdapter(savedInstanceState.getParcelable("ADAPTER"));
            textViewNoContacts.setVisibility(savedInstanceState.getInt("VISIBLE"));
        } else {
            contactsFilter = database.getContactDao().getAllContacts();
            recyclerView.setAdapter(new ContactRecyclerViewAdapter(contacts, contactsFilter, new ContactRecyclerViewAdapter.OnContactClickListener() {
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
        adapter = (ContactRecyclerViewAdapter) recyclerView.getAdapter();

        if (adapter != null) {
            setVisibility(adapter.getItemCount());
        }

        setToolbar();
        setButtonAddContactListener();
        setSearchViewListener();
        setImageButtonSettingListener();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.contactListRecyclerView);
        textViewNoContacts = findViewById(R.id.textViewNoContacts);
        toolbar = findViewById(R.id.searchContactsToolbar);
        fabAddContact = findViewById(R.id.buttonAddContact);
        textSearchView = findViewById(R.id.textSearchView);
        searchView = findViewById(R.id.searchContact);
        imageButtonSetting = findViewById(R.id.imageButtonSetting);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setVisibility(int itemCount) {
        if (itemCount > 0) {
            textViewNoContacts.setVisibility(View.INVISIBLE);
        } else {
            textViewNoContacts.setVisibility(View.VISIBLE);
        }
    }

    private void setButtonAddContactListener() {
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
    }

    private void setSearchViewListener() {
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

    private void setImageButtonSettingListener() {
        imageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AsyncSettingsActivity.class);
                startActivity(intent);
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
                    contacts = database.getContactDao().getAllContacts();
                    contactsFilter = database.getContactDao().getAllContacts();
                    adapter.updateContactList((ArrayList<Contact>) contacts, (ArrayList<Contact>) contactsFilter);
                }
            } else if (requestCode == EDIT_REQUEST_CODE) {
                Contact changeContact;
                changeContact = (Contact) data.getSerializableExtra("CHANGE_CONTACT");
                Contact deleteContact = (Contact) data.getSerializableExtra("REMOVE_CONTACT");
                if (changeContact != null) {
                    database.getContactDao().update(changeContact);
                    contacts = database.getContactDao().getAllContacts();
                    contactsFilter = database.getContactDao().getAllContacts();
                    adapter.updateContactList((ArrayList<Contact>) contacts, (ArrayList<Contact>) contactsFilter);
                    textViewNoContacts.setVisibility(View.INVISIBLE);
                } else if (deleteContact != null) {
                    database.getContactDao().delete(deleteContact);
                    contacts = database.getContactDao().getAllContacts();
                    contactsFilter = database.getContactDao().getAllContacts();
                    adapter.updateContactList((ArrayList<Contact>) contacts, (ArrayList<Contact>) contactsFilter);
                }
            }
            setVisibility(adapter.getItemCount());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String loadAsyncType () {
        SharedPreferences sharedPreferences = getSharedPreferences(AsyncSettingsActivity.getPrefName(), MODE_PRIVATE);
        return sharedPreferences.getString(AsyncSettingsActivity.getPrefSaveKey(), THREADPOOLEXECUTER_HANDLER);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
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
