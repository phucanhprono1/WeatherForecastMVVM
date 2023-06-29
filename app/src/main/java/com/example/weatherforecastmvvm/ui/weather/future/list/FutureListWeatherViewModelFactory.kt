package com.example.weatherforecastmvvm.ui.weather.future.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.provider.UnitProvider

class FutureListWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider,
    private val lifecycleOwner: LifecycleOwner
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(forecastRepository, unitProvider,lifecycleOwner) as T
    }
}