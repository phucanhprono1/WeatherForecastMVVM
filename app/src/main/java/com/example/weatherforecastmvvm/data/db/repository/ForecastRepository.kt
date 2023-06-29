package com.example.weatherforecastmvvm.data.db.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.example.weatherforecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate


interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
}