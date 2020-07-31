package com.gmail.elnora.fet.hw_3_contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private final static int ADD_REQUEST_CODE = 111;
    private final static int EDIT_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contactList);
        recyclerView.setAdapter(new ContactListAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = (ContactListAdapter) recyclerView.getAdapter();

        textViewNoContacts = findViewById(R.id.textViewNoContacts);

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

    public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
        private List<Contact> contactList = new ArrayList<>();

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
