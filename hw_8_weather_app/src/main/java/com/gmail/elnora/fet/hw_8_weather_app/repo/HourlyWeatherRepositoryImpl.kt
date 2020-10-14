package com.gmail.elnora.fet.hw_8_weather_app.repo

import com.gmail.elnora.fet.hw_8_weather_app.data.HourlyWeatherDataModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

private const val API_KEY = "949e072f6bb8cd6e24313d6894d3c322"

class HoursWeatherRepositoryImpl (
        private val okHttpClient: OkHttpClient,
        private val hourlyWeatherDataModelConverter: (String) -> List<HourlyWeatherDataModel>
) : HourlyWeatherRepository {
    override fun getHoursWeather(city: String, units: String): Single<List<HourlyWeatherDataModel>> {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&units=$units&appid=$API_KEY"
        val request = Request.Builder().url(url).build()
        return Single.create<String> { emitter ->
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) emitter.onError(Throwable("Server error code: ${response.code}"))
                if (response.body == null) emitter.onError(NullPointerException("Body is null"))
                emitter.onSuccess((response.body as ResponseBody).string())
            }
        }.subscribeOn(Schedulers.io())
                .map { jsonData -> hourlyWeatherDataModelConverter(jsonData) }
    }
}