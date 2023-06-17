package com.example.weatherforecastmvvm.internal

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class LiveDataPreference(private val sharedPreferences: SharedPreferences, private val key: String) :
    LiveData<String>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, sharedKey ->
            if (sharedKey == key) {
                value = sharedPreferences.getString(key, null)
            }
        }

    override fun onActive() {
        super.onActive()
        value = sharedPreferences.getString(key, null)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }
}