package com.franpulido.emsproject.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LiveEmsDataEntity(
    val solar_power: Double,
    val quasars_power: Double,
    val grid_power: Double,
    val building_demand: Double,
    val system_soc: Double,
    val total_energy: Double,
    val current_energy: Double
)


