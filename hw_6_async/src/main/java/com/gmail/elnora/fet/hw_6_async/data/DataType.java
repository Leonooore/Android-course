package com.gmail.elnora.fet.hw_6_async.data;

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
