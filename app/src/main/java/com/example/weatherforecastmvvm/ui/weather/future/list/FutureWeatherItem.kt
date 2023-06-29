package com.example.weatherforecastmvvm.ui.weather.future.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.weatherforecastmvvm.R
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry

import com.example.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureWeatherItem (
    private val weatherEntries: List<UnitSpecificSimpleFutureWeatherEntry>
    ): RecyclerView.Adapter<FutureWeatherItem.FutureWeatherViewHolder>(){
    private lateinit var listener: onItemClickListener
    class FutureWeatherViewHolder(itemView: View,listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        private val textViewCondition: TextView = itemView.findViewById(R.id.textView_condition)
        private val textViewDate: TextView = itemView.findViewById(R.id.textView_date)
        private val textViewTemperature: TextView = itemView.findViewById(R.id.textView_temperature)
        private val imageViewConditionIcon: ImageView = itemView.findViewById(R.id.imageView_condition_icon)
        init {
            itemView.setOnClickListener {
                listener.onItemClicked(adapterPosition)
            }
        }
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
    interface onItemClickListener{
        fun onItemClicked(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_future_weather, parent, false)
        return FutureWeatherViewHolder(itemView,listener)
    }

    override fun getItemCount(): Int {
        return weatherEntries.size
    }

    override fun onBindViewHolder(holder: FutureWeatherViewHolder, position: Int) {
        val weatherEntry = weatherEntries[position]
        holder.bind(weatherEntry)
    }

}
