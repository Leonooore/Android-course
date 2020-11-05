package com.gmail.elnora.fet.hw_8_weather_app.presenter

interface WeatherModelPresenter {
    fun fetchCurrentWeather(city: String, unit: String)
    fun fetchHoursWeather(city: String, unit: String)
    fun dispose()
}