package com.gmail.elnora.fet.hw_8_weather_app.repo

import com.gmail.elnora.fet.hw_8_weather_app.data.CurrentWeatherDataModel
import com.gmail.elnora.fet.hw_8_weather_app.data.HoursWeatherDataModel
import io.reactivex.Single
import okhttp3.OkHttpClient

private const val API_KEY = "949e072f6bb8cd6e24313d6894d3c322"

class HoursWeatherRepositoryImpl (
        private val okHttpClient: OkHttpClient,
        private val newsDataModelMapper: (String) -> List<CurrentWeatherDataModel>
) : HoursWeatherRepository {
    override fun getHoursWeather(): Single<List<HoursWeatherDataModel>> {
        TODO("Not yet implemented")
    }
}