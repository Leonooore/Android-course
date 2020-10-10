package com.gmail.elnora.fet.hw_8_weather_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.elnora.fet.hw_8_weather_app.R
import com.gmail.elnora.fet.hw_8_weather_app.data.HoursWeatherDataModel
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewImageHourWeatherIcon
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextTime
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextHourDescription
import kotlinx.android.synthetic.main.item_hour_weather_list.view.viewTextHourTemperature

class HourWeatherListAdapter : RecyclerView.Adapter<HourWeatherListAdapter.HourWeatherListItemViewHolder>() {

    private val itemList = mutableListOf<HoursWeatherDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourWeatherListItemViewHolder =
        HourWeatherListItemViewHolder(
                itemView = parent.run { LayoutInflater.from(context).inflate(R.layout.item_hour_weather_list, this, false) },
        )

    override fun onBindViewHolder(holder: HourWeatherListItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class HourWeatherListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hoursWeatherDataModel: HoursWeatherDataModel) {
            with(hoursWeatherDataModel) {
                itemView.apply {
                    viewTextTime.text = time
                    viewTextHourTemperature.text = temp
                    viewTextHourDescription.text = weatherDescription
                }
                Glide.with(itemView.context)
                        .load(icon)
                        .into(itemView.viewImageHourWeatherIcon)
            }
        }
    }

}
