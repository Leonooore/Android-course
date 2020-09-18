package com.gmail.elnora.fet.hw_7_event_logger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG: String = "EventChangedBroadcast"

class EventChangedBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> printLog("ACTION_AIRPLANE_MODE_CHANGED")
            Intent.ACTION_LOCALE_CHANGED -> printLog("ACTION_LOCALE_CHANGED")
            Intent.ACTION_TIMEZONE_CHANGED -> printLog("ACTION_TIMEZONE_CHANGED")
        }
    }

    private fun printLog(msg: String) {
        Log.d(TAG, msg)
    }
}