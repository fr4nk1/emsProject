package com.franpulido.emsproject.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoricalEmsDataEntity(
    val building_active_power: Double,
    val grid_active_power: Double,
    val pv_active_power: Double,
    val quasars_active_power: Double,
    val timestamp: String
)

