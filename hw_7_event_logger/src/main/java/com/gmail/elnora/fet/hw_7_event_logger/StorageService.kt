package com.gmail.elnora.fet.hw_7_event_logger

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.Environment
import android.os.IBinder
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class StorageService: Service() {

    private lateinit var sharedPreferences: SharedPreferences
    private var thread: Thread = Thread()
    private var storageType: StorageType = StorageType.INTERNAL

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra(EventChangedBroadcast.ACTION_KEY)
        val date = intent?.getStringExtra(EventChangedBroadcast.DATE_KEY)
        saveDataToFile(date.toString(), action.toString(), startId)
        return START_REDELIVER_INTENT
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun saveDataToFile(date: String, action: String, startId: Int) {
        val loadStringStorageType = loadStorageType()
        storageType = StorageTypeConverter.fromStringToStorageType(loadStringStorageType.toString())
        thread.apply{
            Thread(Runnable {
                var filesDir: File? = filesDir
                if (storageType == StorageType.EXTERNAL) {
                    if (isExternalStorageWriteable()) {
                        filesDir = getExternalFilesDir(Environment.MEDIA_MOUNTED)
                    } else {
                        Log.d("STORAGE", "External Storage is not available: " + Environment.getExternalStorageState())
                    }
                }
                val filename = "events.txt"
                val file = File(filesDir, filename)
                val event = "$date - $action\n"
                try {
                    FileOutputStream(file, true).apply {
                        write(event.toByteArray())
                        close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                stopSelf(startId)
            }).start()
        }
    }

    private fun isExternalStorageWriteable(): Boolean = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    private fun loadStorageType(): String? {
        sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(MainActivity.PREF_SAVE_KEY, StorageType.INTERNAL.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (thread.isAlive){
            thread.interrupt()
        }
    }
}