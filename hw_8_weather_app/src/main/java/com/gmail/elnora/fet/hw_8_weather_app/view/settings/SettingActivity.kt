package com.gmail.elnora.fet.hw_8_weather_app.view.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gmail.elnora.fet.hw_8_weather_app.R
import kotlinx.android.synthetic.main.activity_setting.toolbar
import kotlinx.android.synthetic.main.activity_setting.viewSwitchUnit

class SettingActivity : AppCompatActivity() {

    private val settings: SettingsPref by lazy { SettingsPref() }
    private var unit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        unit = settings.loadSetting(this)
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
        viewSwitchUnit.setOnCheckedChangeListener { _, isChecked -> settings.saveSetting(this, isChecked) }
    }

}