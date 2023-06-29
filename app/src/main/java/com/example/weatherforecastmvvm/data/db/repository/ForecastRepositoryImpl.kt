package com.example.weatherforecastmvvm.data.db.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecastmvvm.data.db.CurrentWeatherDao
import com.example.weatherforecastmvvm.data.db.FutureWeatherDao
import com.example.weatherforecastmvvm.data.db.WeatherLocationDao
import com.example.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.example.weatherforecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.example.weatherforecastmvvm.data.network.FORECAST_DAYS_COUNT
import com.example.weatherforecastmvvm.data.network.WeatherNetworkDataSource
import com.example.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.weatherforecastmvvm.data.network.response.FutureWeatherResponse
import com.example.weatherforecastmvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.Locale

class ForecastRepositoryImpl (
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
): ForecastRepository {
    private val currentWeather = MutableLiveData<UnitSpecificCurrentWeatherEntry>()
    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever{
                persistFetchedCurrentWeather(it)
            }
            downloadedFutureWeather.observeForever{
                persistFetchedFutureWeather(it)
            }
        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            Log.d("ForecastRepositoryImpl", "getCurrentWeather: ${currentWeatherDao.getWeatherMetric()}")
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()

        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getDetailedWeatherByDateMetric(date)
            else futureWeatherDao.getDetailedWeatherByDateImperial(date)
        }
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastsMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastsImperial(startDate)
        }
    }



    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }
    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
        fun deleteOldForecastData(){
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }
        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeather = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeather)
            weatherLocationDao.upsert(fetchedWeather.location)

        }
    }
    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()
        if (lastWeatherLocation == null ||
            locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchFutureWeather()
            fetchCurrentWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
        if (isFetchFutureNeeded())
            fetchFutureWeather()
    }
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString()
            , Locale.getDefault().language)
    }
    private fun isFetchCurrentNeeded(lastFetchedTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)

    }
    private fun isFetchFutureNeeded(): Boolean{
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT

     }
    private suspend fun fetchFutureWeather(){
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString()
            , Locale.getDefault().language)
    }
}