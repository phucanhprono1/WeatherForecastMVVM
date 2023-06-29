package com.example.weatherforecastmvvm

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.preference.PreferenceManager
import com.example.weatherforecastmvvm.data.WeatherApiService
import com.example.weatherforecastmvvm.data.db.ForecastDatabase
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.db.repository.ForecastRepositoryImpl
import com.example.weatherforecastmvvm.data.network.ConnectivityInterceptor
import com.example.weatherforecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.weatherforecastmvvm.data.network.WeatherNetworkDataSource
import com.example.weatherforecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.weatherforecastmvvm.data.provider.LocationProvider

import com.example.weatherforecastmvvm.data.provider.LocationProviderImpl
import com.example.weatherforecastmvvm.data.provider.UnitProvider
import com.example.weatherforecastmvvm.data.provider.UnitProviderImpl
import com.example.weatherforecastmvvm.ui.base.LifeCycleFragment
import com.example.weatherforecastmvvm.ui.base.ScopedFragment
import com.example.weatherforecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.weatherforecastmvvm.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.example.weatherforecastmvvm.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.threeten.bp.LocalDate

class ForecastApplication: Application(), KodeinAware{
    override val kodein = Kodein.lazy  {
        import(androidXModule(this@ForecastApplication))
        bind<LifecycleOwner>() with provider { ProcessLifecycleOwner.get() }
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<FusedLocationProviderClient>() with provider { LocationServices.getFusedLocationProviderClient(instance<Application>()) }
        
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(),instance(),instance(),instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance(),instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(),instance(),instance()) }

        bind() from factory{detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate,instance(),instance(),instance()) }

    }
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }

}