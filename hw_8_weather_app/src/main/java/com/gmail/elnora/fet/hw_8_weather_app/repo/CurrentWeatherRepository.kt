package com.gmail.elnora.fet.hw_8_weather_app.repo

import com.gmail.elnora.fet.hw_8_weather_app.data.CurrentWeatherDataModel
import io.reactivex.Single

interface CurrentWeatherRepository {
    fun getCurrentWeather(city: String, units: String) : Single<CurrentWeatherDataModel>
}