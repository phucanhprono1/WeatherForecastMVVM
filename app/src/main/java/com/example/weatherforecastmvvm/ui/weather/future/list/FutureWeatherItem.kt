package com.example.weatherforecastmvvm.ui.weather.future.list


import android.content.ClipData.Item;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.weatherforecastmvvm.R
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.MetricSimpleFutureWeatherEntry

import com.example.weatherforecastmvvm.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureWeatherItem (
    private val weatherEntries: List<UnitSpecificSimpleFutureWeatherEntry>,
    private val itemClick:ItemClickListener
    ): RecyclerView.Adapter<FutureWeatherItem.FutureWeatherViewHolder>(){
    inner class FutureWeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val textViewCondition: TextView = itemView.findViewById(R.id.textView_condition)
        private val textViewDate: TextView = itemView.findViewById(R.id.textView_date)
        private val textViewTemperature: TextView = itemView.findViewById(R.id.textView_temperature)
        private val imageViewConditionIcon: ImageView = itemView.findViewById(R.id.imageView_condition_icon)

        fun bind(weatherEntry: UnitSpecificSimpleFutureWeatherEntry) {
            textViewCondition.text = weatherEntry.conditionText
            updateDate(weatherEntry.date)
            updateTemperature(weatherEntry)
            updateConditionImage(weatherEntry.conditionIconUrl)
        }

        private fun updateDate(date: LocalDate) {
            val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            textViewDate.text = date.format(dtFormatter)
        }

        private fun updateTemperature(weatherEntry: UnitSpecificSimpleFutureWeatherEntry) {
            val unitAbbreviation = if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C" else "°F"
            textViewTemperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"
        }

        private fun updateConditionImage(conditionIconUrl: String) {
            Glide.with(imageViewConditionIcon.context)
                .load("http:$conditionIconUrl")
                .into(imageViewConditionIcon)
        }
    }
    interface ItemClickListener{
        fun onItemClicked(item: UnitSpecificSimpleFutureWeatherEntry)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_future_weather, parent, false)
        return FutureWeatherViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherEntries.size
    }

    override fun onBindViewHolder(holder: FutureWeatherViewHolder, position: Int) {
        val weatherEntry = weatherEntries[position]
        holder.bind(weatherEntry)
    }

}
