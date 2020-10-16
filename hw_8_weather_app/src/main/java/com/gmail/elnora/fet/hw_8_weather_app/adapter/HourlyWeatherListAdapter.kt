package com.gmail.elnora.fet.hw_8_weather_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.elnora.fet.hw_8_weather_app.R
import com.gmail.elnora.fet.hw_8_weather_app.WeatherFragment
import com.gmail.elnora.fet.hw_8_weather_app.data.HourlyWeatherDataModel
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewImageHourWeatherIcon
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextTime
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextHourDescription
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextHourTemperature

class HourlyWeatherListAdapter (
        private val itemList: MutableList<HourlyWeatherDataModel>
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

    fun updateItemList(itemListIn: List<HourlyWeatherDataModel>, getUnit: String) {
        itemList.apply {
            clear()
            addAll(itemListIn)
        }
        unit = getUnit
        notifyDataSetChanged()
    }

    class HourWeatherListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hourlyWeatherDataModel: HourlyWeatherDataModel, unit: String) {
            with(hourlyWeatherDataModel) {
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

        private fun tempConvert(temp: String, unit: String): String = if (unit == WeatherFragment.UNITS_METRIC) "$temp °C" else "$temp °F"
    }

}
