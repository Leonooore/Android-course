package com.gmail.elnora.fet.hw_6_async.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gmail.elnora.fet.hw_6_async.data.DataType;
import com.gmail.elnora.fet.hw_6_async.data.DataTypeConverter;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contact implements Serializable {

    @PrimaryKey
    @NonNull
    private final String id;
    private String name;
    private String data;
    @TypeConverters({DataTypeConverter.class})
    private DataType dataType;

    public Contact(String id, String name, String data, DataType dataType) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.dataType = dataType;
    }

    @NonNull
    public String getId() {
        return id;
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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}