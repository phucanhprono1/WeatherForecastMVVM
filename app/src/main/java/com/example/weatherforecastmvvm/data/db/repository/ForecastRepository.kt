package com.example.weatherforecastmvvm.data.db.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}