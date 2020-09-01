package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import androidx.room.TypeConverter

object DataTypeConverter {
    @JvmStatic
    @TypeConverter
    fun fromDataTypeToString(dataType: DataType): String = dataType.toString()

    @JvmStatic
    @TypeConverter
    fun fromStringToDataType(dataType: String): DataType {
        return when (dataType) {
            DataType.EMAIL.toString() -> {
                DataType.EMAIL
            }
            DataType.PHONE.toString() -> {
                DataType.PHONE
            }
            else -> {
                throw IllegalArgumentException("Couldn't recognize dataType")
            }
        }
    }
}