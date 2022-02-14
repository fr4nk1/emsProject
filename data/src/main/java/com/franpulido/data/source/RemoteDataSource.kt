package com.franpulido.data.source

import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData

interface RemoteDataSource {
    suspend fun getHistoricalData(): List<HistoricalEmsData>
    suspend fun getLiveData(): LiveEmsData
}