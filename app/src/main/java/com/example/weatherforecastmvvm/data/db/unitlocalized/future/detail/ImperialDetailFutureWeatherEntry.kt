package com.example.weatherforecastmvvm.data.db.unitlocalized.future.detail

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate


data class ImperialDetailFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "maxtemp_f")
    override val maxTemperature: Double,
    @ColumnInfo(name = "mintemp_f")
    override val minTemperature: Double,
    @ColumnInfo(name = "avgtemp_f")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "maxwind_mph")
    override val maxWindSpeed: Double,
    @ColumnInfo(name = "totalprecip_in")
    override val totalPrecipitation: Double,
    @ColumnInfo(name = "avgvis_miles")
    override val avgVisibilityDistance: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double
) : UnitSpecificDetailFutureWeatherEntry