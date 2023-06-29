package com.example.weatherforecastmvvm.data.db.unitlocalized.future

import androidx.room.ColumnInfo
import androidx.room.RoomWarnings
import org.threeten.bp.LocalDate
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
class ImperialSimpleFutureWeatherEntry (
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtemp_f")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String

): UnitSpecificSimpleFutureWeatherEntry