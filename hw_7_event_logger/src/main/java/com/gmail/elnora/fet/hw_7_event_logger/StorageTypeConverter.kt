package com.gmail.elnora.fet.hw_7_event_logger

object StorageTypeConverter {
    @JvmStatic
    fun fromStorageTypeToString(storageType: StorageType): String = storageType.toString()

    @JvmStatic
    fun fromStringToStorageType(storageType: String): StorageType = when (storageType) {
        StorageType.INTERNAL.toString() -> StorageType.INTERNAL
        StorageType.EXTERNAL.toString() -> StorageType.EXTERNAL
        else -> throw IllegalArgumentException("Invalid storage type specified")
    }
}