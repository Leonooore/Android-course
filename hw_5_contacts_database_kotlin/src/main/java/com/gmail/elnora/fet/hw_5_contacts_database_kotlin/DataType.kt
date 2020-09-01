package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

enum class DataType(private val type: String) {
    PHONE("Phone"),
    EMAIL("Email");

    override fun toString(): String = type
}