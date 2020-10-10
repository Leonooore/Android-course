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
import com.gmail.elnora.fet.hw_8_weather_app.adapter.HourWeatherListAdapter
import com.gmail.elnora.fet.hw_8_weather_app.data.dataConverter.CurrentWeatherDataModelConverter
import com.gmail.elnora.fet.hw_8_weather_app.repo.CurrentWeatherRepositoryImpl
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_weather, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemList()
        fetchCurrentWeather()
    }

    private fun initItemList() {
        viewRecyclerHourWeatherList.apply {
            adapter = HourWeatherListAdapter()
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
                            var iconCode: String = currentWeather.icon
                            Glide.with(this)
                                    .load("https://openweathermap.org/img/w/$iconCode.png")
                                    .into(viewImageWeatherIcon)
                        },
                        { throwable -> Log.d("CURRENT_WEATHER", throwable.toString()) }
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