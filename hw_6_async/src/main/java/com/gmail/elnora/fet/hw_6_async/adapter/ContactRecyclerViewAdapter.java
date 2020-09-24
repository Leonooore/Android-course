package com.gmail.elnora.fet.hw_6_async.adapter;

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

import com.gmail.elnora.fet.hw_6_async.R;
import com.gmail.elnora.fet.hw_6_async.database.Contact;
import com.gmail.elnora.fet.hw_6_async.data.DataType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> implements Parcelable, Filterable {

    public interface OnContactClickListener {
        void onContactClick(Contact contact);
    }

    private List<Contact> contactList = new ArrayList<>();
    private List<Contact> contactsFilter = new ArrayList<>();
    private OnContactClickListener contactListener;

    public ContactRecyclerViewAdapter(List<Contact> contactList, List<Contact> contactsFilter, OnContactClickListener contactListener) {
        this.contactList = contactList;
        this.contactsFilter = contactsFilter;
        this.contactListener = contactListener;
    }

    protected ContactRecyclerViewAdapter(Parcel in) {}

    @NonNull
    @Override
    public ContactRecyclerViewAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactRecyclerViewAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerViewAdapter.ContactViewHolder holder, int position) {
        holder.bind(contactList.get(position), contactListener);
    }

    @Override
    public int getItemCount() {
        return contactList != null ? contactList.size() : 0;
    }

    public void updateContactList(ArrayList<Contact> contacts, ArrayList<Contact> contactsFilter) {
        contactList = contacts;
        this.contactsFilter = contactsFilter;
        notifyDataSetChanged();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable((Serializable) contactList);
        parcel.writeSerializable((Serializable) contactsFilter);
    }

    public static final Creator<ContactRecyclerViewAdapter> CREATOR = new Creator<ContactRecyclerViewAdapter>() {
        @Override
        public ContactRecyclerViewAdapter createFromParcel(Parcel in) {
            return new ContactRecyclerViewAdapter(in);
        }

        @Override
        public ContactRecyclerViewAdapter[] newArray(int size) {
            return new ContactRecyclerViewAdapter[size];
        }
    };

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
