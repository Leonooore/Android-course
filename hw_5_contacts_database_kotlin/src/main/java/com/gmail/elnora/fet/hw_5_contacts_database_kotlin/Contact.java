package com.gmail.elnora.fet.hw_5_contacts_database_kotlin;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contact implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "dataType")
    @TypeConverters({DataTypeConverter.class})
    private DataType dataType;

    public Contact(String id, String name, String data, DataType dataType) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.dataType = dataType;
    }

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
