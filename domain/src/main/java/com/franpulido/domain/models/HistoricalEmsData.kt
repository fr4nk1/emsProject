package com.franpulido.domain.models

data class HistoricalEmsData(
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val pvActivePower: Double,
    val quasarsActivePower: Double,
    val timeStamp: String
)