package com.gmail.elnora.fet.hw_7_event_logger

enum class StorageType(private val type: String) {
    INTERNAL("INTERNAL"),
    EXTERNAL("EXTERNAL");

    override fun toString(): String = type
}