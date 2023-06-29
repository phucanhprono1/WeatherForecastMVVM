package com.example.weatherforecastmvvm.ui.weather.future.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.provider.UnitProvider
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModelFactory(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider,
    private val lifecycleOwner: LifecycleOwner
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(detailDate, forecastRepository, unitProvider,lifecycleOwner) as T
    }
}