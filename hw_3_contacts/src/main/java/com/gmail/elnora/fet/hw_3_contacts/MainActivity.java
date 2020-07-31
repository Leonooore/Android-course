package com.gmail.elnora.fet.hw_3_contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton buttonAddContact;
    private Contact contact;
    private RecyclerView recyclerView;
    private ContactListAdapter adapter;
    private TextView textViewNoContacts;
    private Toolbar toolbar;
    private static final int ADD_REQUEST_CODE = 111;
    private static final int EDIT_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contactList);
        textViewNoContacts = findViewById(R.id.textViewNoContacts);
        if (savedInstanceState != null) {
            recyclerView.setAdapter((ContactListAdapter) savedInstanceState.getParcelable("ADAPTER"));
            textViewNoContacts.setVisibility(savedInstanceState.getInt("VISIBLE"));
        } else {
            recyclerView.setAdapter(new ContactListAdapter());
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        adapter = (ContactListAdapter) recyclerView.getAdapter();

        toolbar = (Toolbar) findViewById(R.id.searchContacts);
        setSupportActionBar(toolbar);

        setButtonAddContactListener();
    }

    private void setButtonAddContactListener() {
        buttonAddContact = findViewById(R.id.buttonAddContact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            contact = (Contact)data.getSerializableExtra("ADD_CONTACT");
            if (contact != null) {
                adapter.addContact(contact);
                textViewNoContacts.setVisibility(View.INVISIBLE);
            }
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Contact changeContact;
            changeContact = (Contact)data.getSerializableExtra("CHANGE_CONTACT");
            contact = (Contact)data.getSerializableExtra("REMOVE_CONTACT");
            if (changeContact != null) {
                adapter.editContact(changeContact);
            } else if (contact != null) {
                adapter.removeContact(contact);
            }
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

    /*ContactListAdapter*/
    public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> implements Parcelable {
        private List<Contact> contactList = new ArrayList<>();

        public ContactListAdapter() {}
        public ContactListAdapter(Parcel in) {}

        @NonNull
        @Override
        public ContactListAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
            return new ContactListAdapter.ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactListAdapter.ContactViewHolder holder, int position) {
            holder.bind(contactList.get(position));
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }

        public void addContact(@NonNull Contact contact) {
            contactList.add(contact);
            notifyItemChanged(contactList.indexOf(contact));
        }

        void editContact(Contact changeContact) {
            String contactId = changeContact.getId();
            for (int i = 0; i < contactList.size(); i++) {
                if (contactList.get(i).getId().equals(contactId)) {
                    contactList.remove(i);
                    contactList.add(i, changeContact);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        void removeContact(Contact removeContact) {
            String contactId = removeContact.getId();
            for (int i = 0; i < contactList.size(); i++) {
                if (contactList.get(i).getId().equals(contactId)) {
                    contactList.remove(i);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeSerializable((Serializable) contactList);
        }

        public final Creator<ContactListAdapter> CREATOR = new Creator<ContactListAdapter>() {
            @Override
            public ContactListAdapter createFromParcel(Parcel in) {
                return new ContactListAdapter(in);
            }

            @Override
            public ContactListAdapter[] newArray(int size) {
                return new ContactListAdapter[size];
            }
        };

        /*ContactViewHolder*/
        public class ContactViewHolder extends RecyclerView.ViewHolder {
            private ImageView contactIcon;
            private TextView contactName;
            private TextView contactData;

            public ContactViewHolder(@NonNull View itemView) {
                super(itemView);
                contactIcon = itemView.findViewById(R.id.imageViewContactIcon);
                contactName = itemView.findViewById(R.id.textViewContactName);
                contactData = itemView.findViewById(R.id.textViewContactPhone);
            }

            public void bind(final Contact contact) {
                contactName.setText(contact.getName());
                contactData.setText(contact.getData());

                if (contact.getDataType() == DataType.PHONE) {
                    contactIcon.setImageResource(R.drawable.ic_baseline_contact_phone_56);
                } else {
                    contactIcon.setImageResource(R.drawable.ic_baseline_contact_mail_56);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                        intent.putExtra("EDIT_CONTACT", (Serializable) contactList.get(position));
                        startActivityForResult(intent, EDIT_REQUEST_CODE );
                        Log.d("MSG", "Checking  onclick contact item");
                    }
                });
            }

        }
    }

}
