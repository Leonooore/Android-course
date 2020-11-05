package com.gmail.elnora.fet.hw_9_getting_contacts

enum class DataType(private val type: String) {
    PHONE("Phone"),
    EMAIL("Email");

    override fun toString(): String = type
}