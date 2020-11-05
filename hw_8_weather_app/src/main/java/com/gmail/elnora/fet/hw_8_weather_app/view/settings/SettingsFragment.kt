package com.gmail.elnora.fet.hw_8_weather_app.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.elnora.fet.hw_8_weather_app.R

import kotlinx.android.synthetic.main.fragment_settings.viewSwitchUnit

class SettingsFragment : Fragment() {

    private val settings: SettingsPref by lazy { SettingsPref() }
    private var unit = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unit = settings.loadSetting(context!!)
        viewSwitchUnit.isChecked = unit
        setOnCheckedChangeListener()
    }

    private fun setOnCheckedChangeListener() {
        viewSwitchUnit.setOnCheckedChangeListener { _, isChecked -> settings.saveSetting(context!!, isChecked) }
    }

    companion object {
        const val TAG: String = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

}