package com.gmail.elnora.fet.hw_5_contacts_database_kotlin;

public enum DataType {
    PHONE("Phone"),
    EMAIL("Email");

    private final String type;

    DataType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
