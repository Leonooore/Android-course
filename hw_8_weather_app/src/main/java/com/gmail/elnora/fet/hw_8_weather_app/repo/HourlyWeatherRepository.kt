package com.gmail.elnora.fet.hw_8_weather_app.repo

import com.gmail.elnora.fet.hw_8_weather_app.data.HourlyWeatherDataModel
import io.reactivex.Single

interface HourlyWeatherRepository {
    fun getHoursWeather(city: String, units: String) : Single<List<HourlyWeatherDataModel>>
}