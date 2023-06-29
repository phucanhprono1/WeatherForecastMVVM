package com.example.weatherforecastmvvm.ui.weather.future.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.provider.UnitProvider
import com.example.weatherforecastmvvm.internal.lazyDeferred
import com.example.weatherforecastmvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider,
    private val lifecycleOwner: LifecycleOwner

) : WeatherViewModel(forecastRepository, unitProvider, lifecycleOwner) {
    val weather by lazyDeferred(lifecycleOwner) {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}