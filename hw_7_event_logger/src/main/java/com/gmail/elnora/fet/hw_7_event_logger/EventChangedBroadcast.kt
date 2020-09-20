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
            Intent.ACTION_SCREEN_OFF -> printLog("ACTION_SCREEN_OFF")
        }
    }

    private fun printLog(msg: String) {
        Log.d(TAG, getCurrentDateTime().toString("yyyy/mm/dd_hh:mm") + " - " + msg)
    }
}