package com.franpulido.emsproject.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoricalEmsModelList(
    val results: List<HistoricalEmsModel>
): Parcelable

@Parcelize
data class HistoricalEmsModel(
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val pvActivePower: Double,
    val quasarsActivePower: Double,
    val timeStamp: String
) : Parcelable