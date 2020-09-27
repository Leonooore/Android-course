package com.gmail.elnora.fet.hw_6_async.async;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gmail.elnora.fet.hw_6_async.adapter.ContactRecyclerViewAdapter;
import com.gmail.elnora.fet.hw_6_async.database.Contact;
import com.gmail.elnora.fet.hw_6_async.database.ContactDatabase;
import com.gmail.elnora.fet.hw_6_async.repo.DatabaseRepositoryInterface;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static android.os.Looper.getMainLooper;

public class ThreadPoolExecutorHandler implements DatabaseRepositoryInterface {
    private List<Contact> contacts;
    private List<Contact> contactsFilter;
    private ContactDatabase database;
    private ContactRecyclerViewAdapter adapter;
    private TextView textViewNoContacts;
    private static final int NUMBER_OF_THREADS = 1;
    private static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public ThreadPoolExecutorHandler(ContactDatabase database, ContactRecyclerViewAdapter adapter, List<Contact> contacts, List<Contact> contactsFilter, TextView textViewNoContacts) {
        this.database = database;
        this.adapter = adapter;
        this.contacts = contacts;
        this.contactsFilter = contactsFilter;
        this.textViewNoContacts = textViewNoContacts;
    }

    private Handler handler = new Handler(getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                List<Contact> contacts = (List<Contact>)msg.obj;
                contactsFilter = contacts;
                adapter.updateContactList(contacts, contactsFilter);
                if (adapter.getItemCount() > 0) {
                    textViewNoContacts.setVisibility(View.INVISIBLE);
                } else {
                    textViewNoContacts.setVisibility(View.VISIBLE);
                }
            }
            return false;
        }
    });

    @Override
    public List<Contact> getAllContacts() {
        threadPoolExecutor.execute(() -> {
            contacts = database.getContactDao().getAllContacts();
            handler.sendMessage(handler.obtainMessage(0, contacts));
        });
        return contacts;
    }

    @Override
    public void insert(Contact contact) {
        threadPoolExecutor.execute(() -> {
            if(database != null)
                database.getContactDao().insert(contact);
            handler.sendMessage(handler.obtainMessage(0));
        });
    }

    @Override
    public void update(Contact contact) {
        threadPoolExecutor.execute(() -> {
            database.getContactDao().update(contact);
            handler.sendMessage(handler.obtainMessage(0));
        });
    }

    @Override
    public void delete(Contact contact) {
        threadPoolExecutor.execute(() -> {
            database.getContactDao().delete(contact);
            handler.sendMessage(handler.obtainMessage(0));
        });
    }

    @Override
    public void closeThreads() {
        database.close();
        threadPoolExecutor.shutdown();
    }

}
