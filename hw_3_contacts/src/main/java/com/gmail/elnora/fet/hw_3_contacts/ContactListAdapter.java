package com.gmail.elnora.fet.hw_3_contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    private List<Contact> contactList = new ArrayList<>();

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
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

        public void bind(final Contact contact) {
            contactName.setText(contact.getName());
            contactData.setText(contact.getData());

            if (contact.getDataType() == DataType.PHONE) {
                contactIcon.setImageResource(R.drawable.ic_baseline_contact_phone_56);
            } else {
                contactIcon.setImageResource(R.drawable.ic_baseline_contact_mail_56);
            }

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ContactsListAdapter.this, EditContactActivity.class);
                    intent.putExtra("EDIT_CONTACT", contactList.get(position));
                    startActivityForResult(intent, 21000);
                    Log.d("MSG");
                }
            });*/
        }
    }
}
