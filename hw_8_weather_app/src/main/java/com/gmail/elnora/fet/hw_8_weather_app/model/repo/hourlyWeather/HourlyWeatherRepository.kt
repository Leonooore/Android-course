package com.gmail.elnora.fet.hw_8_weather_app.model.repo.hourlyWeather

import com.gmail.elnora.fet.hw_8_weather_app.model.data.HourlyWeatherDataModel
import io.reactivex.Single

interface HourlyWeatherRepository {
    fun getHoursWeather(city: String, units: String) : Single<List<HourlyWeatherDataModel>>
}