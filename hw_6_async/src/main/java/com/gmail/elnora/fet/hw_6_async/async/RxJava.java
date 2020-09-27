package com.gmail.elnora.fet.hw_6_async.async;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.gmail.elnora.fet.hw_6_async.adapter.ContactRecyclerViewAdapter;
import com.gmail.elnora.fet.hw_6_async.database.Contact;
import com.gmail.elnora.fet.hw_6_async.database.ContactDatabase;
import com.gmail.elnora.fet.hw_6_async.repo.DatabaseRepositoryInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJava implements DatabaseRepositoryInterface {
    private Disposable disposable;
    private List<Contact> contacts;
    private ContactDatabase database;
    private ContactRecyclerViewAdapter adapter;
    private TextView textViewNoContacts;

    public RxJava(ContactDatabase database, ContactRecyclerViewAdapter adapter, List<Contact> contacts, TextView textViewNoContacts) {
        this.database = database;
        this.adapter = adapter;
        this.contacts = contacts;
        this.textViewNoContacts = textViewNoContacts;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Contact> getAllContacts() {
        disposable = Observable.create((ObservableOnSubscribe<List<Contact>>) emitter -> {
            emitter.onNext(database.getContactDao().getAllContacts());
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> {
                    adapter.updateContactList(contacts);
                    if (adapter.getItemCount() > 0) {
                        textViewNoContacts.setVisibility(View.INVISIBLE);
                    } else {
                        textViewNoContacts.setVisibility(View.VISIBLE);
                    }
                });
        return null;
    }

    @Override
    public void insert(Contact contact) {
        disposable = Observable.create((ObservableOnSubscribe<Contact>) emitter -> database.getContactDao().insert(contact))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void update(Contact contact) {
        disposable = Observable.create((ObservableOnSubscribe<Contact>) emitter -> database.getContactDao().update(contact))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void delete(Contact contact) {
        disposable = Observable.create((ObservableOnSubscribe<Contact>) emitter -> database.getContactDao().delete(contact))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void closeDatabaseThreads() {
        disposable.dispose();
    }
}
