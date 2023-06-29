package com.example.weatherforecastmvvm.ui.weather.current

import androidx.lifecycle.LifecycleOwner
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.provider.UnitProvider
import com.example.weatherforecastmvvm.internal.lazyDeferred
import com.example.weatherforecastmvvm.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    private val lifecycleOwner: LifecycleOwner
) : WeatherViewModel(forecastRepository, unitProvider, lifecycleOwner) {
    val weather by lazyDeferred(lifecycleOwner) {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}