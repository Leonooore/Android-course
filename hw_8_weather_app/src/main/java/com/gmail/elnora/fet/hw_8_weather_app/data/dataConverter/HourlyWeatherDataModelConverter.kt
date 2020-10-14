package com.gmail.elnora.fet.hw_8_weather_app.data.dataConverter

import com.gmail.elnora.fet.hw_8_weather_app.data.HourlyWeatherDataModel
import org.json.JSONObject

class HourlyWeatherDataModelConverter : (String) -> List<HourlyWeatherDataModel> {
    override fun invoke(jsonData: String): List<HourlyWeatherDataModel> {
        val jsonObject = JSONObject(jsonData)
        val jsonListArray = jsonObject.getJSONArray("list")
        if (jsonListArray.length() != 0) {
            val itemList = mutableListOf<HourlyWeatherDataModel>()
            for (index in 0 until jsonListArray.length()) {
                val jsonArrayListObject = jsonListArray.getJSONObject(index)
                val jsonWeatherArray = jsonArrayListObject.getJSONArray("weather")
                val jsonWeatherObject = jsonWeatherArray.getJSONObject(0)
                val hoursWeatherDataModel = HourlyWeatherDataModel(
                        time = jsonArrayListObject.getString("dt_txt").substring(11, 16),
                        temp = jsonArrayListObject.getJSONObject("main").getString("temp"),
                        weatherDescription = jsonWeatherObject.getString("main"),
                        icon = jsonWeatherObject.getString("icon")
                )
                itemList.add(hoursWeatherDataModel)
            }
            return itemList
        }
        return emptyList()
    }
}