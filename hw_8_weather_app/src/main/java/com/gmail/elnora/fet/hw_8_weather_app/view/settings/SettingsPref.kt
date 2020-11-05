package com.gmail.elnora.fet.hw_8_weather_app.view.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class SettingsPref {

    private var sharedPreferences: SharedPreferences? = null

    fun saveSetting(context: Context, isChecked: Boolean) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        sharedPreferences!!.edit().apply(){
            putBoolean(PREF_SAVE_KEY, isChecked)
            apply()
        }
    }

    fun loadSetting(context: Context): Boolean {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean(PREF_SAVE_KEY, false)
    }

    companion object {
        const val PREF_SAVE_KEY = "METRIC_IMPERIAL"
        const val PREF_NAME = "PrefSettingActivity"
    }
}