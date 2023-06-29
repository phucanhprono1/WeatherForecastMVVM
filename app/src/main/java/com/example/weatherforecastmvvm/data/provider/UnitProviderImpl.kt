package com.example.weatherforecastmvvm.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.weatherforecastmvvm.internal.LiveDataPreference
import com.example.weatherforecastmvvm.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"


class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {
    private val appContext = context.applicationContext
    private val preferences1: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}
