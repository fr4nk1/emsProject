package com.franpulido.domain.models

data class LiveEmsData(
    val solarPower: Double,
    val quasarsPower: Double,
    val gridPower: Double,
    val buildingDemand: Double,
    val systemSoc: Double,
    val totalEnergy: Double,
    val currentEnergy: Double
)