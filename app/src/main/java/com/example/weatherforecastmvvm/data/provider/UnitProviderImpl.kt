package com.example.weatherforecastmvvm.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.weatherforecastmvvm.internal.LiveDataPreference
import com.example.weatherforecastmvvm.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : UnitProvider {
    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    private val unitSystemLiveData = LiveDataPreference(preferences, UNIT_SYSTEM)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, unitSystemLiveData.value)
        return UnitSystem.valueOf(selectedName!!)
    }

    fun getUnitSystemLiveData(): LiveData<String> {
        return unitSystemLiveData
    }
}