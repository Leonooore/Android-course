package com.gmail.elnora.fet.hw_8_weather_app.model.repo.currentweather

import com.gmail.elnora.fet.hw_8_weather_app.model.data.CurrentWeatherDataModel
import io.reactivex.Single

interface CurrentWeatherRepository {
    fun getCurrentWeather(city: String, units: String) : Single<CurrentWeatherDataModel>
}