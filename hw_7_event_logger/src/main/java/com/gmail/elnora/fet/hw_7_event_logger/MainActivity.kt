package com.gmail.elnora.fet.hw_7_event_logger

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.viewRadioButtonInternalStorage
import kotlinx.android.synthetic.main.activity_main.viewRadioButtonExternalStorage
import kotlinx.android.synthetic.main.activity_main.radioGroupStorageType

class MainActivity : AppCompatActivity() {
    private val broadcastReceiver: BroadcastReceiver = EventChangedBroadcast()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var storageType: StorageType

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loadStringStorageType = loadStorageType()
        storageType = StorageTypeConverter.fromStringToStorageType(loadStringStorageType.toString())
        when(storageType) {
            StorageType.INTERNAL -> viewRadioButtonInternalStorage.isChecked = true
            StorageType.EXTERNAL -> viewRadioButtonExternalStorage.isChecked = true
        }

        setChangeStorageTypeListener()
        createReceiver()
    }

    private fun createReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_LOCALE_CHANGED)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun setChangeStorageTypeListener() {
        radioGroupStorageType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.viewRadioButtonInternalStorage -> storageType = StorageType.INTERNAL
                R.id.viewRadioButtonExternalStorage -> storageType = StorageType.EXTERNAL
            }
            saveStorageType(storageType)
        }
    }

    private fun saveStorageType(storageType: StorageType) {
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putString(PREF_SAVE_KEY, StorageTypeConverter.fromStorageTypeToString(storageType))
        edit.apply()
    }

    private fun loadStorageType(): String? {
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(PREF_SAVE_KEY, StorageType.INTERNAL.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
    
    companion object {
        const val PREF_SAVE_KEY = "STORAGE_TYPE_KEY"
        const val PREF_NAME = "storageTypePref"
    }
}
