package com.gmail.elnora.fet.hw_3_contacts;

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
import android.os.Parcel;
import android.os.Parcelable;
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
    private static final int ADD_REQUEST_CODE = 111;
    private static final int EDIT_REQUEST_CODE = 222;
    private List<Contact> contacts = new ArrayList<>();
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

        recyclerView = findViewById(R.id.contactListRecyclerView);
        textViewNoContacts = findViewById(R.id.textViewNoContacts);
        if (savedInstanceState != null) {
            recyclerView.setAdapter((ContactAdapter) savedInstanceState.getParcelable("ADAPTER"));
            textViewNoContacts.setVisibility(savedInstanceState.getInt("VISIBLE"));
        } else {
            recyclerView.setAdapter(new ContactAdapter(contacts, new ContactAdapter.OnContactClickListener() {
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

        setButtonAddContactListener();

        toolbar = findViewById(R.id.searchContactsToolbar);
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
            Contact contact = (Contact)data.getSerializableExtra("ADD_CONTACT");
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

    /*ContactAdapter*/
    public static class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Parcelable, Filterable {

        interface OnContactClickListener {
            void onContactClick(Contact contact);
        }

        private List<Contact> contactList;
        private List<Contact> contactsFilter = new ArrayList<>();
        private OnContactClickListener contactListener;

        public ContactAdapter(List<Contact> contactList, OnContactClickListener contactListener) {
            this.contactList = contactList;
            this.contactListener = contactListener;
        }

        protected ContactAdapter(Parcel in) {}

        public static final Creator<ContactAdapter> CREATOR = new Creator<ContactAdapter>() {
            @Override
            public ContactAdapter createFromParcel(Parcel in) {
                return new ContactAdapter(in);
            }

            @Override
            public ContactAdapter[] newArray(int size) {
                return new ContactAdapter[size];
            }
        };

        @NonNull
        @Override
        public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
            return new ContactAdapter.ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
            holder.bind(contactList.get(position), contactListener);
        }

        @Override
        public int getItemCount() {
            if (contactList != null) {
                return contactList.size();
            } else if (contactsFilter != null) {
                return contactsFilter.size();
            } else
                return 0;
        }

        public void addContact(@NonNull Contact contact) {
            contactList.add(contact);
            contactsFilter.add(contact);
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
            for (int i = 0; i < contactsFilter.size(); i++) {
                if (contactsFilter.get(i).getId().equals(contactId)) {
                    contactsFilter.remove(i);
                    contactsFilter.add(i, changeContact);
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
            for (int i = 0; i < contactsFilter.size(); i++) {
                if (contactsFilter.get(i).getId().equals(contactId)) {
                    contactsFilter.remove(i);
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
                        filteredList.addAll(contactsFilter);
                    } else {
                        String filterPattern = charSequence.toString().toLowerCase().trim();
                        for (Contact contact : contactsFilter) {
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
                    contactList.addAll((ArrayList<Contact>)filterResults.values);
                    notifyDataSetChanged();
                }
            };
        }

        /*ContactViewHolder*/
        public static class ContactViewHolder extends RecyclerView.ViewHolder {
            private ImageView contactIcon;
            private TextView contactName;
            private TextView contactData;

            public ContactViewHolder(@NonNull View itemView) {
                super(itemView);
                contactIcon = itemView.findViewById(R.id.imageViewContactIcon);
                contactName = itemView.findViewById(R.id.textViewContactName);
                contactData = itemView.findViewById(R.id.textViewContactPhone);
            }

            public void bind(final Contact contact, final OnContactClickListener contactListener) {
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
                        contactListener.onContactClick(contact);
                    }
                });
            }

        }
    }

}
