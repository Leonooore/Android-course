package com.gmail.elnora.fet.hw_8_weather_app.data

data class HourlyWeatherDataModel(
    val time: String,
    val temp: String,
    val weatherDescription: String,
    val icon: String
)