package com.example.weatherforecastmvvm.ui.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.provider.UnitProvider
import com.example.weatherforecastmvvm.internal.UnitSystem
import com.example.weatherforecastmvvm.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred(lifecycleOwner) {
        forecastRepository.getWeatherLocation()
    }
}