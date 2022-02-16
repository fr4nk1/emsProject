package com.franpulido.emsproject.data.entities

data class HistoricalEmsDataEntity(
    val building_active_power: Double,
    val grid_active_power: Double,
    val pv_active_power: Double,
    val quasars_active_power: Double,
    val timestamp: String
)

