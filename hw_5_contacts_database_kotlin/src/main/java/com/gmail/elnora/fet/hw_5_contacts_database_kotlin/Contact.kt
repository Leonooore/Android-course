package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "contacts")
data class Contact(@field:PrimaryKey val id: String,
                   var name: String,
                   var data: String,
                   @field:TypeConverters(DataTypeConverter::class) var dataType: DataType)
    : Serializable