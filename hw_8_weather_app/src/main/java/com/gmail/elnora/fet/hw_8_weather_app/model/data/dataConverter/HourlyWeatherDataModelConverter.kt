package com.gmail.elnora.fet.hw_8_weather_app.model.data.dataConverter

import android.os.Build
import androidx.annotation.RequiresApi
import com.gmail.elnora.fet.hw_8_weather_app.model.data.HourlyWeatherDataModel
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HourlyWeatherDataModelConverter : (String) -> List<HourlyWeatherDataModel> {
    @RequiresApi(Build.VERSION_CODES.O)
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
                        time = timeConvert(jsonArrayListObject.getString("dt_txt")),
                        temp = jsonArrayListObject.getJSONObject("main").getInt("temp").toString(),
                        weatherDescription = jsonWeatherObject.getString("main"),
                        icon = jsonWeatherObject.getString("icon")
                )
                itemList.add(hoursWeatherDataModel)
            }
            return itemList
        }
        return emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeConvert(fullTime: String): String {
        val pattern: String = "yyyy-MM-dd HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val time = LocalDateTime.parse(fullTime, formatter)
        return "${time.hour}:00"
    }
}