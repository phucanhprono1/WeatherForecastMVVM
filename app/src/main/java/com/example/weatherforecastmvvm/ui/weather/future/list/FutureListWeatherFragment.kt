package com.example.weatherforecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastmvvm.databinding.FragmentFutureListWeatherBinding
import com.example.weatherforecastmvvm.internal.LocalDateConverter
import com.example.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class FutureListWeatherFragment : ScopedFragment(),KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory : FutureListWeatherViewModelFactory by instance()
    companion object {
        fun newInstance() = FutureListWeatherFragment()
    }

    private lateinit var viewModel: FutureListWeatherViewModel
    private lateinit var binding: FragmentFutureListWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFutureListWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(FutureListWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        bindUI()
    }
    private fun bindUI() = viewLifecycleOwner.lifecycleScope.launch{
        val futureWeatherEntries = viewModel.weatherEntries.await()
        val weatherLocation = viewModel.weatherLocation.await()
        weatherLocation.observe(viewLifecycleOwner) {it->
            if (it == null) return@observe
            updateLocation(it.name)
        }
        futureWeatherEntries.observe(viewLifecycleOwner) {it->
            if (it == null) return@observe
            binding.groupLoading.visibility = View.GONE
            updateDateToNextWeek()
            val futureWeatherItem = FutureWeatherItem(it)
            binding.recyclerView.apply {
                adapter = futureWeatherItem
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            futureWeatherItem.setOnItemClickListener(object: FutureWeatherItem.onItemClickListener{
                override fun onItemClicked(position: Int) {
                    val dateString = LocalDateConverter.dateToString(it[position].date)

                    val actionDetail = FutureListWeatherFragmentDirections.actionFutureListWeatherFragmentToFutureDetailWeatherFragment(dateString!!)
                    Navigation.findNavController(binding.root).navigate(actionDetail)
                }

            })

        }
    }

    private fun updateLocation(location:String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }
    private fun updateDateToNextWeek(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
    }


//    private fun showWeatherDetail(date: LocalDate, view: View) {
//        val dateString = LocalDateConverter.dateToString(date)!!
//        val actionDetail = FutureListWeatherFragmentDirections.actionDetail(dateString)
//        Navigation.findNavController(view).navigate(actionDetail)
//    }


}