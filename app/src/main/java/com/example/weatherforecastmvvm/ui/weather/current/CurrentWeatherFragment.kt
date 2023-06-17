package com.example.weatherforecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.weatherforecastmvvm.data.WeatherApiService
import com.example.weatherforecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.weatherforecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.weatherforecastmvvm.databinding.FragmentCurrentWeatherBinding
import com.example.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(),KodeinAware{
    override val kodein by closestKodein()
    private val viewModelFactory : CurrentWeatherViewModelFactory by instance()

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var binding: FragmentCurrentWeatherBinding
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()

    }
    private fun bindUI() = launch{
        val currentWeather = viewModel.weather
        currentWeather.observe(viewLifecycleOwner) {it->
            if (it == null) return@observe
            binding.groupLoading.visibility = View.GONE
            updateLocation("Hanoi")
            updateDateToToday()
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)
            Glide.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(binding.imageViewConditionIcon)
        }
    }
    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }
    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }
    private fun updateTemperature(temperature: Double, feelsLike: Double){
       val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        binding.textViewTemperature.text = "$temperature$unitAbbreviation"
        binding.textViewFeelsLikeTemperature.text = "Feels like $feelsLike$unitAbbreviation"

    }
    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String{
        return if (viewModel.isMetric) metric else imperial
    }
    private fun updateCondition(condition: String){
        binding.textViewCondition.text = condition
    }
    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        binding.textViewPrecipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }
    private fun updateWind(windDirection: String, windSpeed: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        binding.textViewWind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }
    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        binding.textViewVisibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }
}