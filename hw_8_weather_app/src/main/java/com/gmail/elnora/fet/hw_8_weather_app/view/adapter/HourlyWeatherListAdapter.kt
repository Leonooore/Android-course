package com.gmail.elnora.fet.hw_8_weather_app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.elnora.fet.hw_8_weather_app.R
import com.gmail.elnora.fet.hw_8_weather_app.presenter.hourlyWeather.HourlyWeatherViewModel
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewImageHourWeatherIcon
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextTime
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextHourDescription
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextHourTemperature

class HourlyWeatherListAdapter (
        private val itemList: MutableList<HourlyWeatherViewModel>
) : RecyclerView.Adapter<HourlyWeatherListAdapter.HourWeatherListItemViewHolder>() {

    private var unit: String = "metric"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourWeatherListItemViewHolder =
        HourWeatherListItemViewHolder(
                itemView = parent.run { LayoutInflater.from(context).inflate(R.layout.item_hour_weather_list, this, false) },
        )

    override fun onBindViewHolder(holder: HourWeatherListItemViewHolder, position: Int) {
        holder.bind(itemList[position], unit)
    }

    override fun getItemCount(): Int = itemList.size

    fun updateItemList(itemListIn: List<HourlyWeatherViewModel>, getUnit: String) {
        itemList.apply {
            clear()
            addAll(itemListIn)
        }
        unit = getUnit
        notifyDataSetChanged()
    }

    class HourWeatherListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hourlyWeather: HourlyWeatherViewModel, unit: String) {
            with(hourlyWeather) {
                itemView.apply {
                    viewTextTime.text = time
                    viewTextHourTemperature.text = tempConvert(temp, unit)
                    viewTextHourDescription.text = weatherDescription
                }
                Glide.with(itemView.context)
                        .load("https://openweathermap.org/img/w/$icon.png")
                        .into(itemView.viewImageHourWeatherIcon)
            }
        }

        private fun tempConvert(temp: String, unit: String): String = if (unit == "metric") "$temp °C" else "$temp °F"
    }

}
