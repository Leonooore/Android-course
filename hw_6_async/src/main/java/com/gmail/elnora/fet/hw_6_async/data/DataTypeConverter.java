package com.gmail.elnora.fet.hw_6_async.data;

import androidx.room.TypeConverter;

public class DataTypeConverter {
    @TypeConverter
    public static String fromDataTypeToString(DataType dataType) {
        if (dataType == null)
            return null;
        return dataType.toString();
    }

    @TypeConverter
    public static DataType fromStringToDataType(String dataType) {
        if (dataType.equals(DataType.EMAIL.toString())) {
            return DataType.EMAIL;
        } else if (dataType.equals(DataType.PHONE.toString())) {
            return DataType.PHONE;
        } else {
            throw new IllegalArgumentException("Couldn't recognize dataType");
        }
    }
}
