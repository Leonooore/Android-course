package com.gmail.elnora.fet.hw_8_weather_app

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.toolbar
import kotlinx.android.synthetic.main.activity_setting.viewSwitchUnit

class SettingActivity : AppCompatActivity() {
    private var unit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        unit = loadSetting()
        viewSwitchUnit.isChecked = unit
        setOnCheckedChangeListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setOnCheckedChangeListener() {
        viewSwitchUnit!!.setOnCheckedChangeListener { _, isChecked ->
            saveSetting(isChecked)
        }
    }

    private fun saveSetting(isChecked: Boolean) {
        val shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val edit = shared.edit()
        edit.putBoolean(PREF_SAVE_KEY, isChecked)
        edit.apply()
    }

    private fun loadSetting(): Boolean {
        val shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return shared.getBoolean(PREF_SAVE_KEY, false)
    }

    companion object {
        const val PREF_SAVE_KEY = "METRIC_IMPERIAL"
        const val PREF_NAME = "PrefSettingActivity"
    }
}