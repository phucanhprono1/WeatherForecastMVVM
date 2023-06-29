package com.example.weatherforecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.weatherforecastmvvm.R
import com.example.weatherforecastmvvm.databinding.FragmentFutureDetailWeatherBinding
import com.example.weatherforecastmvvm.databinding.FragmentFutureListWeatherBinding
import com.example.weatherforecastmvvm.internal.DateNotFoundException
import com.example.weatherforecastmvvm.internal.LocalDateConverter
import com.example.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactoryInstanceFactory:((LocalDate) -> FutureDetailWeatherViewModelFactory) by factory()
    companion object {
        fun newInstance() = FutureDetailWeatherFragment()
    }

    private lateinit var viewModel: FutureDetailWeatherViewModel
    private lateinit var binding: FragmentFutureDetailWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFutureDetailWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val date  = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        viewModel = ViewModelProvider(this,viewModelFactoryInstanceFactory(date)).get(FutureDetailWeatherViewModel::class.java)

        bindUI()

    }
    private fun bindUI() = viewLifecycleOwner.lifecycleScope.launch {
        val weather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()
        weatherLocation.observe(viewLifecycleOwner) {it->
            if (it == null) return@observe
            updateLocation(it.name)
        }
        weather.observe(viewLifecycleOwner) {it->
            if (it == null) return@observe

            updateDate(it.date)
            updateTemperatures(it.avgTemperature,it.minTemperature,it.maxTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.totalPrecipitation)
            updateWindSpeed(it.maxWindSpeed)
            updateVisibility(it.avgVisibilityDistance)
            updateUv(it.uv)
            Glide.with(this@FutureDetailWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(binding.imageViewConditionIcon)
        }

    }
    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate(date: LocalDate) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(temperature: Double, min: Double, max: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        binding.textViewTemperature.text = "$temperature$unitAbbreviation"
        binding.textViewMinMaxTemperature.text = "Min: $min$unitAbbreviation, Max: $max$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        binding.textViewCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        binding.textViewPrecipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWindSpeed(windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        binding.textViewWind.text = "Wind speed: $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        binding.textViewVisibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

    private fun updateUv(uv: Double) {
        binding.textViewUv.text = "UV: $uv"
    }


}