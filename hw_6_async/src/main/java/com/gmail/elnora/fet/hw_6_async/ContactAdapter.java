package com.gmail.elnora.fet.hw_6_async;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Parcelable, Filterable {

    interface OnContactClickListener {
        void onContactClick(Contact contact);
    }

    private List<Contact> contactList = new ArrayList<>();
    private List<Contact> contactsFilter = new ArrayList<>();
    private OnContactClickListener contactListener;

    public ContactAdapter(List<Contact> contactList, List<Contact> contactsFilter, OnContactClickListener contactListener) {
        this.contactList = contactList;
        this.contactsFilter = contactsFilter;
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
        parcel.writeSerializable((Serializable) contactsFilter);
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

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactIcon;
        private TextView contactName;
        private TextView contactData;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactIcon = itemView.findViewById(R.id.imageViewContactIcon);
            contactName = itemView.findViewById(R.id.textViewContactName);
            contactData = itemView.findViewById(R.id.textViewContactData);
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
