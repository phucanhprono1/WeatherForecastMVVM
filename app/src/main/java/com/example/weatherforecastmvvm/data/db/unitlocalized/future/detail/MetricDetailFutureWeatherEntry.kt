package com.example.weatherforecastmvvm.data.db.unitlocalized.future.detail

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate


data class MetricDetailFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "maxtemp_c")
    override val maxTemperature: Double,
    @ColumnInfo(name = "mintemp_c")
    override val minTemperature: Double,
    @ColumnInfo(name = "avgtemp_c")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "maxwind_kph")
    override val maxWindSpeed: Double,
    @ColumnInfo(name = "totalprecip_mm")
    override val totalPrecipitation: Double,
    @ColumnInfo(name = "avgvis_km")
    override val avgVisibilityDistance: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double
) : UnitSpecificDetailFutureWeatherEntry