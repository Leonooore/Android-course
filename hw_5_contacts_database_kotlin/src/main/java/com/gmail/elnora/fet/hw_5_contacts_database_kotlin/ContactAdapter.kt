package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.ContactAdapter.ContactViewHolder
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.data.DataType
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.database.Contact
import java.io.Serializable
import java.util.Locale
import kotlin.collections.ArrayList

class ContactAdapter : RecyclerView.Adapter<ContactViewHolder>, Parcelable, Filterable {

    interface OnContactClickListener {
        fun onContactClick(contact: Contact)
    }

    private var contactList: MutableList<Contact> = ArrayList<Contact>()
    private var contactsFilter: MutableList<Contact> = ArrayList<Contact>()
    private lateinit var contactListener: OnContactClickListener

    constructor(contactList: MutableList<Contact>, contactsFilter: MutableList<Contact>, contactListener: OnContactClickListener) {
        this.contactList = contactList
        this.contactsFilter = contactsFilter
        this.contactListener = contactListener
    }

    private constructor(parcel: Parcel) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position], contactListener)
    }

    override fun getItemCount() = contactList.size

    fun addContact(contact: Contact) {
        contactList.add(contact)
        contactsFilter.add(contact)
        notifyDataSetChanged()
    }

    fun editContact(changeContact: Contact) {
        val contactId = changeContact.id
        for (i in contactList.indices) {
            if (contactList[i].id == contactId) {
                contactList.removeAt(i)
                contactList.add(i, changeContact)
                break
            }
        }
        for (i in contactsFilter.indices) {
            if (contactsFilter[i].id == contactId) {
                contactsFilter.removeAt(i)
                contactsFilter.add(i, changeContact)
                break
            }
        }
        notifyDataSetChanged()
    }

    fun removeContact(removeContact: Contact) {
        val contactId = removeContact.id
        for (i in contactList.indices) {
            if (contactList[i].id == contactId) {
                contactList.removeAt(i)
                break
            }
        }
        for (i in contactsFilter.indices) {
            if (contactsFilter[i].id == contactId) {
                contactsFilter.removeAt(i)
                break
            }
        }
        notifyDataSetChanged()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeSerializable(contactList as Serializable)
        parcel.writeSerializable(contactsFilter as Serializable)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filteredList: MutableList<Contact> = ArrayList()
                if (charSequence.isEmpty()) {
                    filteredList.addAll(contactsFilter)
                } else {
                    val filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                    for (contact in contactsFilter) {
                        if (contact.name.toLowerCase(Locale.ROOT).contains(filterPattern) || contact.data.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(contact)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                contactList.clear()
                contactList.addAll((filterResults.values as ArrayList<Contact>))
                notifyDataSetChanged()
            }
        }
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactIcon: ImageView = itemView.findViewById(R.id.imageViewContactIcon)
        private val contactName: TextView = itemView.findViewById(R.id.textViewContactName)
        private val contactData: TextView = itemView.findViewById(R.id.textViewContactPhone)

        fun bind(contact: Contact, contactListener: OnContactClickListener) {
            contactName.text = contact.name
            contactData.text = contact.data
            if (contact.dataType === DataType.PHONE) {
                contactIcon.setImageResource(R.drawable.ic_baseline_contact_phone_56)
            } else {
                contactIcon.setImageResource(R.drawable.ic_baseline_contact_mail_56)
            }
            itemView.setOnClickListener { contactListener.onContactClick(contact) }
        }
    }

    companion object {
        val CREATOR: Parcelable.Creator<ContactAdapter> = object : Parcelable.Creator<ContactAdapter> {
            override fun createFromParcel(parcel: Parcel): ContactAdapter {
                return ContactAdapter(parcel)
            }

            override fun newArray(size: Int): Array<ContactAdapter?> {
                return arrayOfNulls(size)
            }
        }
    }
}