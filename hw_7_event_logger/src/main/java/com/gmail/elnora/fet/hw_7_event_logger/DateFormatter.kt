package com.gmail.elnora.fet.hw_7_event_logger

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.util.Calendar

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}
