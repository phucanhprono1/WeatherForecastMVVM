package com.example.weatherforecastmvvm.data.provider

import com.example.weatherforecastmvvm.internal.UnitSystem


interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}