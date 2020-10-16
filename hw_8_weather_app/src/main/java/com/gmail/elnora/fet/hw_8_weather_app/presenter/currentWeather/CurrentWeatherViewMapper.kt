package com.gmail.elnora.fet.hw_8_weather_app.presenter.currentWeather

import com.gmail.elnora.fet.hw_8_weather_app.model.data.CurrentWeatherDataModel

class CurrentWeatherViewMapper: (CurrentWeatherDataModel) -> CurrentWeatherViewModel {
    override fun invoke(dataModel: CurrentWeatherDataModel): CurrentWeatherViewModel {
        return CurrentWeatherViewModel(
                city = dataModel.city,
                temp = dataModel.temp,
                weatherDescription = dataModel.weatherDescription,
                icon = dataModel.icon
        )
    }
}