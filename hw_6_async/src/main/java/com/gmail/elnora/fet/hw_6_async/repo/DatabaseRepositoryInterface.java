package com.gmail.elnora.fet.hw_6_async.repo;

import android.content.Context;

import com.gmail.elnora.fet.hw_6_async.database.Contact;

import java.util.List;

public interface DatabaseRepositoryInterface {
    List<Contact> getAllContacts();
    void insert(Contact contact);
    void update(Contact contact);
    void delete(Contact contact);
    void closeDatabaseThreads();
}
