package com.example.weatherforecastmvvm.ui.weather.future.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.provider.UnitProvider
import com.example.weatherforecastmvvm.internal.lazyDeferred
import com.example.weatherforecastmvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    private val lifecycleOwner: LifecycleOwner
) : WeatherViewModel(forecastRepository, unitProvider, lifecycleOwner) {
    val weatherEntries by lazyDeferred(lifecycleOwner) {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}