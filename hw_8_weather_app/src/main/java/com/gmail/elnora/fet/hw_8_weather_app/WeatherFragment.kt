package com.gmail.elnora.fet.hw_8_weather_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.elnora.fet.hw_8_weather_app.adapter.HourlyWeatherListAdapter
import com.gmail.elnora.fet.hw_8_weather_app.data.HourlyWeatherDataModel
import com.gmail.elnora.fet.hw_8_weather_app.data.dataConverter.CurrentWeatherDataModelConverter
import com.gmail.elnora.fet.hw_8_weather_app.data.dataConverter.HourlyWeatherDataModelConverter
import com.gmail.elnora.fet.hw_8_weather_app.repo.CurrentWeatherRepositoryImpl
import com.gmail.elnora.fet.hw_8_weather_app.repo.HoursWeatherRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_weather.viewTextCity
import kotlinx.android.synthetic.main.fragment_weather.viewTextTemperature
import kotlinx.android.synthetic.main.fragment_weather.viewTextWeatherDescription
import kotlinx.android.synthetic.main.fragment_weather.viewImageWeatherIcon
import kotlinx.android.synthetic.main.fragment_weather.viewRecyclerHourWeatherList
import okhttp3.OkHttpClient

class WeatherFragment : Fragment() {

    private var disposable: Disposable? = null
    private val itemList = mutableListOf<HourlyWeatherDataModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_weather, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemList()
        fetchCurrentWeather()
        fetchHoursWeather()
    }

    private fun initItemList() {
        viewRecyclerHourWeatherList.apply {
            adapter = HourlyWeatherListAdapter(itemList)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun fetchCurrentWeather() {
        disposable = CurrentWeatherRepositoryImpl(
                okHttpClient = OkHttpClient(),
                currentWeatherDataModelConverter = CurrentWeatherDataModelConverter()
        ).getCurrentWeather("Minsk", "metric")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { currentWeather ->
                            viewTextCity.text = currentWeather.city
                            viewTextTemperature.text = currentWeather.temp
                            viewTextWeatherDescription.text = currentWeather.weatherDescription
                            val iconCode: String = currentWeather.icon
                            Glide.with(this)
                                    .load("https://openweathermap.org/img/w/$iconCode.png")
                                    .into(viewImageWeatherIcon)
                        },
                        { throwable -> Log.d("CURRENT_WEATHER", throwable.toString()) }
                )
    }

    private fun fetchHoursWeather() {
        disposable = HoursWeatherRepositoryImpl(
                okHttpClient = OkHttpClient(),
                hourlyWeatherDataModelConverter =  HourlyWeatherDataModelConverter()
        ).getHoursWeather("Minsk", "metric")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list ->
                            (viewRecyclerHourWeatherList.adapter as? HourlyWeatherListAdapter)?.updateItemList(list)
                        },
                        { throwable -> Log.d("HOURS_WEATHER", throwable.toString()) }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        const val TAG: String = "WeatherFragment"
        fun newInstance() = WeatherFragment()
    }
}