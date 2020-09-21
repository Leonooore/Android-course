package com.gmail.elnora.fet.hw_7_event_logger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class EventChangedBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val date = getCurrentDateTime().toString("yyyy/mm/dd_hh:mm")
        when(action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED,
            Intent.ACTION_LOCALE_CHANGED,
            Intent.ACTION_SCREEN_OFF -> {
                Toast.makeText(context, "$date - $action", Toast.LENGTH_SHORT).show()
                startStorageService(context, date, action)
            }
        }
    }

    private fun startStorageService(context: Context, date: String, action: String){
        val intentService = Intent(context, StorageService::class.java)
        intentService.putExtra(ACTION_KEY, action)
        intentService.putExtra(DATE_KEY, date)
        context.startService(intentService)
    }

    companion object {
        const val ACTION_KEY = "ACTION_KEY"
        const val DATE_KEY = "DATE_KEY"
    }
}