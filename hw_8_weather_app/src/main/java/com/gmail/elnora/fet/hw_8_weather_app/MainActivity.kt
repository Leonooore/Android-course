package com.gmail.elnora.fet.hw_8_weather_app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMainFragmentWeather(savedInstanceState)
    }

    private fun showMainFragmentWeather(savedInstanceState: Bundle?) {
        if (findViewById<View?>(R.id.fragmentContainer) != null) {
            if (savedInstanceState != null) {
                return
            }
            showFragment(WeatherFragment.newInstance(), WeatherFragment.TAG)
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commit()
    }
}
