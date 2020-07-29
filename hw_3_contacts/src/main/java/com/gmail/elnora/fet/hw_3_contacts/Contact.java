package com.gmail.elnora.fet.hw_3_contacts;

import java.io.Serializable;

public class Contact implements Serializable {
    private String id;
    private String name;
    private String data;

    public Contact(String id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
