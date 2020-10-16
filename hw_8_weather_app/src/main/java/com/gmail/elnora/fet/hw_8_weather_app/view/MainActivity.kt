package com.gmail.elnora.fet.hw_8_weather_app.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gmail.elnora.fet.hw_8_weather_app.R
import com.gmail.elnora.fet.hw_8_weather_app.view.settings.SettingActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMainFragmentWeather()
    }

    private fun showMainFragmentWeather() = showFragment(WeatherFragment.newInstance(), WeatherFragment.TAG)

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.itemSettingShowMessage) {
            startSettingActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSettingActivity() {
        val intent = Intent(this@MainActivity, SettingActivity::class.java)
        startActivity(intent)
    }

}
