package com.gmail.elnora.fet.hw_8_weather_app.presenter

import com.gmail.elnora.fet.hw_8_weather_app.model.data.CurrentWeatherDataModel
import com.gmail.elnora.fet.hw_8_weather_app.model.data.HourlyWeatherDataModel
import com.gmail.elnora.fet.hw_8_weather_app.model.repo.currentWeather.CurrentWeatherRepository
import com.gmail.elnora.fet.hw_8_weather_app.model.repo.hourlyWeather.HourlyWeatherRepository
import com.gmail.elnora.fet.hw_8_weather_app.presenter.currentWeather.CurrentWeatherViewModel
import com.gmail.elnora.fet.hw_8_weather_app.presenter.hourlyWeather.HourlyWeatherViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class WeatherPresenterImpl(
        private val weatherViewPresenter: WeatherViewPresenter,
        private val currentWeatherRepository: CurrentWeatherRepository,
        private val currentWeatherMapper: (CurrentWeatherDataModel) -> CurrentWeatherViewModel,
        private val hourlyWeatherRepository: HourlyWeatherRepository,
        private val hourlyWeatherViewMapper: (List<HourlyWeatherDataModel>) -> List<HourlyWeatherViewModel>
) : WeatherModelPresenter {

    private var disposable: Disposable? = null

    override fun fetchCurrentWeather(city: String, unit: String) {
        disposable = currentWeatherRepository.getCurrentWeather(city, unit)
                .map { currentWeatherMapper(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { currentWeather -> weatherViewPresenter.showCurrentWeather(currentWeather) },
                        { throwable -> weatherViewPresenter.onError(throwable.toString()) }
                )
    }

    override fun fetchHoursWeather(city: String, unit: String) {
        disposable = hourlyWeatherRepository.getHoursWeather(city, unit)
                .map { hourlyWeatherViewMapper(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> weatherViewPresenter.showHoursWeather(list) },
                        { throwable -> weatherViewPresenter.onError(throwable.toString()) }
                )
    }

    override fun dispose() {
        disposable?.dispose()
    }

}