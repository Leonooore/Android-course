package com.gmail.elnora.fet.hw_6_async.repo.async;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.gmail.elnora.fet.hw_6_async.adapter.ContactRecyclerViewAdapter;
import com.gmail.elnora.fet.hw_6_async.database.Contact;
import com.gmail.elnora.fet.hw_6_async.database.ContactDatabase;
import com.gmail.elnora.fet.hw_6_async.repo.DatabaseRepositoryInterface;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompletableFutureThreadPoolExecutor implements DatabaseRepositoryInterface {
    private static final int NUMBER_OF_THREADS = 1;
    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Executor mainExecutor;
    private List<Contact> contacts;
    private ContactDatabase database;
    private ContactRecyclerViewAdapter adapter;
    private TextView textViewNoContacts;

    public CompletableFutureThreadPoolExecutor(ContactDatabase database, ContactRecyclerViewAdapter adapter, List<Contact> contacts, TextView textViewNoContacts, Executor mainExecutor) {
        this.database = database;
        this.adapter = adapter;
        this.contacts = contacts;
        this.textViewNoContacts = textViewNoContacts;
        this.mainExecutor = mainExecutor;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Contact> getAllContacts() {
        CompletableFuture.supplyAsync(new Supplier<List<Contact>>() {
            @Override
            public List<Contact> get() {
                return database.getContactDao().getAllContacts();
            }
        }, threadPoolExecutor)
                .thenAcceptAsync(new Consumer<List<Contact>>() {
                    @Override
                    public void accept(List<Contact> contacts) {
                        adapter.updateContactList(contacts);
                        if (adapter.getItemCount() > 0) {
                            textViewNoContacts.setVisibility(View.INVISIBLE);
                        } else {
                            textViewNoContacts.setVisibility(View.VISIBLE);
                        }
                    }
                }, mainExecutor);
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void insert(Contact contact) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                database.getContactDao().insert(contact);
            }
        }, threadPoolExecutor);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void update(Contact contact) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                database.getContactDao().update(contact);
            }
        }, threadPoolExecutor);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void delete(Contact contact) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                database.getContactDao().delete(contact);
            }
        }, threadPoolExecutor);
    }

    @Override
    public void closeDatabaseThreads() {
        threadPoolExecutor.shutdown();
    }
}