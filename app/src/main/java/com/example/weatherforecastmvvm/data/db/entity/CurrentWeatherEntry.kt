package com.example.weatherforecastmvvm.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherforecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0
@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("is_day")
    val isDay: Int,
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("feelslike_f")
    val feelslikeF: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double
) : UnitSpecificCurrentWeatherEntry {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
    override val temperature: Double
        get() = TODO("Not yet implemented")
    override val conditionText: String
        get() = TODO("Not yet implemented")
    override val conditionIconUrl: String
        get() = TODO("Not yet implemented")
    override val windSpeed: Double
        get() = TODO("Not yet implemented")
    override val windDirection: String
        get() = TODO("Not yet implemented")
    override val precipitationVolume: Double
        get() = TODO("Not yet implemented")
    override val feelsLikeTemperature: Double
        get() = TODO("Not yet implemented")
    override val visibilityDistance: Double
        get() = TODO("Not yet implemented")
}