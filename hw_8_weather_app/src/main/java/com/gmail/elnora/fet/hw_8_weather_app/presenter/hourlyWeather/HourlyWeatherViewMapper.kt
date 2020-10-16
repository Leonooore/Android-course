package com.gmail.elnora.fet.hw_8_weather_app.presenter.hourlyWeather

import com.gmail.elnora.fet.hw_8_weather_app.model.data.HourlyWeatherDataModel

class HourlyWeatherViewMapper: (List<HourlyWeatherDataModel>) -> List<HourlyWeatherViewModel> {
    override fun invoke(list: List<HourlyWeatherDataModel>): List<HourlyWeatherViewModel> =
        list.map { hourlyWeatherDataModel ->
            HourlyWeatherViewModel(
                    time = hourlyWeatherDataModel.time,
                    temp = hourlyWeatherDataModel.temp,
                    weatherDescription = hourlyWeatherDataModel.weatherDescription,
                    icon = hourlyWeatherDataModel.icon
            )
        }
}