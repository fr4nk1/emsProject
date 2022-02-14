package com.franpulido.data.repository

import com.franpulido.data.source.RemoteDataSource
import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData

class EmsRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getHistoricalData(): List<HistoricalEmsData> = remoteDataSource.getHistoricalData()
    suspend fun getLiveData(): LiveEmsData = remoteDataSource.getLiveData()
}