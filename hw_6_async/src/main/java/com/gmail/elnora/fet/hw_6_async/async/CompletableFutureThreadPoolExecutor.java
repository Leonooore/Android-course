package com.gmail.elnora.fet.hw_6_async.async;

import android.content.Context;

import com.gmail.elnora.fet.hw_6_async.database.Contact;
import com.gmail.elnora.fet.hw_6_async.repo.DatabaseRepositoryInterface;

import java.util.List;

public class CompletableFutureThreadPoolExecutor implements DatabaseRepositoryInterface {

    @Override
    public List<Contact> getAllContacts() {
        return null;
    }

    @Override
    public void insert(Contact contact) {

    }

    @Override
    public void update(Contact contact) {

    }

    @Override
    public void delete(Contact contact) {

    }

    @Override
    public void closeThreads() {

    }
}
