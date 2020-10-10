package com.gmail.elnora.fet.hw_8_weather_app.repo

import com.gmail.elnora.fet.hw_8_weather_app.data.HoursWeatherDataModel
import io.reactivex.Single

interface HoursWeatherRepository {
    fun getHoursWeather() : Single<List<HoursWeatherDataModel>>
}