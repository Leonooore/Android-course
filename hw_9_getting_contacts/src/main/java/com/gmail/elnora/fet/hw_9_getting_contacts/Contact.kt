package com.gmail.elnora.fet.hw_9_getting_contacts

import java.io.Serializable

data class Contact(
        val id: String,
        var name: String,
        var data: String,
        var dataType: String
) : Serializable