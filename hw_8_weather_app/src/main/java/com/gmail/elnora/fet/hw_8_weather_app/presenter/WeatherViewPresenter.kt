package com.gmail.elnora.fet.hw_8_weather_app.presenter

import com.gmail.elnora.fet.hw_8_weather_app.presenter.currentweather.CurrentWeatherViewModel
import com.gmail.elnora.fet.hw_8_weather_app.presenter.hourlyweather.HourlyWeatherViewModel

interface WeatherViewPresenter {
    fun showCurrentWeather(currentWeather: CurrentWeatherViewModel)
    fun showHoursWeather(list: List<HourlyWeatherViewModel>)
    fun onError(errorMessage: String)
}