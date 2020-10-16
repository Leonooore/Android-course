package com.gmail.elnora.fet.hw_8_weather_app.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.gmail.elnora.fet.hw_8_weather_app.R
import com.gmail.elnora.fet.hw_8_weather_app.view.adapter.HourlyWeatherListAdapter
import com.gmail.elnora.fet.hw_8_weather_app.model.data.dataConverter.CurrentWeatherDataModelConverter
import com.gmail.elnora.fet.hw_8_weather_app.model.data.dataConverter.HourlyWeatherDataModelConverter
import com.gmail.elnora.fet.hw_8_weather_app.model.repo.currentWeather.CurrentWeatherRepositoryImpl
import com.gmail.elnora.fet.hw_8_weather_app.model.repo.hourlyWeather.HoursWeatherRepositoryImpl
import com.gmail.elnora.fet.hw_8_weather_app.presenter.WeatherPresenterImpl
import com.gmail.elnora.fet.hw_8_weather_app.presenter.hourlyWeather.HourlyWeatherViewMapper
import com.gmail.elnora.fet.hw_8_weather_app.presenter.WeatherViewPresenter
import com.gmail.elnora.fet.hw_8_weather_app.presenter.currentWeather.CurrentWeatherViewMapper
import com.gmail.elnora.fet.hw_8_weather_app.presenter.currentWeather.CurrentWeatherViewModel
import com.gmail.elnora.fet.hw_8_weather_app.presenter.hourlyWeather.HourlyWeatherViewModel
import com.gmail.elnora.fet.hw_8_weather_app.view.settings.SettingsPref
import kotlinx.android.synthetic.main.fragment_weather.viewTextCity
import kotlinx.android.synthetic.main.fragment_weather.viewTextTemperature
import kotlinx.android.synthetic.main.fragment_weather.viewTextWeatherDescription
import kotlinx.android.synthetic.main.fragment_weather.viewImageWeatherIcon
import kotlinx.android.synthetic.main.fragment_weather.viewRecyclerHourWeatherList
import okhttp3.OkHttpClient

class WeatherFragment : Fragment(), WeatherViewPresenter {
    private val itemList = mutableListOf<HourlyWeatherViewModel>()
    private val settings: SettingsPref by lazy { SettingsPref() }
    private var city: String =  "Minsk"
    private var unit: String = "metric"

    private val presenter: WeatherPresenterImpl = WeatherPresenterImpl(
            weatherViewPresenter = this,
            currentWeatherRepository = CurrentWeatherRepositoryImpl(OkHttpClient(), CurrentWeatherDataModelConverter()),
            currentWeatherMapper = CurrentWeatherViewMapper(),
            hourlyWeatherRepository = HoursWeatherRepositoryImpl(OkHttpClient(), HourlyWeatherDataModelConverter()),
            hourlyWeatherViewMapper = HourlyWeatherViewMapper()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_weather, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemList()
    }

    private fun initItemList() {
        viewRecyclerHourWeatherList.apply {
            adapter = HourlyWeatherListAdapter(itemList)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        unit = initUnit()
        presenter.apply {
            fetchCurrentWeather(city, unit)
            fetchHoursWeather(city, unit)
        }
    }

    override fun showCurrentWeather(currentWeather: CurrentWeatherViewModel) {
        viewTextCity.text = currentWeather.city
        viewTextTemperature.text = tempConvert(currentWeather.temp, unit)
        viewTextWeatherDescription.text = currentWeather.weatherDescription
        val iconCode: String = currentWeather.icon
        Glide.with(this)
                .load("https://openweathermap.org/img/w/$iconCode.png")
                .into(viewImageWeatherIcon)
    }

    override fun showHoursWeather(list: List<HourlyWeatherViewModel>) {
        (viewRecyclerHourWeatherList.adapter as? HourlyWeatherListAdapter)?.updateItemList(list, unit)
    }

    override fun onError(errorMessage: String) {
        Log.d("WeatherFragment", errorMessage)
    }

    private fun initUnit(): String = if (settings.loadSetting(context!!)) UNITS_IMPERIAL else UNITS_METRIC

    private fun tempConvert(temp: String, unit: String): String = if (unit == "metric") "$temp °C" else "$temp °F"

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

    companion object {
        const val TAG: String = "WeatherFragment"
        const val UNITS_METRIC: String = "metric"
        const val UNITS_IMPERIAL: String = "imperial"
        fun newInstance() = WeatherFragment()
    }

}