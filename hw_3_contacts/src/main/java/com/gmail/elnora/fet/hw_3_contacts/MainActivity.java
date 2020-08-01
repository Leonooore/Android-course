package com.gmail.elnora.fet.hw_3_contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Contact contact;
    private RecyclerView recyclerView;
    private ContactListAdapter adapter;
    private FloatingActionButton fabAddContact;
    private TextView textViewNoContacts;
    TextView textSearchView;
    private Toolbar toolbar;
    private SearchView searchView;
    private static final int ADD_REQUEST_CODE = 111;
    private static final int EDIT_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contactListRecyclerView);
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

        setButtonAddContactListener();

        toolbar = (Toolbar) findViewById(R.id.searchContactsToolbar);
        setSupportActionBar(toolbar);
        setSearchViewListener();
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
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            contact = (Contact)data.getSerializableExtra("ADD_CONTACT");
            if (contact != null) {
                adapter.addContact(contact);
                textViewNoContacts.setVisibility(View.INVISIBLE);
            }
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Contact changeContact;
            changeContact = (Contact)data.getSerializableExtra("CHANGE_CONTACT");
            Contact deleteContact = (Contact)data.getSerializableExtra("REMOVE_CONTACT");
            if (changeContact != null) {
                adapter.editContact(changeContact);
            } else if (deleteContact != null) {
                adapter.removeContact(deleteContact);
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
    @SuppressLint("ParcelCreator")
    public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> implements Parcelable, Filterable {
        private List<Contact> contactList = new ArrayList<>();
        private ArrayList<Contact> contacts = new ArrayList<>();

        public ContactListAdapter() {}

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
            if (contactList != null) {
                return contactList.size();
            } else if (contacts != null) {
                return contacts.size();
            } else
                return 0;
        }

        public void addContact(@NonNull Contact contact) {
            contactList.add(contact);
            contacts.add(contact);
            notifyDataSetChanged();
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
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getId().equals(contactId)) {
                    contacts.remove(i);
                    contacts.add(i, changeContact);
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
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getId().equals(contactId)) {
                    contacts.remove(i);
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

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    List<Contact> filteredList = new ArrayList<>();
                    if (charSequence.length() == 0) {
                        filteredList.addAll(contacts);
                    } else {
                        String filterPattern = charSequence.toString().toLowerCase().trim();
                        for (Contact contact : contacts) {
                            if (contact.getName().toLowerCase().contains(filterPattern) || contact.getData().toLowerCase().contains(filterPattern)) {
                                filteredList.add(contact);
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    contactList.clear();
                    contactList.addAll((ArrayList<Contact>) filterResults.values);
                    notifyDataSetChanged();
                }
            };
        }

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
