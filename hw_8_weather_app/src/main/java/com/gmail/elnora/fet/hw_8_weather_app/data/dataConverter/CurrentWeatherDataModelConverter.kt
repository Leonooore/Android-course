package com.gmail.elnora.fet.hw_8_weather_app.data.dataConverter

import com.gmail.elnora.fet.hw_8_weather_app.data.CurrentWeatherDataModel
import org.json.JSONObject

class CurrentWeatherDataModelConverter : (String) -> CurrentWeatherDataModel  {
    override fun invoke(jsonData: String): CurrentWeatherDataModel {
        val jsonObject = JSONObject(jsonData)
        val jsonWeatherArray = jsonObject.getJSONArray("weather")
        val jsonMainArray = jsonObject.getJSONObject("main")
        return CurrentWeatherDataModel(
                city = jsonObject.getString("name"),
                temp = jsonMainArray.getInt("temp").toString(),
                weatherDescription = jsonWeatherArray.getJSONObject(0).getString("description"),
                icon = jsonWeatherArray.getJSONObject(0).getString("icon")
        )
    }
}