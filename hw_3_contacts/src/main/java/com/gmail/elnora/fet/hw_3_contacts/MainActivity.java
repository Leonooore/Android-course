package com.gmail.elnora.fet.hw_3_contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton buttonAddContact;
    private Contact contact;
    private RecyclerView recyclerView;
    private ContactListAdapter adapter;
    private TextView textViewNoContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contactList);
        recyclerView.setAdapter(new ContactListAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        textViewNoContacts = findViewById(R.id.textViewNoContacts);

        setButtonAddContactListener();
    }

    private void setButtonAddContactListener() {
        buttonAddContact = findViewById(R.id.buttonAddContact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, 18000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 18000 && resultCode == Activity.RESULT_OK && data != null) {
            contact = (Contact)data.getSerializableExtra("ADD_CONTACT");
            adapter = (ContactListAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.addContact(contact);
                textViewNoContacts.setVisibility(View.INVISIBLE);
            }
            Log.d("MainActivity", "Name: " + contact.getName() + " | Data: " + contact.getData() + " | dataType: " + contact.getDataType());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
